package com.example.halla.elmataamapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.halla.elmataamapp.ApplicationController;
import com.example.halla.elmataamapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }

    TextView userName, userEmail;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_user_profile,container,false);
        Bundle bundle = getArguments();
        String userId = bundle.getString("userId");
        String [] userProfile = bundle.getStringArray("userProfile");
        userName = (TextView) view.findViewById(R.id.tv_userName_userProfile);
        userEmail = (TextView) view.findViewById(R.id.tv_userEmail_userProfile);


        userName.setText(userProfile[1]);
        userEmail.setText(userProfile[0]);
        return view;
    }


}
