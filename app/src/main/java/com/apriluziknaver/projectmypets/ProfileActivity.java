package com.apriluziknaver.projectmypets;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class ProfileActivity extends AppCompatActivity {

    Typeface typeface;


    ImageView picPath;
    ImageView imgIc;
    TextView name;
    TextView birth;
    TextView breed;

    TextView gallery;
    TextView careNote;
    TextView training;
    ActionBar actionBar;

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();

        typeface = Typeface.createFromAsset(getAssets(), "fonts/SDMiSaeng.ttf");

        gallery = (TextView)findViewById(R.id.pf_in_gallery);
        gallery.setTypeface(typeface);

        careNote = (TextView)findViewById(R.id.pf_in_carenote);
        careNote.setTypeface(typeface);

        training = (TextView)findViewById(R.id.pf_in_training);
        training.setTypeface(typeface);


        picPath = (ImageView)findViewById(R.id.pf_in_img);
        imgIc = (ImageView)findViewById(R.id.pf_in_icon);
        name = (TextView)findViewById(R.id.pf_in_name);
        name.setTypeface(typeface);

        birth = (TextView)findViewById(R.id.pf_in_birth);
        birth.setTypeface(typeface);

        breed = (TextView)findViewById(R.id.pf_in_breed);
        breed.setTypeface(typeface);

        callIntent();

    }//onCreate


    public void callIntent(){
        intent = getIntent();

        //인텐트로 받아온거 정보 세팅
        String imgPath = intent.getStringExtra("PicPath").toString();

        Glide.with(this).load(imgPath).into(picPath);
        imgIc.setImageResource( intent.getIntExtra("Icon",0));
        String name2 = intent.getStringExtra("Name");

        name.setText(intent.getStringExtra("Name"));
        //폰트줄이기
        if(name2.length()>=10){
           name.setTextSize(24f);
// 폰트줄이는거 대신에 자르기?
        }else{
            name.setTextSize(32f);
        }


        birth.setText(intent.getStringExtra("Birth"));
        breed.setText(intent.getStringExtra("Breed") +" "+ intent.getStringExtra("Color"));

        actionBar.setTitle(" "+name2+" Profile ");

    }



    public void clickTraining(View v){


        Toast.makeText(this, "clickTraining", Toast.LENGTH_SHORT).show();
    }


    public void clickCareNote(View v){

        intent = new Intent(this,ScheduleCheckActivity.class);
        intent.putExtra("ProfileName",name.getText().toString());
        startActivity(intent);


        Toast.makeText(this, "clickCareNote", Toast.LENGTH_SHORT).show();
    }


    public void clickGallery(View v){
//
//        intent = new Intent(this,UserGalleryActivity.class);
//        intent.putExtra("ProfileName",name.getText().toString());
//        startActivity(intent);

        Toast.makeText(this, "clickGallery", Toast.LENGTH_SHORT).show();
    }





}
