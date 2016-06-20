package com.example.halla.elmataamapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.halla.elmataamapp.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendationFragment extends Fragment {

    ArrayList<String> resName, resRate, resImage, resBudget;

    public RecommendationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.frag_newfeed_recommendation,container,false);
        ListView listView = (ListView) view.findViewById(R.id.list_news);
        listView.setEmptyView(view.findViewById(R.id.tv_empty_list_newfeed));
        Bundle bundle = getArguments();
        String userId = bundle.getString("userId");
        return view;
    }


}
