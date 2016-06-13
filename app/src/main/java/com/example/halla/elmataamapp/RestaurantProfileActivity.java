package com.example.halla.elmataamapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.example.halla.elmataamapp.adapters.GridAdapter;

/**
 * Created by Halla on 13/06/2016.
 */
public class RestaurantProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_restaurant_profile);

        GridView gridview = (GridView) findViewById(R.id.gv_resturantprofile);
        gridview.setAdapter(new GridAdapter(this));
    }
}
