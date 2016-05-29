package com.example.halla.elmataamapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.halla.elmataamapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendationFragment extends Fragment {


    public RecommendationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_rating,container,false);
    }


}
