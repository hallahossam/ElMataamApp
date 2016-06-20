package com.example.halla.elmataamapp.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.example.halla.elmataamapp.R;
import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by Halla on 15/06/2016.
 */
public class NotificationsListenerService extends GcmListenerService {
    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);
        sendNotification("Received: " + data.getString("message"),data.getString("contentTitle"));
    }
    private void sendNotification(String msg, String title) {

        Intent intent = new Intent(this, MessageView.class);
        intent.putExtra("Message", msg);
        intent.putExtra("Title",title);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(
                this).setSmallIcon(R.drawable.small_logo)
                .setContentTitle(title).setContentText(msg)
                .setAutoCancel(true).setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());

    }
}
