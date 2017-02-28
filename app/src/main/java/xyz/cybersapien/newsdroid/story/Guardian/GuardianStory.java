package xyz.cybersapien.newsdroid.story.Guardian;

import android.os.Build;
import android.text.Html;

/**
 * Created by ogcybersapien on 6/10/16.
 * A custom Object for GuardianStory
 */

public class GuardianStory {


    /*Title of the GuardianStory*/
    private String title;
    /*Trailing text of the GuardianStory*/
    private String subText;
    /*URL for the Image of the GuardianStory*/
    private String imgURL;
    /*Author of the GuardianStory*/
    private String byLine;
    /*Default Value of the GuardianStory*/
    public final static String byLineDefault = "NO_AUTHOR";
    /*URL to The Guardian page of the GuardianStory*/
    private String webLink;
    /*Publication date of the GuardianStory*/
    private String publicationDate;

    /**
     * Constructor for creating a new GuardianStory Object
     * @param title Title of the GuardianStory
     * @param subText trailText of the GuardianStory
     * @param imgURL imageURL of the story
     * @param byLine Name of the Author
     * @param webLink URL to the story on the Guardian
     * @param publicationDate date of the GuardianStory
     */
    public GuardianStory(String title, String subText, String imgURL, String byLine, String webLink, String publicationDate) {
        this.title = title;
        this.subText = subText;
        this.imgURL = imgURL;
        this.byLine = "By ".concat(byLine);
        this.webLink = webLink;
        this.publicationDate = publicationDate;
    }

    /**
     * Gets the String representation of the Title of the story
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the String representation of the trailing Text of the GuardianStory
     * @return Trailing Text
     */
    public String getTrailingText() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N)
            return Html.fromHtml(subText).toString();
        else
            return Html.fromHtml(subText, 0).toString();
    }

    /**
     * Gets the URL to the Image for the story in String form
     * @return Image URL
     */
    public String getImgURL() {
        return imgURL;
    }

    /**
     * Gets the String representation of the Author Name
     * @return Author Name
     */
    public String getByLine() {
        return byLine;
    }

    /**
     * Gets the URL to The Guardian web-page for the story in String form
     * @return URL
     */
    public String getWebLink() {
        return webLink;
    }

    /**
     * Gets the String representation of the Date of Publication
     * @return Publication Date
     */
    public String getPublicationDate() {
        return publicationDate;
    }
}
