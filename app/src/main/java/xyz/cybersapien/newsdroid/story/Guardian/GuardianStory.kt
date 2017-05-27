package xyz.cybersapien.newsdroid.story.Guardian

import android.os.Build
import android.text.Html

/**
 * Created by ogcybersapien on 6/10/16.
 * A custom Object for GuardianStory
 * @param title Title of the GuardianStory
 *
 * @param subText trailText of the GuardianStory
 *
 * @param imgURL imageURL of the story
 *
 * @param byLine Name of the Author
 *
 * @param webLink URL to the story on the Guardian
 *
 * @param publicationDate date of the GuardianStory
 */

class GuardianStory(
        val title: String,
        private val subText: String,
        val imgURL: String,
        var byLine: String,
        val webLink: String,
        val publicationDate: String) {

    init {
        if (byLine.isNotEmpty()){
            byLine = "By " + byLine
        }
    }

    val trailingText: String
        get() {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N)
                return Html.fromHtml(subText).toString()
            else
                return Html.fromHtml(subText, 0).toString()
        }

    companion object {
        /*Default Value of the GuardianStory*/
        val byLineDefault = "NO_AUTHOR"
    }
}
