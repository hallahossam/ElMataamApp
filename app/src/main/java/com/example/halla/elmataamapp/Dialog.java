package com.example.halla.elmataamapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by user on 3/17/2016.
 */
public class Dialog extends DialogFragment implements View.OnClickListener {
    EditText phoneNumber;
    Button cancel, send;

    public Dialog(){

    }

    public static Dialog newInstance (String title){
        Dialog frag = new Dialog();
        Bundle args = new Bundle();
        args.putString("Title", title);
        frag.setArguments(args);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_dialog, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        phoneNumber = (EditText) view.findViewById(R.id.et_phone);
        cancel = (Button) view.findViewById(R.id.btn_cancel);
        send = (Button) view.findViewById(R.id.btn_send);


        phoneNumber.requestFocus();
        cancel.setOnClickListener(this);
        send.setOnClickListener(this);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);;

    }

    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        android.app.Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btn_cancel:
                getDialog().dismiss();
                break;

        }

    }
}
