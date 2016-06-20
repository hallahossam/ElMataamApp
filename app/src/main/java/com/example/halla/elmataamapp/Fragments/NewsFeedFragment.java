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
public class NewsFeedFragment extends Fragment {

    ArrayList<String> resName, resRate, resImage, resBudget;

    public NewsFeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_newfeed_recommendation,container,false);
        ListView listView = (ListView) view.findViewById(R.id.list_news);
        Bundle bundle = getArguments();
        String userId = bundle.getString("userId");
        listView.setEmptyView(view.findViewById(R.id.tv_empty_list_newfeed));
       // Toast.makeText(getContext(),userId,Toast.LENGTH_LONG).show();
        return view;
    }


}
