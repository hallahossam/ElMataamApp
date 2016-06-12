package com.example.halla.elmataamapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mikhaellopez.circularfillableloaders.CircularFillableLoaders;

/**
 * Created by user on 2/23/2016.
 */
public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    int progress = 0;
    android.os.Handler customHandler;
    CircularFillableLoaders circularFillableLoaders;
    boolean done = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_splash);
        circularFillableLoaders = (CircularFillableLoaders)findViewById(R.id.circularFillableLoaders);

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
