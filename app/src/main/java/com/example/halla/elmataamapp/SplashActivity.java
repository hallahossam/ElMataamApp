package com.example.halla.elmataamapp;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mikhaellopez.circularfillableloaders.CircularFillableLoaders;

/**
 * Created by user on 2/23/2016.
 */
public class SplashActivity extends AppCompatActivity {


    int progress = 0;
    android.os.Handler customHandler;
    CircularFillableLoaders circularFillableLoaders;
    boolean done = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_splash);


        circularFillableLoaders = (CircularFillableLoaders)findViewById(R.id.circularFillableLoaders);
         String android_id = Settings.Secure.getString(SplashActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        startService(new Intent(this,RegistrationService.class));
        Log.v("device id",android_id);
        customHandler = new android.os.Handler();
        customHandler.postDelayed(updateTimerThread, 500);
    }
    private Runnable updateTimerThread = new Runnable()
    {
        public void run()
        {
            //write here whaterver you want to repeat
            circularFillableLoaders.setProgress(progress);
            progress += 10;
            customHandler.postDelayed(this, 250);
            if(progress == 150){
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }
    };
}
