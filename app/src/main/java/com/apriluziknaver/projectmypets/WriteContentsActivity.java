package com.apriluziknaver.projectmypets;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class WriteContentsActivity extends AppCompatActivity {

    Intent intent;
    String url ="";
    ArrayList<WriteContents> contentses = new ArrayList<>();

    CircleImageView userImg;
    TextView userName;
    TextView userDate;

    EditText contentsTxt;
    ImageView contentsImg;
    VideoView contentsVideo;

    ImageView cancel;
    ImageView save;

    ActionBar actionBar;

    String imgPath;
    String name;
    String date;
    String time;// h:mm a
    String format="EEE, d MMM yyyy";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_write);

        actionBar = getSupportActionBar();
        actionBar.setTitle(" Communicated ");

        init();


    }


    public void init() {
        intent =getIntent();

        date = new SimpleDateFormat(format).format(new Date());

        userImg = (CircleImageView) findViewById(R.id.cm_userimg);
        userName = (TextView) findViewById(R.id.cm_name);
        userDate = (TextView) findViewById(R.id.cm_time);

        contentsTxt = (EditText) findViewById(R.id.cm_content_et);
        contentsImg = (ImageView) findViewById(R.id.cm_content_img);
        contentsVideo = (VideoView) findViewById(R.id.cm_content_video);
        cancel = (ImageView) findViewById(R.id.write_back);
        save = (ImageView) findViewById(R.id.write_save);

        name = intent.getStringExtra("Name");
        imgPath = intent.getStringExtra("ImgPath").toString();

        userName.setText(name);
        userDate.setText(date);
        Glide.with(this).load(imgPath).into(userImg);

        Log.d("USERINFO",userImg+"\n"+" date:"+userDate);

    }

    public void backBtn(View v){
        //글쓰기 액티비티종료 --> CommunityActivity로
        finish();

    }

    public void saveBtn(View v){

        Snackbar.make(v,"save",Snackbar.LENGTH_SHORT).show();


        //리사이클러에 리스트추가
        //WriteContents+
        //ReadContentsItem(list) , ContentsAdapter , CommunityActivity




    }
}
