package com.example.test.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MyDemoBoundService extends Service {
    public class MyBinder extends Binder {
        public MyDemoBoundService getMyDemoBoundService() {
            return MyDemoBoundService.this;
        }
    }

    private MyBinder myBinder = new MyBinder();

    private int count;

    public MyDemoBoundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        int input = intent.getIntExtra("COUNT", 0);
        count = input;
        count++;
        return myBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public int getCount() {
        count++;
        return count;
    }

}