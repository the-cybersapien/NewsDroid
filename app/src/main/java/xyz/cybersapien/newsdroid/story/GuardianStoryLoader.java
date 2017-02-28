package xyz.cybersapien.newsdroid.story;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

import xyz.cybersapien.newsdroid.network.GuardianUtils;

/**
 * Created by ogcybersapien on 7/10/16.
 * Custom Loader for the stories
 */

public class GuardianStoryLoader extends AsyncTaskLoader<List<GuardianStory>> {

    /* Query URL */
    private String queryUrl;

    public GuardianStoryLoader(Context context, String url) {
        super(context);
        queryUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<GuardianStory> loadInBackground() {
        if (queryUrl == null){
            return null;
        }

        //Perform the network request, parse the response, and extract the stories.
        return GuardianUtils.fetchGuardianStories(queryUrl);
    }
}
