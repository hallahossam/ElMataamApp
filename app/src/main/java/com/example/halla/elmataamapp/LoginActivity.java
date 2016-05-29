package com.example.halla.elmataamapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.halla.elmataamapp.db.DbHelper;
import com.example.halla.elmataamapp.register.RegisterActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView register, forgetPassword;
    EditText email, password;
    Button login;

    //Button facebookLogin;
    CallbackManager mCallbackManager;
    DbHelper mDbHelper = new DbHelper(LoginActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginWithFacebook();

        setContentView(R.layout.act_login);

        email = (EditText) findViewById(R.id.et_email);
        password = (EditText) findViewById(R.id.et_pass);
        forgetPassword = (TextView) findViewById(R.id.tv_forgetpass);
        register = (TextView) findViewById(R.id.tv_register_login);
        login = (Button) findViewById(R.id.btn_login);

        forgetPassword.setOnClickListener(this);
        register.setOnClickListener(this);
        login.setOnClickListener(this);


        //facebookLogin = (Button) findViewById(R.id.btn_facebook_login);

       // facebookLogin.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
       //         LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "user_friends", "email"));
       //     }
       // });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(mCallbackManager.onActivityResult(requestCode,resultCode,data)){
            return;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

    public void loginWithFacebook(){

        FacebookSdk.sdkInitialize(this);
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(LoginActivity.this, "Success!", Toast.LENGTH_SHORT).show();

                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {

                                try {
                                    Log.v("LoginActivity", object.getString("first_name"));
                                    Log.v("LoginActivity", object.getString("last_name"));
                                    Log.v("LoginActivity", object.getString("gender"));
                                    Log.v("LoginActivity", object.getString("age_range"));
                                    Log.v("LoginActivity", object.getString("id"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                // Toast.makeText(MainActivity.this,fname + " "+lname+ " "+gender+ " "+ ageRange,Toast.LENGTH_SHORT).show();
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "first_name,last_name,gender,age_range");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();

            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Login Canceled!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(email.getWindowToken(), 0);
                String Email = email.getText().toString();
                String Pass = password.getText().toString();

                if (Email.isEmpty() || Pass.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter your credentials", Toast.LENGTH_LONG).show();
                } else {
                    String res = mDbHelper.login(Email, Pass);
                    if (res.isEmpty()) {
                        Toast.makeText(LoginActivity.this, "wrong credentials", Toast.LENGTH_LONG).show();
                    } else {
                        email.getText().clear();
                        password.getText().clear();
                        Toast.makeText(LoginActivity.this, "Logged in", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(LoginActivity.this, IndexActivity.class));
                        finish();
                    }
                }
                break;
        }
    }
}
