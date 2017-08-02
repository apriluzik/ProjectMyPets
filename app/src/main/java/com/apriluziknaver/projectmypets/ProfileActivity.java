package com.apriluziknaver.projectmypets;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });//onClick
    }



    public void clickTraining(View v){


        Toast.makeText(this, "clickTraining", Toast.LENGTH_SHORT).show();
    }


    public void clickCareNote(View v){


        Toast.makeText(this, "clickCareNote", Toast.LENGTH_SHORT).show();
    }


    public void clickGallery(View v){


        Toast.makeText(this, "clickGallery", Toast.LENGTH_SHORT).show();
    }



}
