package com.example.test.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final static int DB_VERSION = 1;
    private final static String DB_NAME = "SampleDB";
    public final static String USER_TABLE = "USER_INFO";
    public final static String USER_NAME = "USER_NAME";
    public final static String PASSWORD = "PASSWORD";
    public final static String ROLE = "ROLE";
    public final static String CAMPUS = "CAMPUS";
    public final static String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " + USER_TABLE + "("
            + USER_NAME + " TEXT NOT NULL PRIMARY KEY," + PASSWORD + " TEXT,"
            + ROLE + " TEXT," + CAMPUS + " TEXT )";

    public DatabaseHandler(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (db.isOpen()) {
//            db.execSQL("DROP TABLE " + USER_TABLE);
            db.execSQL(CREATE_USER_TABLE);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public String insertUser(String username, String password, String role, String campus) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_NAME, username);
        values.put(PASSWORD, password);
        values.put(ROLE, role);
        values.put(CAMPUS, password);
        sqLiteDatabase.insert(USER_TABLE, null, values);
        return username;
    }

    public UserInfo getUser(String username) {
        String selectUser = "SELECT * FROM " + USER_TABLE + " WHERE " + USER_NAME + "=?";
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectUser, new String[]{username});
        UserInfo userInfo = null;
        if (cursor.moveToNext()) {
            userInfo = new UserInfo();
            int index = cursor.getColumnIndex(PASSWORD);
            userInfo.setPassword(cursor.getString(index));
            index = cursor.getColumnIndex(ROLE);
            userInfo.setRole(cursor.getColumnName(index));
            index = cursor.getColumnIndex(CAMPUS);
            userInfo.setCampus(cursor.getColumnName(index));
            userInfo.setUsername(username);
        }
        return userInfo;
    }
    public Cursor getUserRecord(String username) {
        String selectUser = "SELECT * FROM " + USER_TABLE + " WHERE " + USER_NAME + "=?";
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectUser, new String[]{username});

        return cursor
                ;
    }
}
