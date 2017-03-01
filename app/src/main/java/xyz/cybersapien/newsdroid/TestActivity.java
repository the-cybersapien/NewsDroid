package xyz.cybersapien.newsdroid;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import java.util.List;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import xyz.cybersapien.newsdroid.database.NewsContract;
import xyz.cybersapien.newsdroid.sources.Source;
import xyz.cybersapien.newsdroid.sources.SourceAPI;
import xyz.cybersapien.newsdroid.sources.SourceAdapter;
import xyz.cybersapien.newsdroid.sources.SourceInterface;

public class TestActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String TAG = "TestActivity";
    private static final String[] SOURCE_COLUMNS = {

            NewsContract.SourceEntry.TABLE_NAME + "." + NewsContract.SourceEntry._ID,
            NewsContract.SourceEntry.COLUMN_SOURCE_NAME,
            NewsContract.SourceEntry.COLUMN_SOURCE_DESC,
            NewsContract.SourceEntry.COLUMN_SOURCE_LANGUAGE,
            NewsContract.SourceEntry.COLUMN_SOURCE_CATEGORY,
            NewsContract.SourceEntry.COLUMN_SOURCE_COUNTRY
    };

    /* Adapter for the sources */
    private SourceAdapter sourceAdapter;

    /* RecyclerView for the list */
    private RecyclerView sourceListView;

    /* Reference to the Progress Bar */
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        sourceAdapter = new SourceAdapter(this, null);

        sourceListView = (RecyclerView) findViewById(R.id.sources_list);
        sourceListView.setAdapter(sourceAdapter);

        sourceListView.setLayoutManager(new LinearLayoutManager(this));

        // TODO: Attach an onclick-Listener
        populateList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sources, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.refresh_sources:
                fetchSourcesFromAPI();
        }
        return super.onOptionsItemSelected(item);
    }

    void populateList(){
        getLoaderManager().initLoader(100, null, this);
    }

    void fetchSourcesFromAPI(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Log.d(TAG, "fetchSourcesFromAPI: Starting!");

        SourceInterface service = retrofit.create(SourceInterface.class);

        Call<SourceAPI> call = service.getSources();

        call.enqueue(new Callback<SourceAPI>() {
            @Override
            public void onResponse(Call<SourceAPI> call, Response<SourceAPI> response) {
                SourceAPI sourceAPI = response.body();
                List<Source> sourceList = sourceAPI.getSources();
                Vector<ContentValues> contentValuesVector = new Vector<ContentValues>(sourceList.size());

                getContentResolver().delete(NewsContract.SourceEntry.SOURCE_CONTENT_URI, null, null);

                for (Source source : sourceList) {
                    ContentValues cv = new ContentValues();
                    cv.put(NewsContract.SourceEntry.COLUMN_SOURCE_SID, source.getId());
                    cv.put(NewsContract.SourceEntry.COLUMN_SOURCE_NAME, source.getName());
                    cv.put(NewsContract.SourceEntry.COLUMN_SOURCE_DESC, source.getDescription());
                    cv.put(NewsContract.SourceEntry.COLUMN_SOURCE_URL, source.getUrl());
                    cv.put(NewsContract.SourceEntry.COLUMN_SOURCE_CATEGORY, source.getCategory());
                    cv.put(NewsContract.SourceEntry.COLUMN_SOURCE_LANGUAGE, source.getLanguage());
                    cv.put(NewsContract.SourceEntry.COLUMN_SOURCE_COUNTRY, source.getCountry());
                    int sortVal = 0;
                    for (String s : source.getSortBysAvailable()){
                        switch (s){
                            case "popular":
                                sortVal += 1;
                                break;
                            case "latest":
                                sortVal += 2;
                                break;
                            case "top":
                                sortVal += 4;
                                break;
                            default:
                                Log.d(TAG, "onResponse: Got Weird Response: " + s);
                        }
                    }
                    cv.put(NewsContract.SourceEntry.COLUMN_SOURCE_SORT, sortVal);
                    contentValuesVector.add(cv);
                }

                int inserted = 0;
                // add to database
                if (contentValuesVector.size() > 0){
                    // Call bulk insert for all the data
                    inserted = getContentResolver().bulkInsert(NewsContract.SourceEntry.SOURCE_CONTENT_URI, contentValuesVector.toArray(new ContentValues[contentValuesVector.size()]));
                }

                Log.d(TAG, "Task complete " + inserted + " inserted");
                populateList();
            }

            @Override
            public void onFailure(Call<SourceAPI> call, Throwable t) {

            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String sortOrder = NewsContract.SourceEntry.COLUMN_SOURCE_NAME + " ASC";

        Uri query = NewsContract.SourceEntry.SOURCE_CONTENT_URI;

        return new CursorLoader(this, query, SOURCE_COLUMNS, null, null, sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        sourceAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        sourceAdapter.swapCursor(null);
    }
}
