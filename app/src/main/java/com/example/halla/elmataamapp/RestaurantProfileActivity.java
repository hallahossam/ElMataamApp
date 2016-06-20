package com.example.halla.elmataamapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.halla.elmataamapp.adapters.GridAdapter;

import org.json.JSONObject;

/**
 * Created by Halla on 13/06/2016.
 */
public class RestaurantProfileActivity extends AppCompatActivity {
    TextView rstaurantName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_restaurant_profile);

        GridView gridview = (GridView) findViewById(R.id.gv_resturantprofile);
        gridview.setAdapter(new GridAdapter(this));
        String resName = getIntent().getExtras().getString("resName");

        rstaurantName = (TextView) findViewById(R.id.tv_res_name);
        rstaurantName.setText(resName);

        String url = "https://elmataam.azurewebsites.net/api/Mobile/SearchJson?keyWord=" + resName;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("error",error.toString());
            }
        });

        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest);
    }
}
