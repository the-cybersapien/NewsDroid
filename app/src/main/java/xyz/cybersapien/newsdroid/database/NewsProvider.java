package xyz.cybersapien.newsdroid.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.text.TextUtils;

/**
 * Created by ogcybersapien on 2/28/2017.
 * The NewsProvider Class acts as the middleman between the application database and the driving code
 * This ensures that databases are used efficiently, and safely.
 */

public class NewsProvider extends ContentProvider {

    /* Log Tag*/
    private static final String TAG = "NewsProvider";

    /*News Database Helper Object to create/upgrade Databases as and when needed */
    private NewsDbHelper newsDbHelper;

    /* Codes for different URI Matches from Source Table */
    public static final int SOURCE = 100;
    public static final int SOURCE_ID = 101;

    /* Codes for different URI Matches from Articles Table*/
    public static final int ARTICLE = 200;
    public static final int ARTICLE_ID = 201;

    /* URI Object for Matching URIs to appropriate Tasks */
    private static final UriMatcher newsUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        newsUriMatcher.addURI(NewsContract.CONTENT_AUTHORITY, NewsContract.PATH_SOURCES, SOURCE);
        newsUriMatcher.addURI(NewsContract.CONTENT_AUTHORITY, NewsContract.PATH_SOURCES + "/#", SOURCE_ID);
        newsUriMatcher.addURI(NewsContract.CONTENT_AUTHORITY, NewsContract.PATH_ARTICLE, ARTICLE);
        newsUriMatcher.addURI(NewsContract.CONTENT_AUTHORITY, NewsContract.PATH_ARTICLE + "/#", ARTICLE_ID);
    }

    @Override
    public boolean onCreate() {
        newsDbHelper = new NewsDbHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // Get a readable Database
        SQLiteDatabase db = newsDbHelper.getReadableDatabase();

        Cursor cursor = null;

        int match = newsUriMatcher.match(uri);
        switch (match){

            /*To get Sources List, match will contain value SOURCE*/
            case SOURCE:
                cursor = db.query(NewsContract.SourceEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            /* To get a single source, match will contain value SOURCE_ID*/
            case SOURCE_ID:
                selection = NewsContract.SourceEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(NewsContract.SourceEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            /* To get Articles List, match will contain value ARTICLE */
            case ARTICLE:
                cursor = db.query(NewsContract.ArticleEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            /* To get a particular Article, match will contain value ARTICLE_ID */
            case ARTICLE_ID:
                selection = NewsContract.ArticleEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(NewsContract.ArticleEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            /* If nothing matches, then inform Houston that we have a problem */
            default:
                throw new IllegalArgumentException("Cannot find match for specified URI");
        }

        // Set a cursor notification to notify change
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {

        int match = newsUriMatcher.match(uri);
        switch (match){
            case SOURCE:
                return NewsContract.SourceEntry.CONTENT_LIST_TYPE;
            case SOURCE_ID:
                return NewsContract.SourceEntry.CONTENT_ITEM_TYPE;
            case ARTICLE:
                return NewsContract.SourceEntry.CONTENT_LIST_TYPE;
            case ARTICLE_ID:
                return NewsContract.SourceEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Cannot find match for specified URI");
        }
    }

    // TODO: Write Logic for the following methods for all the tables

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
