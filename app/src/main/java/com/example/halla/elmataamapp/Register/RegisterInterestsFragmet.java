package com.example.halla.elmataamapp.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class RegisterInterestsFragmet extends Fragment implements View.OnClickListener {
    Button register;
    ImageButton addInterest;
    ExpandableHeightListView expandableHeightListView;
    EditText interest;
    ArrayList<String> interests;
    ListAdapter adapter;
    boolean firstTime = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_register_two, container, false);

        register = (Button) view.findViewById(R.id.btn_register);
        addInterest = (ImageButton) view.findViewById(R.id.btn_add_interest);
        interest = (EditText) view.findViewById(R.id.et_interests);

        expandableHeightListView = (ExpandableHeightListView) view.findViewById(R.id.elv_interests);

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
                }


        }
    }
}
