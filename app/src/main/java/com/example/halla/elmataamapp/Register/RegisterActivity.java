package com.example.halla.elmataamapp.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.halla.elmataamapp.CallingWebService;
import com.example.halla.elmataamapp.db.DbHelper;
import com.example.halla.elmataamapp.IndexActivity;
import com.example.halla.elmataamapp.R;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText email, password, confirmPassword, name, phone;
    Button save;
    CheckBox mCheckBox;
    CallingWebService mWebService;
    String errorMessage = "Required";

    DbHelper helper = new DbHelper(RegisterActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_register);

        email = (EditText) findViewById(R.id.et_email_register);
        password = (EditText) findViewById(R.id.et_pass_register);
        confirmPassword = (EditText) findViewById(R.id.et_confirm_pass);
        name = (EditText) findViewById(R.id.et_name);
        phone = (EditText) findViewById(R.id.et_phone);

        mCheckBox = (CheckBox) findViewById(R.id.cb_conditions);
        save = (Button) findViewById(R.id.btn_save);

        save.setOnClickListener(this);

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
            case R.id.btn_save:
                String Email = email.getText().toString();
                String Pass = password.getText().toString();
                String Confirm = confirmPassword.getText().toString();
                String Name = name.getText().toString();
                String Phone = phone.getText().toString();


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
                        else {
                            //Part DB Local h3mlha b3den
//                            boolean result = helper.register(Email, Pass);
//                            if (!result) {
//                                Toast.makeText(RegisterActivity.this, "Account Already exists", Toast.LENGTH_LONG).show();
//                            } else {
                            email.getText().clear();
                            password.getText().clear();
                            confirmPassword.getText().clear();
                            name.getText().clear();
                            phone.getText().clear();

                            mCheckBox.setChecked(false);
                            mWebService = new CallingWebService(RegisterActivity.this);
                            String response = mWebService.Register("Register/test");
                            Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, IndexActivity.class));
                            finish();
                            //  }
                        }
                    }
                    else{
                        confirmPassword.setError("Passwords Should match");
                    }
                }
                break;
        }
    }
}
