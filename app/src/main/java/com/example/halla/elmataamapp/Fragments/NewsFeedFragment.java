package com.example.halla.elmataamapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.halla.elmataamapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFeedFragment extends Fragment {


    public NewsFeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_rating,container,false);
        Bundle bundle = getArguments();
        String userId = bundle.getString("userId");
        Toast.makeText(getContext(),userId,Toast.LENGTH_LONG).show();
        return view;
    }


}
