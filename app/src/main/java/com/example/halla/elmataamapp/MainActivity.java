package com.example.halla.elmataamapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Button;

import com.example.halla.elmataamapp.register.RegisterActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SearchView.OnQueryTextListener {

    Button login, register;
    SearchView mSearchView;
    SearchManager mSearchManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_welcome);

        login = (Button) findViewById(R.id.btn_login_main);
        register = (Button) findViewById(R.id.btn_register_main);
        mSearchView = (SearchView) findViewById(R.id.sv_search);
        mSearchView.setIconified(false);
        mSearchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView.setSearchableInfo(mSearchManager.getSearchableInfo(getComponentName()));
        mSearchView.setOnQueryTextListener(MainActivity.this);
        mSearchView.setQueryHint(getResources().getString(R.string.search));
        mSearchView.setSubmitButtonEnabled(true);



        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btn_login_main:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;

            case R.id.btn_register_main:
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
                break;
        }
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);

        intent.putExtra("Query",query);
        startActivity(intent);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
