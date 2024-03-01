package com.example.test.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.test.entity.ProductEntity;

@Database(entities = {ProductEntity.class}, version = 1)
public abstract class ProductRoomDatabase extends RoomDatabase {
    public abstract ProductDAO productDAO();
    private static ProductRoomDatabase INSTANCE = null;
    public static ProductRoomDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (ProductRoomDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),ProductRoomDatabase.class, "Product_DB")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .enableMultiInstanceInvalidation()
                        .build();
            }
        }
        return INSTANCE;
    }
}
