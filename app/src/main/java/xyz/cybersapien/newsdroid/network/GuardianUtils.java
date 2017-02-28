package xyz.cybersapien.newsdroid.network;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import xyz.cybersapien.newsdroid.story.GuardianStory;

/**
 * Created by ogcybersapien on 6/10/16.
 * This Class is a helper Class to be used for formatting and getting data from the network.
 * The Sole Purpose of this class is to keep the main classes clean and clutter free.
 */

public final class GuardianUtils {


    /** Tag for the log messages */
    private static final String LOG_TAG = GuardianUtils.class.getSimpleName();


    /**
     * Create a private constructor because no one should ever create a {@link GuardianUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name GuardianUtils (and an object instance of GuardianUtils is not needed).
     */
    private GuardianUtils() {
    }

    public static List<GuardianStory> fetchGuardianStories(String queryUrl) {

        URL url = NetUtils.createURL(queryUrl);

        String jsonResponse = null;

        try {
            jsonResponse = NetUtils.makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem getting HTTP data", e);
        }

        return getDataFromGJson(jsonResponse);
    }

    /**
     * Method to get the stories from The Guardian News Service API
     * This is obsolete
     * @param storyJSON JSON String Containing raw data
     * @return List of G-Stories Parsed from the JSON
     */
    public static List<GuardianStory> getDataFromGJson(String storyJSON){

        //If storyJSON is empty, return early
        if (storyJSON.isEmpty()){
            return null;
        }

        //Create an Empty List of Stories to Start
        List<GuardianStory> stories = new ArrayList<GuardianStory>();

        //Start parsing JSON data to add Stories to.
        try{
            //Create the root JSON Object from the reponse String
            JSONObject rootObject = new JSONObject(storyJSON);

            //Extract the response JSONOnject from the root Object
            JSONObject responseObject = rootObject.getJSONObject("response");

            //Get result Array from JSON
            JSONArray resultsArray = responseObject.getJSONArray("results");

            for (int i=0;i<resultsArray.length();i++){
                JSONObject currentStory = resultsArray.getJSONObject(i);

                //Get story Title
                String title = currentStory.getString("webTitle");

                //Get GuardianStory publication Date
                String pubDate = NetUtils.getDate(currentStory.getString("webPublicationDate"));

                //Get GuardianStory URL
                String storyURL = currentStory.getString("webUrl");

                //Get the fields JSON Object
                JSONObject currentStoryField = currentStory.getJSONObject("fields");

                //Get associated Image URL
                String storyImageURL = currentStoryField.getString("thumbnail");

                //Get the Authors Details
                String storyByLine = currentStoryField.optString("byline", GuardianStory.byLineDefault);

                //Get the trail Text
                String trailText = currentStoryField.getString("trailText");

                stories.add(new GuardianStory(title, trailText, storyImageURL, storyByLine, storyURL,pubDate));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return stories;
    }
}
