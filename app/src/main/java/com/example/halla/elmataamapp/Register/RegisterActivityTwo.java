package com.example.halla.elmataamapp.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.halla.elmataamapp.adapters.ListAdapter;
import com.example.halla.elmataamapp.R;
import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;

import java.util.ArrayList;

/**
 * Created by Halla on 30/05/2016.
 */
public class RegisterActivityTwo  extends AppCompatActivity implements View.OnClickListener {
    Button register;
    ImageButton addInterest;
    ExpandableHeightListView expandableHeightListView;
    EditText interest;
    ArrayList<String> interests;

    ListAdapter adapter;
    boolean firstTime = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_register_two);

        register = (Button) findViewById(R.id.btn_register);
        addInterest = (ImageButton) findViewById(R.id.btn_add_interest);
        interest = (EditText) findViewById(R.id.et_interests);
        interests = new ArrayList<String>();
        expandableHeightListView = (ExpandableHeightListView) findViewById(R.id.elv_interests);

        register.setOnClickListener(this);
        addInterest.setOnClickListener(this);

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
                        firstTime = false;
                        adapter = new ListAdapter(interests,this);
                        expandableHeightListView.setAdapter(adapter);
                        expandableHeightListView.setExpanded(true);

                    }
                   else{
                        adapter.notifyDataSetChanged();
                    }
                    interest.getText().clear();
                }


        }
    }
}

