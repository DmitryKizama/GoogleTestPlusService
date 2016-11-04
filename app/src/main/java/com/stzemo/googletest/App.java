package com.stzemo.googletest;

import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;

import com.stzemo.googletest.notifications.CustomNotificationManager;

public class App extends Application {
    public static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        CustomNotificationManager.getInstance();
    }

    @Override
    public void unregisterComponentCallbacks(ComponentCallbacks callback) {
        super.unregisterComponentCallbacks(callback);
    }
}
