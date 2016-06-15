package com.example.halla.elmataamapp.notification;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;

import com.example.halla.elmataamapp.R;

/**
 * Created by Halla on 15/06/2016.
 */
public class MessageView extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String message = getIntent().getExtras().getString("Message");
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.small_logo);
        mBuilder.setContentTitle("Notification Alert, Click Me!");
        mBuilder.setContentText(message);
    }
}
