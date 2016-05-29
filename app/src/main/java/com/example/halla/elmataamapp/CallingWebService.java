package com.example.halla.elmataamapp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;


public class CallingWebService {
    String url = "https://elmataam.azurewebsites.net/API/";
    final RequestQueue queue = VolleySingleton.getsInstance().getRequestQueue();
    Context mContext;

    public CallingWebService(Context context){
        mContext = context;
    }

    public String Register(String restUrl){
        final String[] resp = new String[1];
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url+ restUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                resp[0] = response+ "YAy";

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                resp[0] = error.getMessage();
            }
        });
        queue.add(stringRequest);
        return resp[0];
    }
}
