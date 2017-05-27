package xyz.cybersapien.newsdroid.story.Guardian

import android.content.AsyncTaskLoader
import android.content.Context
import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import xyz.cybersapien.newsdroid.network.createURL
import xyz.cybersapien.newsdroid.network.getDate
import xyz.cybersapien.newsdroid.network.makeHttpRequest

/**
 * Created by cybersapien on 27/5/17.
 * Custom Loader for Guardian Stories
 */

class GuardianStoryLoader(ctxt: Context?, val queryURL: String) :
        AsyncTaskLoader<List<GuardianStory>>(ctxt) {

    companion object {
        private val LOG_TAG = this::class.java.simpleName
    }

    override fun onStartLoading() {
        forceLoad()
    }

    override fun loadInBackground(): List<GuardianStory> {
        val URL = createURL(queryURL)
        val jsonResponse = makeHttpRequest(URL)

        return getDataFromGJson(jsonResponse)
    }

    /**
     * Method to get the stories from The Guardian News Services API
     * This is more or less obsolete
     * @param storyJSON JSON String containing Raw data
     * @return List of Guardian Stories
     */
    fun getDataFromGJson(storyJSON: String): List<GuardianStory> {
        if (storyJSON.isNullOrEmpty()) {
            return emptyList()
        }

        val root = JSONObject(storyJSON)
        val responseObject = root.getJSONObject("response")
        val resultsArray = responseObject.getJSONArray("results")
        val stories: ArrayList<GuardianStory> = ArrayList<GuardianStory>()

        for (i in 1 until resultsArray.length()) {
            val currentStory = resultsArray.getJSONObject(i)
            try {
                val title = currentStory.getString("webTitle")
                val pubDate = getDate(currentStory.getString("webPublicationDate"))
                val storyURL = currentStory.getString("webUrl")
                val currentStoryField = currentStory.getJSONObject("fields")
                val storyImageURL = currentStoryField.getString("thumbnail")
                val byLine = currentStoryField.optString("byline", GuardianStory.byLineDefault)
                val trailText = currentStoryField.getString("trailText")

                stories.add(GuardianStory(title, trailText, storyImageURL, byLine, storyURL, pubDate))
            } catch (e: JSONException){
                Log.e(LOG_TAG, "Error Parsing JSON")
                Log.e(LOG_TAG, currentStory.toString())
            }
        }

        return stories
    }
}