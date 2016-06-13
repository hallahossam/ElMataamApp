package com.example.halla.elmataamapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.halla.elmataamapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class TrustedFragment extends Fragment {


    ListView listView;
    public TrustedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_trusted,container,false);
        listView = (ListView) view.findViewById(R.id.lv_trusted);
        listView.setEmptyView(view.findViewById(R.id.tv_empty_list));
        return view;
    }


}
