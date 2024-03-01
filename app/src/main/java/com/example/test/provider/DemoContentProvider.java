package com.example.test.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.test.database.DatabaseHandler;

public class DemoContentProvider extends ContentProvider {
    private DatabaseHandler databaseHandler = null;

    @Override
    public boolean onCreate() {
        databaseHandler = new DatabaseHandler(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        switch (DemoContract.URI_MATCHER.match(uri)) {
            case DemoContract.ONE_USER:
                String username = uri.getLastPathSegment();
                Cursor cursor = databaseHandler.getUserRecord(username);
                return cursor;
            case DemoContract.ALL_USERS:
                SQLiteDatabase sqLiteDatabase = databaseHandler.getReadableDatabase();
                Cursor cursor1 = sqLiteDatabase.query(DemoContract.USER_TABLE,
                        projection,
                        null, null,
                        null, null, sortOrder);
                return cursor1;
        }

        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (DemoContract.URI_MATCHER.match(uri)) {
            case DemoContract.ONE_USER:
                return DemoContract.ONE_USER_CONTENT_TYPE;
            case DemoContract.ALL_USERS:
                return DemoContract.ALL_USER_CONTENT_TYPE;
        }
        ;

        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        databaseHandler.getWritableDatabase().insert(DemoContract.USER_TABLE, null, values);
        Uri uri1 = Uri.parse(DemoContract.CONTENT_URI + "/" + values.get(DemoContract.USER_NAME));

        return uri1;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
