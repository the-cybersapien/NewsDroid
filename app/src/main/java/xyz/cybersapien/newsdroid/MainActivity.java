package xyz.cybersapien.newsdroid;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Story>> {

    private String jsonData = "{\"response\":{\"currentPage\":1,\"pageSize\":10,\"pages\":5655,\"edition\":{\"webUrl\":\"https://www.theguardian.com/technology\",\"webTitle\":\"Technology\",\"apiUrl\":\"https://content.guardianapis.com/technology\",\"code\":\"default\",\"id\":\"technology\"},\"total\":56547,\"section\":{\"webUrl\":\"https://www.theguardian.com/technology\",\"webTitle\":\"Technology\",\"editions\":[{\"webUrl\":\"https://www.theguardian.com/technology\",\"webTitle\":\"Technology\",\"apiUrl\":\"https://content.guardianapis.com/technology\",\"code\":\"default\",\"id\":\"technology\"},{\"webUrl\":\"https://www.theguardian.com/au/technology\",\"webTitle\":\"Technology\",\"apiUrl\":\"https://content.guardianapis.com/au/technology\",\"code\":\"au\",\"id\":\"au/technology\"},{\"webUrl\":\"https://www.theguardian.com/uk/technology\",\"webTitle\":\"Technology\",\"apiUrl\":\"https://content.guardianapis.com/uk/technology\",\"code\":\"uk\",\"id\":\"uk/technology\"},{\"webUrl\":\"https://www.theguardian.com/us/technology\",\"webTitle\":\"Technology\",\"apiUrl\":\"https://content.guardianapis.com/us/technology\",\"code\":\"us\",\"id\":\"us/technology\"}],\"apiUrl\":\"https://content.guardianapis.com/technology\",\"id\":\"technology\"},\"userTier\":\"developer\",\"startIndex\":1,\"results\":[{\"webUrl\":\"https://www.theguardian.com/technology/2016/oct/06/snapchat-snap-inc-ipo-report\",\"webPublicationDate\":\"2016-10-06T20:38:03Z\",\"webTitle\":\"Snapchat company's planned IPO could put value at $25bn – report\",\"sectionName\":\"Technology\",\"apiUrl\":\"https://content.guardianapis.com/technology/2016/oct/06/snapchat-snap-inc-ipo-report\",\"id\":\"technology/2016/oct/06/snapchat-snap-inc-ipo-report\",\"isHosted\":false,\"fields\":{\"thumbnail\":\"https://media.guim.co.uk/bad572df5d406e6d4c1aa5180569ff488bc47386/0_145_3000_1800/500.jpg\",\"byline\":\"Sam Thielman in New York\",\"trailText\":\"The share sale of Snap Inc, which owns the popular picture and video-sharing app, would be largest on US stock exchange since 2014, if report proves true\"},\"sectionId\":\"technology\",\"type\":\"article\"},{\"webUrl\":\"https://www.theguardian.com/technology/2016/oct/06/duolingo-chatbots-learning-language\",\"webPublicationDate\":\"2016-10-06T15:00:57Z\",\"webTitle\":\"Can a chatbot teach you a foreign language? Duolingo thinks so\",\"sectionName\":\"Technology\",\"apiUrl\":\"https://content.guardianapis.com/technology/2016/oct/06/duolingo-chatbots-learning-language\",\"id\":\"technology/2016/oct/06/duolingo-chatbots-learning-language\",\"isHosted\":false,\"fields\":{\"thumbnail\":\"https://media.guim.co.uk/64994a1f9d21cc519b34b8226fb2abb87c140f7d/0_1_944_566/500.jpg\",\"byline\":\"Alex Hern\",\"trailText\":\"The less embarrassed you are, the better you tend to be at learning languages. The answer? Chatbots\"},\"sectionId\":\"technology\",\"type\":\"article\"},{\"webUrl\":\"https://www.theguardian.com/technology/2016/oct/06/gears-of-war-4-review-a-shot-in-the-arm-for-a-fading-series\",\"webPublicationDate\":\"2016-10-06T13:21:30Z\",\"webTitle\":\"Gears of War 4 review – a shot in the arm for a fading series\",\"sectionName\":\"Technology\",\"apiUrl\":\"https://content.guardianapis.com/technology/2016/oct/06/gears-of-war-4-review-a-shot-in-the-arm-for-a-fading-series\",\"id\":\"technology/2016/oct/06/gears-of-war-4-review-a-shot-in-the-arm-for-a-fading-series\",\"isHosted\":false,\"fields\":{\"thumbnail\":\"https://media.guim.co.uk/5bf2ad7ddcace38b954b1336b5dd343ef4abf74f/104_0_3092_1856/500.jpg\",\"byline\":\"Steve Boxer\",\"trailText\":\"A coherent single-player campaign and excellent online options bring this Xbox stalwart right back into the battle\"},\"sectionId\":\"technology\",\"type\":\"article\"},{\"webUrl\":\"https://www.theguardian.com/technology/2016/oct/06/yahoo-email-surveillance-government-nsa-fisa\",\"webPublicationDate\":\"2016-10-06T12:59:27Z\",\"webTitle\":\"Yahoo email surveillance: who approved the secret scanning program?\",\"sectionName\":\"Technology\",\"apiUrl\":\"https://content.guardianapis.com/technology/2016/oct/06/yahoo-email-surveillance-government-nsa-fisa\",\"id\":\"technology/2016/oct/06/yahoo-email-surveillance-government-nsa-fisa\",\"isHosted\":false,\"fields\":{\"thumbnail\":\"https://media.guim.co.uk/24614fbc0f1840c3354a2b220c30476de9681edd/0_293_3500_2100/500.jpg\",\"byline\":\"Sam Thielman\",\"trailText\":\"Neither the tech company nor the government will say who greenlighted custom program to scan users’ emails, but secret Fisa court and FBI are possibilities\"},\"sectionId\":\"technology\",\"type\":\"article\"},{\"webUrl\":\"https://www.theguardian.com/technology/2016/oct/06/playstation-vr-review-theres-magic-but-the-mainstream-is-a-way-off\",\"webPublicationDate\":\"2016-10-06T12:01:04Z\",\"webTitle\":\"PlayStation VR review – there's magic, but the mainstream is a way off\",\"sectionName\":\"Technology\",\"apiUrl\":\"https://content.guardianapis.com/technology/2016/oct/06/playstation-vr-review-theres-magic-but-the-mainstream-is-a-way-off\",\"id\":\"technology/2016/oct/06/playstation-vr-review-theres-magic-but-the-mainstream-is-a-way-off\",\"isHosted\":false,\"fields\":{\"thumbnail\":\"https://media.guim.co.uk/01ba933ebe022777d4ab6755cdc91b4675d93ee1/0_260_4023_2414/500.jpg\",\"byline\":\"Keith Stuart\",\"trailText\":\"Sony’s entry into the world of consumer virtual reality is an impressive start but it’s not yet the affordable high-end VR experience some are dreaming of\"},\"sectionId\":\"technology\",\"type\":\"article\"},{\"webUrl\":\"https://www.theguardian.com/technology/2016/oct/06/the-silly-game-helped-me-walk-again-readers-on-pokemon-go-three-months-on\",\"webPublicationDate\":\"2016-10-06T11:45:36Z\",\"webTitle\":\"'The silly game helped me walk again': readers on Pokémon Go three months on\",\"sectionName\":\"Technology\",\"apiUrl\":\"https://content.guardianapis.com/technology/2016/oct/06/the-silly-game-helped-me-walk-again-readers-on-pokemon-go-three-months-on\",\"id\":\"technology/2016/oct/06/the-silly-game-helped-me-walk-again-readers-on-pokemon-go-three-months-on\",\"isHosted\":false,\"fields\":{\"thumbnail\":\"https://media.guim.co.uk/8a83a8008b9461009de49eab2a022e2aed6b2a16/0_42_1456_874/500.jpg\",\"byline\":\"Rachel Obordo and Guardian readers\",\"trailText\":\"We asked whether you’re still catch ‘em all crazy three months since the game’s release. From hundreds of responses here’s what some of you said<br>\"},\"sectionId\":\"technology\",\"type\":\"article\"},{\"webUrl\":\"https://www.theguardian.com/technology/2016/oct/06/spotify-hit-by-malvertising-in-app\",\"webPublicationDate\":\"2016-10-06T10:31:11Z\",\"webTitle\":\"Spotify hit by 'malvertising' in app\",\"sectionName\":\"Technology\",\"apiUrl\":\"https://content.guardianapis.com/technology/2016/oct/06/spotify-hit-by-malvertising-in-app\",\"id\":\"technology/2016/oct/06/spotify-hit-by-malvertising-in-app\",\"isHosted\":false,\"fields\":{\"thumbnail\":\"https://media.guim.co.uk/8e23a9a450fdc4d31cf758ab5b2d75da5b2275e4/227_0_3073_1844/500.jpg\",\"byline\":\"Alex Hern\",\"trailText\":\"A malicious advert pushed through the free tier of the music streaming site has opened ‘questionable’ pop-ups” for some users\"},\"sectionId\":\"technology\",\"type\":\"article\"},{\"webUrl\":\"https://www.theguardian.com/technology/askjack/2016/oct/06/whats-the-best-software-for-editing-drone-videos\",\"webPublicationDate\":\"2016-10-06T08:40:52Z\",\"webTitle\":\"What’s the best software for editing drone videos?\",\"sectionName\":\"Technology\",\"apiUrl\":\"https://content.guardianapis.com/technology/askjack/2016/oct/06/whats-the-best-software-for-editing-drone-videos\",\"id\":\"technology/askjack/2016/oct/06/whats-the-best-software-for-editing-drone-videos\",\"isHosted\":false,\"fields\":{\"thumbnail\":\"https://media.guim.co.uk/1644e9db9d6cc083bd2c346aac1aa616511086bc/58_0_4354_2614/500.jpg\",\"byline\":\"Jack Schofield\",\"trailText\":\"Paul’s company wants to create professional-looking videos from drone camera footage. What are the options?\"},\"sectionId\":\"technology\",\"type\":\"article\"},{\"webUrl\":\"https://www.theguardian.com/technology/2016/oct/06/shell-drink-to-that-fake-instagram-louise-delage-profile-highlights-alcoholism\",\"webPublicationDate\":\"2016-10-06T06:34:05Z\",\"webTitle\":\"Who is Louise Delage?  New Instagram influencer not what she seems\",\"sectionName\":\"Technology\",\"apiUrl\":\"https://content.guardianapis.com/technology/2016/oct/06/shell-drink-to-that-fake-instagram-louise-delage-profile-highlights-alcoholism\",\"id\":\"technology/2016/oct/06/shell-drink-to-that-fake-instagram-louise-delage-profile-highlights-alcoholism\",\"isHosted\":false,\"fields\":{\"thumbnail\":\"https://media.guim.co.uk/bceffa0d573d10c6489da9d9d631368d958f31ce/0_24_600_360/500.jpg\",\"byline\":\"Elle Hunt\",\"trailText\":\"Ad agency creation attracts 65,000 followers after 150 posts – every one of which shows 25-year-old Parisian with alcohol\"},\"sectionId\":\"technology\",\"type\":\"article\"},{\"webUrl\":\"https://www.theguardian.com/technology/2016/oct/06/chatterbox-thursday\",\"webPublicationDate\":\"2016-10-06T06:00:00Z\",\"webTitle\":\"Chatterbox: Thursday\",\"sectionName\":\"Technology\",\"apiUrl\":\"https://content.guardianapis.com/technology/2016/oct/06/chatterbox-thursday\",\"id\":\"technology/2016/oct/06/chatterbox-thursday\",\"isHosted\":false,\"fields\":{\"thumbnail\":\"https://media.guim.co.uk/8716528322e3bd72e9d5b961a7b5c0e38d5017f9/0_3_800_480/500.jpg\",\"trailText\":\"The place to talk about games and other things that matter\"},\"sectionId\":\"technology\",\"type\":\"article\"}],\"status\":\"ok\"}}";

    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int STORY_LOADER_ID = 1;

    /*Log Tag for the Activity*/
    private static final String LOG_TAG = MainActivity.class.getName();

    /*Endpoint of the URL to The Guardian*/
    private static final String GUARDIAN_URL = "https://content.guardianapis.com/";

    /*Adapter for the stories*/
    private StoryAdapter storyAdapter;

    /*Create the Recycler View*/
    private RecyclerView storyListView;

    /*Reference to the progress Bar*/
    private ProgressBar progressBar;

    /*Get a reference to the LoaderManager to intereact with the Loaders*/
    private LoaderManager loaderManager;

    /*Reference to the error(hint) TextView*/
    private TextView hintView;

    /*Reference the stories.*/
    List<Story> stories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loaderManager = getLoaderManager();

        //Initialize Progress bar
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //Initialize TextView
        hintView = (TextView) findViewById(R.id.errorHint);

        //Create RecyclerView
        storyListView = (RecyclerView) findViewById(R.id.stories_list);
        storyListView.setVisibility(View.GONE);

        //Create Story list;
        stories = new ArrayList<Story>();
        //get StoryAdapter
        storyAdapter = new StoryAdapter(this, stories);

        storyListView.setAdapter(storyAdapter);

        storyListView.setLayoutManager(new LinearLayoutManager(this));

        startLoad();
    }

    private void startLoad(){

        //Create the connectivity Manager and NetworkInfo  objects to check for Network connectivity
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo!=null && networkInfo.isConnected()){
            //Initialize the loader. Pass the itn ID constant defined above and pass in null
            loaderManager.initLoader(STORY_LOADER_ID, null, this);
        } else {
            hintView.setText("No Internet Connection.");
            progressBar.setVisibility(View.GONE);
            hintView.setVisibility(View.VISIBLE);
            storyListView.setVisibility(View.GONE);
        }
    }


    @Override
    public Loader<List<Story>> onCreateLoader(int id, Bundle args) {
        progressBar.setVisibility(View.VISIBLE);
        hintView.setVisibility(View.GONE);

        Uri baseUri = Uri.parse(GUARDIAN_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendPath("technology");
        uriBuilder.appendQueryParameter("format", "json");
        uriBuilder.appendQueryParameter("show-fields","trailText,thumbnail,byline");
        uriBuilder.appendQueryParameter("api-key","5a290564-de02-4140-ad58-84bb0b4aa87d");

        return new StoryLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Story>> loader, List<Story> storyList) {
        storyAdapter.clearData();
        if (storyList!=null && !storyList.isEmpty()){
            stories.addAll(storyList);
            storyAdapter.notifyDataSetChanged();
            storyListView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            hintView.setVisibility(View.GONE);
        } else {
            storyListView.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            hintView.setVisibility(View.VISIBLE);
            hintView.setText("Error Getting Data");
        }
    }

    //On Reset we want to clear out all data.
    @Override
    public void onLoaderReset(Loader<List<Story>> loader) {
        storyAdapter.clearData();
    }
}
