package com.example.halla.elmataamapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 3/17/2016.
 */
public class Dialog extends DialogFragment implements View.OnClickListener {
    EditText phoneNumber;
    Button cancel, send;
    static String temp;
    final String[] resId = new String[1];
    String finalId = "";

    public Dialog(){

    }

    public static Dialog newInstance (String title){
        Dialog frag = new Dialog();
        Bundle args = new Bundle();
        args.putString("Title", title);
        temp = title;
        frag.setArguments(args);
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_dialog, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        phoneNumber = (EditText) view.findViewById(R.id.et_phone);
        cancel = (Button) view.findViewById(R.id.btn_cancel);
        send = (Button) view.findViewById(R.id.btn_send);

        if(temp.equals("Add Trusted")){
            phoneNumber.setHint("Enter trusted email");
        }
        else if (temp.charAt(0) == 'C'){
            phoneNumber.setHint("Enter the restaurant name");
        }

        phoneNumber.requestFocus();
        cancel.setOnClickListener(this);
        send.setOnClickListener(this);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);;

    }


    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        android.app.Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btn_cancel:
                getDialog().dismiss();
                break;
            case R.id.btn_send:
                //Tobe added
                if(temp.charAt(0) == 'C'){

                    String url = "https://elmataam.azurewebsites.net/api/Mobile/SearchJson?keyWord=" + phoneNumber.getText().toString();
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                String id = "";
                                int cnt =0;
                                for(int i=11;i<temp.length();i++){
                                    id += temp.charAt(i);
                                }
Log.v("res",response.toString());

                                finalId = id;

                                final JSONArray array = response.getJSONArray("result");
                                resId[0] = array.getJSONObject(0).getString("id");
                                Log.v("id",resId[0]);
                                StringRequest stringRequest = new StringRequest(Request
                                        .Method.POST, "https://elmataam.azurewebsites.net/api/Mobile/checkIn", new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.v("Check in",response);
                                        if(response.charAt(1) == 't') {
                                            getDialog().dismiss();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }){
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String, String> params = new HashMap<>();

                                            params.put("userId", finalId);
                                            params.put("ResturantId",resId[0]);

                                        return params;
                                    }
                                };
                                ApplicationController.getInstance().addToRequestQueue(stringRequest);
                                Log.v(resId[0],"IDRES");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            Log.v("Response",response.toString());

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.v("error",error.toString());
                        }
                    });

                    ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest);



                }
                else {
                    String url = "https://elmataam.azurewebsites.net/api/Mobile/fndUserByEmail";
                    Map<String, String> params = new HashMap<>();
                    params.put("Email", phoneNumber.getText().toString());

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new JSONObject(params),
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.v("RESPONSEE", response.toString());
                                    phoneNumber.setText("");
                                    getDialog().dismiss();
                                    Intent intent = new Intent(getActivity(), SearchTrustedActivity.class);
                                    intent.putExtra("Response", response.toString());
                                    startActivity(intent);
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

                    ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest);
                }
                break;

        }

    }

    public interface VolleyCallback{
        void onSuccess(String result);
    }
}
