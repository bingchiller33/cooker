package com.example.test.provider;

import android.content.ContentResolver;
import android.content.UriMatcher;
import android.net.Uri;

public class DemoContract {
    public final static String AUTHORITY = "com.example.test.provider.DemoContentProvider";

    public final static String USER_TABLE = "USER_INFO";
    public final static String USER_NAME = "USER_NAME";
    public final static String PASSWORD = "PASSWORD";
    public final static String ROLE = "ROLE";
    public final static String CAMPUS = "CAMPUS";
    public final static String CONTENT_PATH = USER_TABLE;
    public final static Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + CONTENT_PATH);
    public final static int ONE_USER = 1;
    public final static int ALL_USERS = 2;
    public final static String ONE_USER_CONTENT_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/mt_user";
    public final static String ALL_USER_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/mt_user";
    public static UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, CONTENT_PATH + "/", ALL_USERS);
        URI_MATCHER.addURI(AUTHORITY, CONTENT_PATH + "/*", ONE_USER);
    }

}
