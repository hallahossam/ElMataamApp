package com.example.halla.elmataamapp.notification;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.halla.elmataamapp.R;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

/**
 * Created by Halla on 15/06/2016.
 */
public class RegistrationService extends IntentService {

    SharedPreferences registrationTokenPreference;
    SharedPreferences.Editor editor;

    public RegistrationService() {
        super("RegistrationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        InstanceID myID = InstanceID.getInstance(this);
        try {
            String registrationToken = myID.getToken(
                    getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE,
                    null);
            Log.d("Registration Token", registrationToken);
            registrationTokenPreference = getSharedPreferences("RegistrationToken", Context.MODE_PRIVATE);
            editor = registrationTokenPreference.edit();
            editor.putString("RegistrationToken",registrationToken);
            editor.apply();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
