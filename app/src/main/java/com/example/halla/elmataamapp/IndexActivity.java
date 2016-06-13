package com.example.halla.elmataamapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.astuetz.PagerSlidingTabStrip;
import com.example.halla.elmataamapp.adapters.PagerAdapter;

import org.json.JSONObject;


/**
 * Created by user on 12/17/2015.
 */
public class IndexActivity extends AppCompatActivity implements android.support.v7.widget.SearchView.OnQueryTextListener{

    ViewPager pager;
    PagerAdapter adapter;
    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
       // sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        userEmail = getIntent().getExtras().getString("userEmail");

        adapter = new PagerAdapter(getSupportFragmentManager());
        pager = (ViewPager) findViewById(R.id.pager);

        pager.setAdapter(adapter);
        PagerSlidingTabStrip slidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        slidingTabStrip.setViewPager(pager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(IndexActivity.this);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Search L Mat3am");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){

            //    sharedPreferences.edit().remove("loginPrefs").commit();


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        String url = "https://elmataam.azurewebsites.net/api/Mobile/SearchJson?keyWord=" + s;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String jsonResponse = response.toString();
                Log.v("Response",jsonResponse);
                Toast.makeText(IndexActivity.this,response.toString(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(IndexActivity.this, SearchActivity.class);
                intent.putExtra("Response",jsonResponse);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("error",error.toString());
            }
        });

        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}
