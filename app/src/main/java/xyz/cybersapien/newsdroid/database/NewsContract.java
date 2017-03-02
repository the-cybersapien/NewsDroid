package xyz.cybersapien.newsdroid.database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by cybersapien on 28/2/17.
 * The NewsContract class is used to define a structure of the database.
 * The tables in the Stories database are as follows
 * 1. Sources
 * 2. Article
 */

public final class NewsContract {

    /*Content Authority for the Application database*/
    public static final String CONTENT_AUTHORITY =
            "xyz.cybersapien.newsdroid";

    /*Base URI with schema and Content Authority*/
    public static final Uri BASE_CONTENT_URI =
            Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_SOURCES = SourceEntry.TABLE_NAME;

    public static final String PATH_ARTICLE = ArticleEntry.TABLE_NAME;

    public static abstract class SourceEntry implements BaseColumns{

        /*Table Name*/
        public static final String TABLE_NAME = "sources";

        /*Content URI for the sources table */
        public static final Uri SOURCE_CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_SOURCES);

        /* Columns of the sources table*/
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_SOURCE_NAME = "name";
        public static final String COLUMN_SOURCE_SID = "sid";
        public static final String COLUMN_SOURCE_DESC = "desc";
        public static final String COLUMN_SOURCE_URL = "url";
        public static final String COLUMN_SOURCE_CATEGORY = "category";
        public static final String COLUMN_SOURCE_LANGUAGE = "lang";
        public static final String COLUMN_SOURCE_COUNTRY = "country";
        public static final String COLUMN_SOURCE_IMAGEURL = "img_url";
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

    public static abstract class ArticleEntry implements BaseColumns{

        /* Table Name */
        public static final String TABLE_NAME = "article";

        /* Content URI for Article table*/
        public static final Uri ARTICLE_CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_ARTICLE);

        /* Columns for Articles */
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_ARTICLE_AUTHOR = "author";
        public static final String COLUMN_ARTICLE_TITLE = "title";
        public static final String COLUMN_ARTICLE_DESC = "desc";
        public static final String COLUMN_ARTICLE_URL = "url";
        public static final String COLUMN_ARTICLE_IMAGE = "img";
        public static final String COLUMN_ARTICLE_PUBLISHED = "published";
        public static final String COLUMN_ARTICLE_ADDED = "added";
        public static final String COLUMN_ARTICLE_SOURCE = "source";

        /*Mime Types for different Content URIs*/
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ARTICLE;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ARTICLE;

    }
}