package com.example.halla.elmataamapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.halla.elmataamapp.adapters.CustomAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class SearchActivity extends AppCompatActivity implements FloatingActionMenu.MenuStateChangeListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    CustomAdapter mCustomAdapter;
    ListView mListView;
    ArrayList<String> resName;
    ArrayList<String>  resRate;
     RelativeLayout layout;
    FloatingActionMenu actionMenu;
    String lan,lot;

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private Location mLastLocation;

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;
    private static final String TAG = SearchActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setLogo(R.drawable.small_logo);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        resName = new ArrayList<>();
        resRate = new ArrayList<>();
        String queryResult = getIntent().getExtras().getString("Response");
        try {
            JSONObject jsonObject = new JSONObject(queryResult);
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            int length = jsonArray.length();
            JSONObject object;
            for(int i=0;i<length;i++){
                object = jsonArray.getJSONObject(i);
                resName.add(object.getString("Name"));
                resRate.add(object.getString("OverallRate"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        final RelativeLayout layout = (RelativeLayout) findViewById(R.id.rl);
        if (checkPlayServices()) {

            // Building the GoogleApi client
            buildGoogleApiClient();
        }


        mListView = (ListView) findViewById(R.id.lv_search);
        ImageView icon = new ImageView(this); // Create an icon
        icon.setImageResource(R.drawable.filter);

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .build();

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
// repeat many times:
        ImageView itemIcon = new ImageView(this);
        itemIcon.setImageResource( R.drawable.interests );
        SubActionButton button1 = itemBuilder.setContentView(itemIcon).build();

        ImageView itemIcon2 = new ImageView(this);
        itemIcon2.setImageResource(R.drawable.trusted);
        SubActionButton button2 = itemBuilder.setContentView(itemIcon2).build();

        ImageView itemIcon3 = new ImageView(this);
        itemIcon3.setImageResource( R.drawable.location );
        SubActionButton button3 = itemBuilder.setContentView(itemIcon3).build();

        layout.setVisibility(View.VISIBLE);
         actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(button1)
                .addSubActionView(button2)
                .addSubActionView(button3)
                .attachTo(actionButton)
                .build();


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SearchActivity.this,"Interests",Toast.LENGTH_SHORT).show();

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SearchActivity.this,"Trusted",Toast.LENGTH_SHORT).show();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String ,String> params = new HashMap<>();
                params.put("lan",lan);
                params.put("longitude",lot);
                String url = "https://elmataam.azurewebsites.net/api/Mobile/Nearby";
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new JSONObject(params), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //   Toast.makeText(SearchActivity.this,response.toString(),Toast.LENGTH_LONG).show();
                        try {
                            JSONArray jsonArray = response.getJSONArray("result");
                            int length = jsonArray.length();
                            JSONObject object;
                            JSONObject restaurants;
                            resName.clear();
                            resRate.clear();
                            for(int i=0;i<length;i++){
                                object = jsonArray.getJSONObject(i);
                                restaurants = object.getJSONObject("resturant");
                                resName.add(restaurants.getString("Name"));
                                resRate.add(restaurants.getString("ExpertRate"));

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mCustomAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SearchActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                });
                ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest);
            }
        });


        mCustomAdapter = new CustomAdapter(resName, resRate, SearchActivity.this);
        mListView.setAdapter(mCustomAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchActivity.this,RestaurantProfileActivity.class);
                intent.putExtra("resName",resName.get(position));
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });


    }

    @Override
    public void onMenuOpened(FloatingActionMenu floatingActionMenu) {
        layout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onMenuClosed(FloatingActionMenu floatingActionMenu) {
        layout.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
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
            //Toast.makeText(SearchActivity.this,String.valueOf(latitude) + " , " + String.valueOf(longitude),Toast.LENGTH_LONG).show();
             lan = String.valueOf(latitude);
             lot =   String.valueOf(longitude);
        } else {
            Snackbar.make(mListView,"Couldn't get the location. Make sure location is enabled on the device",Snackbar.LENGTH_LONG).show();
          //  Toast.makeText(SearchActivity.this,"Couldn't get the location. Make sure location is enabled on the device",Toast.LENGTH_LONG).show();

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
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

}
