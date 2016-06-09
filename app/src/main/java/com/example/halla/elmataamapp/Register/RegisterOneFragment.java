package com.example.halla.elmataamapp.register;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.halla.elmataamapp.R;

/**
 * Created by Halla on 09/06/2016.
 */
public class RegisterOneFragment extends Fragment implements View.OnClickListener {
    EditText email, password, confirmPassword, name, phone;
    Button save;
    CheckBox mCheckBox;
    String errorMessage = "Required";

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

        save.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
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
                            //      mWebService = new CallingWebService(RegisterActivity.this);
                            //     String response = mWebService.Register("Register/test");
                            //   Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                            ((OnComplete)getActivity()).onComplete(Email);
                            //  startActivity(new Intent(getContext(), IndexActivity.class));

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

    public static interface OnComplete{
        public abstract void onComplete (String userEmail);
    }
}
