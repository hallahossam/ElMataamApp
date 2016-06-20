package com.example.halla.elmataamapp.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;

import com.example.halla.elmataamapp.R;
import com.example.halla.elmataamapp.RestaurantProfileActivity;

/**
 * Created by Halla on 15/06/2016.
 */
public class MessageView extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String message = getIntent().getExtras().getString("Message");
        //TODO get title
        String title = getIntent().getExtras().getString("Title");
     //   int mNotificationId = 001;

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle(title);
        mBuilder.setContentText(message);

        //This i added, To be tested
//        mBuilder.setContentIntent( PendingIntent.getActivity(this, 0,
//                new Intent(this, RestaurantProfileActivity.class), PendingIntent.FLAG_UPDATE_CURRENT));
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
//                new Intent(this, RestaurantProfileActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
//        mBuilder.setContentIntent(contentIntent);
//
//
//        // Gets an instance of the NotificationManager service
//        NotificationManager mNotifyMgr =
//                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//
//        // Builds the notification and issues it.
//        mNotifyMgr.notify(mNotificationId, mBuilder.build());

    }
}
