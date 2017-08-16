package com.apriluziknaver.projectmypets;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class UserGalleryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<GalleryItem> galleryItems = new ArrayList<>();
    GalleryAdapter galleryAdapter;
    Intent intent;
    GridLayoutManager manager;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_gallery);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


            }
        });

        intent = getIntent();
        intent.getStringExtra("ProfileName");



        recyclerView = (RecyclerView) findViewById(R.id.gr_recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        galleryAdapter = new GalleryAdapter(this,galleryItems);

        recyclerView.setAdapter(galleryAdapter);

        galleryAdapter.notifyDataSetChanged();



    }
}
