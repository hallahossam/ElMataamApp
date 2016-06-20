package com.example.halla.elmataamapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Halla on 20/06/2016.
 */
public class SearchTrustedActivity extends AppCompatActivity {
    TextView userName, userEmail;
    Button addTrusted;
    String trustedId;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_search_trusted);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setLogo(R.drawable.small_logo);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        userName = (TextView) findViewById(R.id.tv_name_trusted);
        userEmail = (TextView) findViewById(R.id.tv_email_trusted);
        addTrusted = (Button) findViewById(R.id.btn_add_trusted);

        String trustedUser = getIntent().getExtras().getString("Response");
        try {
            JSONObject object = new JSONObject(trustedUser);
            JSONArray jsonArray = object.getJSONArray("users");

           userName.setText(jsonArray.getJSONObject(0).getString("Name"));
            userEmail.setText(jsonArray.getJSONObject(0).getString("Email"));
            trustedId =jsonArray.getJSONObject(0).getString("id");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        sharedPreferences = getSharedPreferences("UserId", Context.MODE_PRIVATE);
        final String userId = sharedPreferences.getString("UserId","nothing");

        addTrusted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://elmataam.azurewebsites.net/api/Mobile/addTrusted";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("ADDTRUSTEd",response);
                        if(response.charAt(1) == 'T'){
                            Snackbar.make(userName, "User has been trusted",Snackbar.LENGTH_SHORT).show();
                            finish();
                        }
                        else{
                            Snackbar.make(userName, "Something went wrong, try again later",Snackbar.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("UserId",userId);
                        params.put("Friend",trustedId);
                        return params;
                    }
                };
                ApplicationController.getInstance().addToRequestQueue(stringRequest);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                onBackPressed();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

}
