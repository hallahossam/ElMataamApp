package com.example.halla.elmataamapp.register;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.example.halla.elmataamapp.R;

/**
 * Created by Halla on 09/06/2016.
 */
public class RegisterActivity extends AppCompatActivity implements RegisterOneFragment.OnComplete {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_register);

        Fragment firstRegisterScreen = new RegisterOneFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment,firstRegisterScreen).commit();

    }

    @Override
    public void onComplete(String userEmail, String iserId) {
        Fragment secondRegisterScreen = new RegisterTwoFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment,secondRegisterScreen).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }
}
