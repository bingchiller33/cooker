package com.example.test.resolver;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.test.database.UserInfo;
import com.example.test.provider.DemoContract;

public class DemoContentResolver {
    private Context context = null;

    public DemoContentResolver(Context context) {
        this.context = context;
    }

    public UserInfo getUser(String username) {
        Uri uri = Uri.parse("content://" + DemoContract.AUTHORITY + "/" + DemoContract.CONTENT_PATH + "/" + username);
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        UserInfo userInfo = null;
        if (cursor.moveToNext()) {
            userInfo = new UserInfo();
            int index = cursor.getColumnIndex(DemoContract.ROLE);
            userInfo.setUsername(username);
            userInfo.setRole(cursor.getString(index));
            index = cursor.getColumnIndex(DemoContract.PASSWORD);
            userInfo.setPassword(cursor.getString(index));
        }
        return userInfo;
    }

    public void createUser(UserInfo userInfo) {
        ContentValues contentValues = new ContentValues();

        // Put data into contentValues
        contentValues.put(DemoContract.USER_NAME, userInfo.getUsername());
        contentValues.put(DemoContract.ROLE, userInfo.getRole());
        contentValues.put(DemoContract.PASSWORD, userInfo.getPassword());

        Uri uri = Uri.parse("content://" + DemoContract.AUTHORITY + "/" + DemoContract.CONTENT_PATH + "/" + userInfo);
        uri = context.getContentResolver().insert(uri, contentValues);
    }
}
