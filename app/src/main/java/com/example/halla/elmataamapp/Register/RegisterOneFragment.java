package com.example.halla.elmataamapp.register;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.halla.elmataamapp.ApplicationController;
import com.example.halla.elmataamapp.R;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Halla on 09/06/2016.
 */
public class RegisterOneFragment extends Fragment implements View.OnClickListener {


    EditText email, password, confirmPassword, name, phone;
    Button save;
    CheckBox mCheckBox;
    String errorMessage = "Required";
    SharedPreferences sharedPreferences;
    ProgressBar mProgressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_one_register, container, false);

        email = (EditText) view.findViewById(R.id.et_email_register);
        password = (EditText) view.findViewById(R.id.et_pass_register);
        confirmPassword = (EditText) view.findViewById(R.id.et_confirm_pass);
        name = (EditText) view.findViewById(R.id.et_name);
        phone = (EditText) view.findViewById(R.id.et_phone);

        mCheckBox = (CheckBox) view.findViewById(R.id.cb_conditions);
        save = (Button) view.findViewById(R.id.btn_save);

        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        save.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_save:
                final String Email = email.getText().toString();
                final String Pass = password.getText().toString();
                String Confirm = confirmPassword.getText().toString();
                final String Name = name.getText().toString();
                final String Phone = phone.getText().toString();

                if(Email.isEmpty()  || Pass.isEmpty() || Confirm.isEmpty() || Name.isEmpty() || Phone.isEmpty()){
                    if(Email.isEmpty()){
                        email.setError(errorMessage);
                    }
                    else if(Pass.isEmpty()){
                        password.setError(errorMessage);
                    }
                    else if(Confirm.isEmpty()){
                        confirmPassword.setError(errorMessage);
                    }
                    else if(Name.isEmpty()){
                        name.setError(errorMessage);
                    }
                    else{
                        phone.setError(errorMessage);
                    }

                }

                else{
                    if(Pass.equals(Confirm)) {
                        if(!mCheckBox.isChecked()){
                            mCheckBox.setError(errorMessage);
                        }
                        else{
                            String url = "https://elmataam.azurewebsites.net/api/Mobile/registerJson";

                            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    mProgressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(getContext(),response,Toast.LENGTH_SHORT).show();
                                    if(response.charAt(1) == 'S'){
                                        Snackbar.make(email,response,Snackbar.LENGTH_SHORT).show();
                                    }
                                    else if (response.charAt(1) == 'U'){
                                        Snackbar.make(email,response,Snackbar.LENGTH_SHORT).show();
                                    }
                                    else{
                                        String userId = "";
                                        for(int i=1;i<response.length()-1;i++){
                                            userId+=response.charAt(i);
                                        }
                                        email.getText().clear();
                                        password.getText().clear();
                                        confirmPassword.getText().clear();
                                        name.getText().clear();
                                        phone.getText().clear();
                                        mCheckBox.setChecked(false);
                                        ((OnComplete)getActivity()).onComplete(Email, userId);
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Snackbar.make(mCheckBox,error.toString(),Snackbar.LENGTH_LONG).show();
                                }
                            }){
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String,String> params = new HashMap<>();
                                    params.put("Email",Email);
                                    params.put("Password",Pass);
                                    params.put("Name",Name);
                                    params.put("Phone",Phone);
                                    return params;
                                }
                            };
                            ApplicationController.getInstance().addToRequestQueue(stringRequest);
                            mProgressBar.setVisibility(View.VISIBLE);
                        }
                    }
                    else{
                        confirmPassword.setError("Passwords Should match");
                    }
                }
                break;
        }
    }

    public static interface OnComplete{
        public abstract void onComplete (String userEmail, String userId);
    }
}
