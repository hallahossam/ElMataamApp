package com.example.halla.elmataamapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.halla.elmataamapp.register.RegisterActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SearchView.OnQueryTextListener {

    Button login, register;
    SearchView mSearchView;
    SearchManager mSearchManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_welcome);

        login = (Button) findViewById(R.id.btn_login_main);
        register = (Button) findViewById(R.id.btn_register_main);
        mSearchView = (SearchView) findViewById(R.id.sv_search);
        mSearchView.setIconified(false);
        mSearchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView.setSearchableInfo(mSearchManager.getSearchableInfo(getComponentName()));
        mSearchView.setOnQueryTextListener(MainActivity.this);
        mSearchView.setQueryHint(getResources().getString(R.string.search));
        mSearchView.setSubmitButtonEnabled(true);



        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btn_login_main:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;

            case R.id.btn_register_main:
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
                break;
        }
    }


    @Override
    public boolean onQueryTextSubmit(final String query) {
        final String[] resp = new String[1];
        String url = "https://elmataam.azurewebsites.net/api/Mobile/SearchJson?keyWord=" + query;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.v("Response",response.toString());
                Toast.makeText(MainActivity.this,response.toString(),Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
//                intent.putExtra("Query",query);
//                intent.putExtra("Response",resp);
//                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("error",error.toString());
            }
        });

//        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                Log.v("Response",response);
//                Toast.makeText(MainActivity.this,response,Toast.LENGTH_SHORT).show();
////                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
////                intent.putExtra("Query",query);
////                intent.putExtra("Response",resp);
////                startActivity(intent);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.v("error",error.toString());
//            }
//        });
          ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest);

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
