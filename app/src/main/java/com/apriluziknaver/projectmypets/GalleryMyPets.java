package com.apriluziknaver.projectmypets;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

public class GalleryMyPets extends AppCompatActivity {
    private String name;
    private TextView mTextMessage;
    Intent intent;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    home();
                    return true;
                case R.id.navigation_camera:
                    camera();
                    return true;
                case R.id.navigation_gallery:
                    gallery();
                    return true;
            }
            return false;
        }

    };

    ///

    RecyclerView recyclerView;
    ArrayList<GalleryItems> galleryItems = new ArrayList<>();
    GalleryAdapter galleryAdapter ;
    ActionBar actionBar;
    StaggeredGridLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pets_gallery);

        intent = getIntent();
        name = intent.getStringExtra("ProfileName");
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.gall_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        manager = new StaggeredGridLayoutManager(3,1);
        recyclerView = (RecyclerView)findViewById(R.id.gall_recycler);
        galleryAdapter = new GalleryAdapter(galleryItems,this);
        recyclerView.setLayoutManager(manager);


    }

    public void home(){
        mTextMessage.setText(R.string.title_home);
    }

    public void camera(){
        mTextMessage.setText("Camera");
    }

    public void gallery(){
        mTextMessage.setText("Gallery");

        galleryItems.add(new GalleryItems("123123",R.drawable.zava));
        galleryItems.add(new GalleryItems("234234",R.drawable.heart));
        galleryItems.add(new GalleryItems("345345",R.drawable.home));
        galleryItems.add(new GalleryItems("456456",R.drawable.ma9ni));
        galleryItems.add(new GalleryItems("123123",R.drawable.zava));
        galleryItems.add(new GalleryItems("234234",R.drawable.heart));
        galleryItems.add(new GalleryItems("345345",R.drawable.home));
        galleryItems.add(new GalleryItems("456456",R.drawable.ma9ni));

        galleryAdapter.notifyDataSetChanged();
    }

}
