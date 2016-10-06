package xyz.cybersapien.newsdroid;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ogcybersapien on 6/10/16.
 * This Class is a helper Class to be used for formatting and getting data from the network.
 * The Sole Purpose of this class is to keep the main classes clean and clutter free.
 */

public class QueryUtils {


    /**
     * Custom static method to get the date from the Given format
     * @param dateTime String value of DateTime in format "yyyy-MM-dd'T'HH:mm:ss'Z'"
     * @return Date formatted to the phone's settings
     */
    public static String getDate(String dateTime) {
        if (dateTime != null) {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                Date thisDate = simpleDateFormat.parse(dateTime);
                dateTime = SimpleDateFormat.getDateInstance().format(thisDate);
                } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return dateTime;
    }



}
