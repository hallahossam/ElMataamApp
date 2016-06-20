package com.example.halla.elmataamapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
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
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONObject;


/**
 * Created by user on 12/17/2015.
 */
public class IndexActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    ViewPager pager;
    PagerAdapter adapter;
    String userEmail, userId;
    FloatingActionButton checkIn;

    String lan,lot;

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private android.location.Location mLastLocation;

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;
    private static final String TAG = IndexActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        // sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        userEmail = getIntent().getExtras().getString("userEmail");
        userId = getIntent().getExtras().getString("userID");

        adapter = new PagerAdapter(getSupportFragmentManager(), userId);
        pager = (ViewPager) findViewById(R.id.pager);
        checkIn = (FloatingActionButton) findViewById(R.id.fab_add_trusted);
        checkIn.setOnClickListener(this);

        pager.setAdapter(adapter);
        PagerSlidingTabStrip slidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        slidingTabStrip.setViewPager(pager);

        if (checkPlayServices()) {
            // Building the GoogleApi client
            buildGoogleApiClient();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        switch (id) {
            case R.id.check_in:
                Log.v("Locationssss",lan + " " + lot);
                break;
            //    sharedPreferences.edit().remove("loginPrefs").commit();


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        String url = "https://elmataam.azurewebsites.net/api/Mobile/SearchJson?keyWord=" + s;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String jsonResponse = response.toString();
                Log.v("Response", jsonResponse);
                Toast.makeText(IndexActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(IndexActivity.this, SearchActivity.class);
                intent.putExtra("Response", jsonResponse);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("error", error.toString());
            }
        });

        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            //This was added
            case R.id.fab_add_trusted:
                Dialog.newInstance("Add Trusted").show(getSupportFragmentManager(), "Yay!");
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        checkPlayServices();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        displayLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());
    }

    private void displayLocation() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {
            double latitude = mLastLocation.getLatitude();
            double longitude = mLastLocation.getLongitude();
            Toast.makeText(IndexActivity.this,String.valueOf(latitude) + " , " + String.valueOf(longitude),Toast.LENGTH_LONG).show();
            lan = String.valueOf(latitude);
            lot =   String.valueOf(longitude);
        } else {
            Toast.makeText(IndexActivity.this,"Couldn't get the location. Make sure location is enabled on the device",Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Creating google api client object
     * */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }
}
