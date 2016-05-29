package com.example.halla.elmataamapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.halla.elmataamapp.DB.DbHelper;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText email, password, confirmPassword;
    Button register;
    CheckBox mCheckBox;
    CallingWebService mWebService;

    DbHelper helper = new DbHelper(RegisterActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_register);


        email = (EditText) findViewById(R.id.et_email_register);
        password = (EditText) findViewById(R.id.et_pass_register);
        confirmPassword = (EditText) findViewById(R.id.et_confirm_pass);

        mCheckBox = (CheckBox) findViewById(R.id.cb_conditions);
        register = (Button) findViewById(R.id.btn_register);

        register.setOnClickListener(this);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btn_register:
                String Email = email.getText().toString();
                String Pass = password.getText().toString();
                String confirm = confirmPassword.getText().toString();

                if(Email.isEmpty() || Pass.isEmpty() || confirm.isEmpty()){
                    Toast.makeText(RegisterActivity.this,"Please enter correct credentials",Toast.LENGTH_LONG).show();
                }
                else{
                    if(Pass.equals(confirm)) {
                        if(!mCheckBox.isChecked()){
                            Toast.makeText(RegisterActivity.this,"Can't register without accepting conditions",Toast.LENGTH_LONG).show();
                        }
                        else {
                            boolean result = helper.register(Email, Pass);
                            if (!result) {
                                Toast.makeText(RegisterActivity.this, "Account Already exists", Toast.LENGTH_LONG).show();
                            } else {
                                email.getText().clear();
                                password.getText().clear();
                                confirmPassword.getText().clear();
                                mCheckBox.setChecked(false);
                                mWebService = new CallingWebService(RegisterActivity.this);
                                String response = mWebService.Register("Register/test");
                                Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this, IndexActivity.class));
                                finish();
                            }
                        }
                    }
                    else{
                        Toast.makeText(RegisterActivity.this,"The Password doesn't match",Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }
}
