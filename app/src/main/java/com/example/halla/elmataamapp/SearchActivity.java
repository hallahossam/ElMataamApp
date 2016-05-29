package com.example.halla.elmataamapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.halla.elmataamapp.Adapters.CustomAdapter;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;


public class SearchActivity extends AppCompatActivity implements FloatingActionMenu.MenuStateChangeListener{

    CustomAdapter mCustomAdapter;
    ListView mListView;
    String [] resName;
    String [] resRate;
     RelativeLayout layout;
    FloatingActionMenu actionMenu;
    //int [] resImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setLogo(R.drawable.small_logo);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        String queryRestaurant = getIntent().getExtras().getString("Query");
        Toast.makeText(SearchActivity.this, queryRestaurant, Toast.LENGTH_SHORT).show();
        resName = new String[]{"Astoria", "Prego", "Macdonald's"};
        resRate = new String[] {"4/5","5/5","3.7"};
        final RelativeLayout layout = (RelativeLayout) findViewById(R.id.rl);
        //  resImage = new int [] {R.drawable.phone, R.drawable.recommendation, R.drawable.rate};

        mListView = (ListView) findViewById(R.id.lv_search);
        ImageView icon = new ImageView(this); // Create an icon
        icon.setImageResource(R.drawable.filter);

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .build();

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
// repeat many times:
        ImageView itemIcon = new ImageView(this);
        itemIcon.setImageResource( R.drawable.interests );
        SubActionButton button1 = itemBuilder.setContentView(itemIcon).build();

        ImageView itemIcon2 = new ImageView(this);
        itemIcon2.setImageResource(R.drawable.trusted);
        SubActionButton button2 = itemBuilder.setContentView(itemIcon2).build();

        ImageView itemIcon3 = new ImageView(this);
        itemIcon3.setImageResource( R.drawable.location );
        SubActionButton button3 = itemBuilder.setContentView(itemIcon3).build();

        layout.setVisibility(View.VISIBLE);
         actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(button1)
                .addSubActionView(button2)
                .addSubActionView(button3)
                .attachTo(actionButton)
                .build();


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SearchActivity.this,"Interests",Toast.LENGTH_SHORT).show();

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SearchActivity.this,"Trusted",Toast.LENGTH_SHORT).show();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SearchActivity.this,"Location",Toast.LENGTH_SHORT).show();
            }
        });


        mCustomAdapter = new CustomAdapter(resName, resRate, SearchActivity.this);
        mListView.setAdapter(mCustomAdapter);


    }

    @Override
    public void onMenuOpened(FloatingActionMenu floatingActionMenu) {
        layout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onMenuClosed(FloatingActionMenu floatingActionMenu) {
        layout.setVisibility(View.VISIBLE);
    }
}
