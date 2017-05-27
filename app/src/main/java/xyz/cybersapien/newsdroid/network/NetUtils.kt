package xyz.cybersapien.newsdroid.network;

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by ogcybersapien on 2/28/2017.
 * The NetUtils file contains standard helper functions to be used in multiple places
 * This is code will be removed in future release as the migration to NewsAPI is completed
 * All the Different Query Helpers will trace back to this class for the base Request
 * Create a private constructor because no one should ever create a NetUtils object.
 * This class is only meant to hold static variables and methods, which can be accessed
 * directly from the class name NetUtils (and an object instance of NetUtils is not needed).
 */

private val LOG_TAG = "NetUtils"

/**
 * Custom  method to get the date from the Given format
 * @param dateTime String value of DateTime in format "yyyy-MM-dd'T'HH:mm:ss'Z'"
 * @return Date formatted to the phone's settings
 */
fun getDate(dateTime: String): String{
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
    val thisDate = simpleDateFormat.parse(dateTime)
    return SimpleDateFormat.getDateInstance().format(thisDate)
}

/**
 * Return the URL from the string.
 * This method is used to give the fetchGuardianStories method a little abstraction and make sure that there is no crash in case of an error
 * @param urlString String url to convert to URL Object
 * @return URL object after conversion from String.
 */
fun createURL(urlString: String): URL {
    return URL(urlString)
}

/**
 * Make an HTTP request from given URL and return a String as the response
 * @param url URL to get the data from
 * @return returns JSON String from data from the server
 * */
fun makeHttpRequest(url: URL): String {
    var jsonResponse = ""

    val urlConnection = url.openConnection() as HttpURLConnection
    urlConnection.readTimeout = 10000
    urlConnection.connectTimeout = 15000
    urlConnection.requestMethod = "GET"
    urlConnection.connect()

    if (urlConnection.responseCode == 200) {
        val inputStream = urlConnection.inputStream
        val output = StringBuilder()
        if (inputStream != null){
            val inputStreamReader = InputStreamReader(inputStream, Charset.forName("UTF-8"))
            val bufferedReader = BufferedReader(inputStreamReader)
            var line: String? = bufferedReader.readLine()
            while (!line.isNullOrEmpty()) {
                output.append(line)
                line = bufferedReader.readLine()
            }
        }
        jsonResponse = output.toString()
        inputStream.close()
    }
    urlConnection.disconnect()
    return jsonResponse
}
