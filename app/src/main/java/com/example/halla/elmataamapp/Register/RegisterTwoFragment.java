package com.example.halla.elmataamapp.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.halla.elmataamapp.ApplicationController;
import com.example.halla.elmataamapp.IndexActivity;
import com.example.halla.elmataamapp.adapters.ListAdapter;
import com.example.halla.elmataamapp.R;
import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Halla on 30/05/2016.
 */
public class RegisterTwoFragment extends Fragment implements View.OnClickListener {

    //TODO handling back button with proper action
    //Lw user 3ml add then delete then register
    //Handle it mn l adapter w est3';y l array list
    //Est5dmy interface

    Button register;
    ImageButton addInterest;
    ExpandableHeightListView expandableHeightListView;
    EditText interest;
    ArrayList<String> interests;
    ListAdapter adapter;
    String allInterests = "";
    boolean firstTime = true;
    String userEmail, userId, userName;



    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_two_register, container, false);

        register = (Button) view.findViewById(R.id.btn_register);
        addInterest = (ImageButton) view.findViewById(R.id.btn_add_interest);
        interest = (EditText) view.findViewById(R.id.et_interests);

        expandableHeightListView = (ExpandableHeightListView) view.findViewById(R.id.elv_interests);
        Bundle bundle = getArguments();
        userEmail = bundle.getString("userEmail");
        userId = bundle.getString("userId");


        interests = new ArrayList<>();
        register.setOnClickListener(this);
        addInterest.setOnClickListener(this);
        expandableHeightListView.setExpanded(true);
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_register:
                if(firstTime){
                    Snackbar.make(register,"At least add one interest to add your profile",Snackbar.LENGTH_LONG).show();
                }
                else{
                    //Send to WS
                    ArrayList<String> temp = adapter.interestList();
                    if(temp.isEmpty()){
                        Toast.makeText(getContext(),"Insert Interests to build your profile", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        for(int i=0;i<temp.size();i++){
                            if(i == 0)
                            {
                                allInterests += temp.get(i);
                            }
                           else
                                allInterests += "," + temp.get(i);
                        }
                        Toast.makeText(getContext(),allInterests, Toast.LENGTH_SHORT).show();
                        String url = "https://elmataam.azurewebsites.net/api/Mobile/registerInterestsJson";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.charAt(1) == 's'){
                                    Intent intent = new Intent(getContext(), IndexActivity.class);
                                    intent.putExtra("userID",userId);
                                    intent.putExtra("userName",userName);
                                    startActivity(intent);
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String> params = new HashMap<>();
                                params.put("UserId",userId);
                                params.put("interests",allInterests);
                                return params;
                            }
                        };
                        ApplicationController.getInstance().addToRequestQueue(stringRequest);
                    }
                }
                break;

            case R.id.btn_add_interest:
                String Interest = interest.getText().toString();
                if(Interest.isEmpty()){
                    interest.setError("Required");
                }

                else{
                    interests.add(Interest);
                    if(firstTime){
                        adapter = new ListAdapter(interests,getContext());
                        expandableHeightListView.setAdapter(adapter);
                        expandableHeightListView.setExpanded(true);
                        firstTime = false;
                    }
                   else{
                        adapter.notifyDataSetChanged();
                    }
                    interest.setText("");
                }


        }
    }


}
