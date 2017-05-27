package xyz.cybersapien.newsdroid

import android.app.LoaderManager
import android.content.Context
import android.content.Intent
import android.content.Loader
import android.net.ConnectivityManager
import android.net.Uri
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import java.util.ArrayList

import xyz.cybersapien.newsdroid.story.Guardian.GuardianStory
import xyz.cybersapien.newsdroid.story.Guardian.GuardianStoryAdapter
import xyz.cybersapien.newsdroid.story.Guardian.GuardianStoryLoader

class GuardianActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<List<GuardianStory>> {

    /*Adapter for the stories*/
    private var guardianStoryAdapter: GuardianStoryAdapter? = null

    /*Create the Recycler View*/
    private var storyListView: RecyclerView? = null

    /*Reference to the progress Bar*/
    private var progressBar: ProgressBar? = null

    /*Reference to the error(hint) TextView*/
    private var hintView: TextView? = null

    /*Reference the stories.*/
    var stories: ArrayList<GuardianStory>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guardian)

        //Initialize Progress bar
        progressBar = findViewById(R.id.progressBar) as ProgressBar

        //Initialize TextView
        hintView = findViewById(R.id.errorHint) as TextView

        //Create RecyclerView
        storyListView = findViewById(R.id.stories_list) as RecyclerView
        storyListView!!.visibility = View.GONE

        //Create GuardianStory list;
        stories = ArrayList<GuardianStory>()
        //get GuardianStoryAdapter
        guardianStoryAdapter = GuardianStoryAdapter(this, stories)

        storyListView!!.adapter = guardianStoryAdapter

        storyListView!!.layoutManager = LinearLayoutManager(this)

        startLoad()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
            }
            R.id.action_test ->
                startLoad()
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * This method starts the loader to get news Stories,
     * if there is no connection, it shows the hintView stating the error
     */
    private fun startLoad() {
        //Create the connectivity Manager and NetworkInfo  objects to check for Network connectivity
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        if (networkInfo != null && networkInfo.isConnected) {
            //Initialize the loader. Pass the itn ID constant defined above and pass in null
            loaderManager.restartLoader(STORY_LOADER_ID, null, this)

            loaderManager.initLoader(STORY_LOADER_ID, null, this)
        } else {
            hintView!!.setText(R.string.no_internet)
            progressBar!!.visibility = View.GONE
            hintView!!.visibility = View.VISIBLE
            storyListView!!.visibility = View.GONE
        }
    }


    /**
     * On Create Loader handles the setting of the View to loading and then goes on to initiate the network call i the background.\
     */
    override fun onCreateLoader(id: Int, args: Bundle?): Loader<List<GuardianStory>> {
        progressBar!!.visibility = View.VISIBLE
        hintView!!.visibility = View.GONE

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val section = sharedPreferences.getString(getString(R.string.settings_section_key),
                getString(R.string.settings_section_default_value))
        val pageSize = sharedPreferences.getString(getString(R.string.settings_number_key), getString(R.string.settings_number_default_value))

        val baseUri = Uri.parse(GUARDIAN_URL)
        val uriBuilder = baseUri.buildUpon()
        uriBuilder.appendPath(section)
        uriBuilder.appendQueryParameter("format", "json")
        /*Number of Items on this page*/
        uriBuilder.appendQueryParameter("page-size", pageSize)
        /*Extra data to display, such as by-line, image and additional text*/
        uriBuilder.appendQueryParameter("show-fields", "trailText,thumbnail,byline")
        /**/
        uriBuilder.appendQueryParameter("api-key", "test")

        return GuardianStoryLoader(this, uriBuilder.toString())
    }

    /**
     * OnLoadFinish Method handles the loading of stories in the list,
     * If the list is empty, it shows the error view
     */
    override fun onLoadFinished(loader: Loader<List<GuardianStory>>, guardianStoryList: List<GuardianStory>?) {
        guardianStoryAdapter!!.clearData()
        if (guardianStoryList != null && !guardianStoryList.isEmpty()) {
            stories?.addAll(guardianStoryList)
            guardianStoryAdapter!!.notifyDataSetChanged()
            storyListView!!.visibility = View.VISIBLE
            progressBar!!.visibility = View.GONE
            hintView!!.visibility = View.GONE
        } else {
            storyListView!!.visibility = View.GONE
            progressBar!!.visibility = View.GONE
            hintView!!.visibility = View.VISIBLE
            hintView!!.setText(R.string.error_getting_data)
        }
    }

    //On Reset we want to clear out all data.
    override fun onLoaderReset(loader: Loader<List<GuardianStory>>) {
        guardianStoryAdapter!!.clearData()
    }

    companion object {

        /**
         * Constant value for the earthquake loader ID. We can choose any integer.
         * This really only comes into play if you're using multiple loaders.
         */
        private val STORY_LOADER_ID = 1

        /*Log Tag for the Activity*/
        private val LOG_TAG = GuardianActivity::class.java.name

        /*Endpoint of the URL to The Guardian*/
        private val GUARDIAN_URL = "https://content.guardianapis.com/"
    }
}
