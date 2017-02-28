package xyz.cybersapien.newsdroid.network;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ogcybersapien on 2/28/2017.
 * The NetUtils Class is a general purpose Helper class
 * All the Different Query Helpers will trace back to this class for the base Request
 */

public class NetUtils {

    private static final String LOG_TAG = "NetUtils";
    /**
     * Create a private constructor because no one should ever create a {@link NetUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name NetUtils (and an object instance of NetUtils is not needed).
     */
    private NetUtils() {
    }

    /**
     * Custom static method to get the date from the Given format
     * @param dateTime String value of DateTime in format "yyyy-MM-dd'T'HH:mm:ss'Z'"
     * @return Date formatted to the phone's settings
     */
    public static String getDate(String dateTime) {
        if (dateTime != null) {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
                Date thisDate = simpleDateFormat.parse(dateTime);
                dateTime = SimpleDateFormat.getDateInstance().format(thisDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return dateTime;
    }

    /**
     * Return the URL from the string.
     * This method is used to give the fetchGuardianStories method a little abstraction and make sure that there is no crash in case of an error
     * @param urlString String url to convert to URL Object
     * @return URL object after conversion from String.
     */
    public static URL createURL(String urlString) {
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * Make an HTTP request from given URL and return a String as the response
     * @param url URL to get the data from
     * @return returns JSON String from data from the server
     * */
    public static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        /*If url is null no point conitnuing, so return early*/
        if (url == null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            //If request was successful, server will send a response code 200
            //Else it will send an error code. In case of the former, read the input stream.
            if (urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem with JSON result.");
        } finally {
            /*Close the urlConnection and input stream after we're done getting data*/
            if (urlConnection != null){
                urlConnection.disconnect();
            }

            if (inputStream != null){
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    public static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();

        if (inputStream!=null){
            InputStreamReader streamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(streamReader);
            String line = reader.readLine();
            while (line!=null){
                output.append(line);
                line = reader.readLine();
            };
        }
        return output.toString();
    }
}
