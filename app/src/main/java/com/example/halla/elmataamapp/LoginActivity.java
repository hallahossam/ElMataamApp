package com.example.halla.elmataamapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.halla.elmataamapp.db.DbHelper;
import com.example.halla.elmataamapp.register.RegisterActivity;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView register, forgetPassword;
    EditText email, password;
    Button login;
    ProgressBar mProgressBar;
    SharedPreferences sharedPreferences;
    SharedPreferences userPreference;
    SharedPreferences.Editor userIdEditor;

    DbHelper mDbHelper = new DbHelper(LoginActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.act_login);

        email = (EditText) findViewById(R.id.et_email);
        password = (EditText) findViewById(R.id.et_pass);
        forgetPassword = (TextView) findViewById(R.id.tv_forgetpass);
        register = (TextView) findViewById(R.id.tv_register_login);
        login = (Button) findViewById(R.id.btn_login);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        forgetPassword.setOnClickListener(this);
        register.setOnClickListener(this);
        login.setOnClickListener(this);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }



    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.tv_forgetpass:
                Dialog.newInstance("Forget Password").show(getSupportFragmentManager(), "Yay!");
                break;

            case R.id.tv_register_login:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;

            case R.id.btn_login:
                final String Email = email.getText().toString();
                final String Pass = password.getText().toString();
                sharedPreferences = getSharedPreferences("RegistrationToken", Context.MODE_PRIVATE);
                final String registrationToken = sharedPreferences.getString("RegistrationToken","nothing");
                Log.v("REG",registrationToken);
                if (Email.isEmpty() || Pass.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter your credentials", Toast.LENGTH_LONG).show();
                } else {

                    String url = "https://elmataam.azurewebsites.net/api/Mobile/loginJson";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            mProgressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(LoginActivity.this,response,Toast.LENGTH_LONG).show();
                            if(response.charAt(1) == 'e' && response.charAt(2) == 'r' && response.charAt(3) == 'r') {
                                Snackbar.make(email,"Wrong email or password",Snackbar.LENGTH_LONG).show();
                            }
                            else{
                                email.getText().clear();
                                password.getText().clear();
                                Toast.makeText(LoginActivity.this, "Logged in", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LoginActivity.this, IndexActivity.class);
                                intent.putExtra("userEmail",Email);
                                String userId = "";
                                for(int i=1;i<response.length()-1;i++){
                                    userId+=response.charAt(i);
                                }
                                userPreference = getSharedPreferences("UserId", Context.MODE_PRIVATE);
                                userIdEditor = userPreference.edit();
                                userIdEditor.putString("UserId",userId);
                                userIdEditor.apply();
                                startActivity(intent);
                                finish();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params = new HashMap<>();
                            params.put("Email",Email);
                            params.put("Password",Pass);
                            params.put("token",registrationToken);
                            return params;
                        }
                    };
                    ApplicationController.getInstance().addToRequestQueue(stringRequest);
                    mProgressBar.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}
