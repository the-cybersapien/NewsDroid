package xyz.cybersapien.newsdroid.database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by cybersapien on 28/2/17.
 * The NewsContract class is used to define a structure of the database.
 * The tables in the Stories database are as follows
 * 1. Sources
 * 2. Story
 */

public final class NewsContract {

    /*Content Authority for the Application database*/
    public static final String CONTENT_AUTHORITY =
            "xyz.cybersapien.newsdroid";

    /*Base URI with schema and Content Authority*/
    public static final Uri BASE_CONTENT_URI =
            Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_SOURCES = SourceEntry.TABLE_NAME;

    public static final String PATH_STORY = StoryEntry.TABLE_NAME;

    public static abstract class SourceEntry implements BaseColumns{

        /*Table Name*/
        public static final String TABLE_NAME = "sources";

        /*Content URI for the sources table */
        public static final Uri SOURCE_CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_SOURCES);

        /* Columns of the sources table*/
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_SOURCE_NAME = "name";
        public static final String COLUMN_SOURCE_DESC = "desc";
        public static final String COLUMN_SOURCE_URL = "url";
        public static final String COLUMN_SOURCE_CATEGORY = "category";
        public static final String COLUMN_SOURCE_LANGUAGE = "lang";
        public static final String COLUMN_SOURCE_COUNTRY = "country";
        public static final String COLUMN_SOURCE_LOGO_URL = "logo_url";
        /**
         * The sort Works in the following manner:
         * Add 1 for popular
         * Add 2 for latest
         * Add 4 for top
         * This way, we have 7 different combinations, stored in a easy manner
         * Just like linux permissions
         */
        public static final String COLUMN_SOURCE_SORT = "sort";

        /* Mime Types for different Content URIs*/
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SOURCES;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SOURCES;

    }

    public static abstract class StoryEntry implements BaseColumns{

        /* Table Name */
        public static final String TABLE_NAME = "story";

        /* Content URI for Story table*/
        public static final Uri STORY_CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_STORY);

        /* Columns for stories */
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_STORY_AUTHOR = "author";
        public static final String COLUMN_STORY_TITLE = "title";
        public static final String COLUMN_STORY_DESC = "desc";
        public static final String COLUMN_STORY_URL = "url";
        public static final String COLUMN_STORY_IMAGE = "img";
        public static final String COLUMN_STORY_PUBLISHED = "published";
        public static final String COLUMN_STORY_SOURCE = "source";

        /*Mime Types for different Content URIs*/
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_STORY;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_STORY;

    }
}