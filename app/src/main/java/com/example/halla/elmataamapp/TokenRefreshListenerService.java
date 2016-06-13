package com.example.halla.elmataamapp;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by Halla on 12/06/2016.
 */
public class TokenRefreshListenerService extends InstanceIDListenerService {

    public void onTokenRefresh() {
        Intent i = new Intent(this, RegistrationService.class);
        startService(i);
    }
}
