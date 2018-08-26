package com.livesafemobile.android_places_demo_app.app;

import android.app.Application;

import com.livesafemobile.android_places_demo_app.services.Api;

public class App extends Application {

    private static App instance;
    public Api api;

    public static final String MY_LAT = "38.896255";
    public static final String MY_LNG = "-77.073494";

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        api = new Api();
    }

}
