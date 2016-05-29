package com.example.halla.elmataamapp;

import android.app.Application;
import android.content.Context;

/**
 * Created by user on 2/6/2016.
 */
public class ApplicationController extends Application {

    private static ApplicationController sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static  ApplicationController getInstance() {
        return sInstance;
    }

    public  static Context getAppContext(){
        return sInstance.getApplicationContext();
    }


}
