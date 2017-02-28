package xyz.cybersapien.newsdroid.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import xyz.cybersapien.newsdroid.database.NewsContract.*;

/**
 * Created by ogcybersapien on 2/28/2017.
 * Helper Class to create and upgrade Databases in Android
 */

public class NewsDbHelper extends SQLiteOpenHelper {

    /* Name of the Database */
    public static final String DATABASE_NAME = "news.db";

    /**
     * Current Version of the Database
     * This needs to be updated when we make any upgrade changes to the database
     */
    public static final int DATABASE_VERSION = 1;

    public NewsDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /* Source Table */
        String SQL_CREATE_SOURCE_TABLE = "CREATE TABLE " + SourceEntry.TABLE_NAME
                + " (" + SourceEntry._ID + " TEXT PRIMARY KEY, "
                + SourceEntry.COLUMN_SOURCE_NAME + " TEXT NOT NULL, "
                + SourceEntry.COLUMN_SOURCE_DESC + " TEXT NOT NULL, "
                + SourceEntry.COLUMN_SOURCE_URL + " TEXT NOT NULL, "
                + SourceEntry.COLUMN_SOURCE_CATEGORY + " TEXT NOT NULL, "
                + SourceEntry.COLUMN_SOURCE_LANGUAGE + " TEXT NOT NULL, "
                + SourceEntry.COLUMN_SOURCE_COUNTRY + " TEXT NOT NULL, "
                + SourceEntry.COLUMN_SOURCE_LOGO_URL + " TEXT NOT NULL, "
                + SourceEntry.COLUMN_SOURCE_SORT + " INTEGER NOT NULL);";

        db.execSQL(SQL_CREATE_SOURCE_TABLE);

        String SQL_CREATE_ARTICLES_TABLE = "CREATE TABLE " + ArticleEntry.TABLE_NAME
                + " (" + ArticleEntry._ID + " INTEGER PRIMARY KEY AUTO INCREMENT, "
                + ArticleEntry.COLUMN_ARTICLE_TITLE + " TEXT NOT NULL, "
                + ArticleEntry.COLUMN_ARTICLE_DESC + " TEXT NOT NULL, "
                + ArticleEntry.COLUMN_ARTICLE_URL + " TEXT NOT NULL, "
                + ArticleEntry.COLUMN_ARTICLE_IMAGE + " TEXT NOT NULL, "
                + ArticleEntry.COLUMN_ARTICLE_PUBLISHED + " REAL NOT NULL, "
                + ArticleEntry.COLUMN_ARTICLE_AUTHOR + " TEXT NOT NULL, "
                + ArticleEntry.COLUMN_ARTICLE_SOURCE + "TEXT NOT NULL, "
                + "FOREIGN KEY(" + ArticleEntry.COLUMN_ARTICLE_SOURCE
                + ") REFERENCES " + SourceEntry.TABLE_NAME + "(" + SourceEntry._ID + ")"
                + ");";

        db.execSQL(SQL_CREATE_ARTICLES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
