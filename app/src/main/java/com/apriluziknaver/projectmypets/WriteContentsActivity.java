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
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class WriteContentsActivity extends AppCompatActivity {
    String writeUrl ="http://kghy234.dothome.co.kr/MyPets/writeBoard.php";
    String uploadimgUrl="http://kghy234.dothome.co.kr/MyPets/uploadimg/";
    Intent intent;

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
//    String contentsText;
//    String contentsImg;
//    String contentsVideo;

    String cImgPath;
    String cText;
    String cVideoPath;

    String dateANDtime;// h:mm a
    String format="EEE 요일, yyyy년 MMM d일";
    String format2 = "yyyy.MM.d, h:mm a";



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
        dateANDtime = new SimpleDateFormat(format2).format(new Date());

        userImg = (CircleImageView) findViewById(R.id.cm_userimg);
        userName = (TextView) findViewById(R.id.cm_name);
        userDate = (TextView) findViewById(R.id.cm_time);

        contentsTxt = (EditText) findViewById(R.id.cm_content_et);
        contentsImg = (ImageView) findViewById(R.id.cm_content_img);
        contentsVideo = (VideoView) findViewById(R.id.cm_content_video);


        cancel = (ImageView) findViewById(R.id.write_back);
        save = (ImageView) findViewById(R.id.write_save);

        name = intent.getStringExtra("Name");

        imgPath = intent.getStringExtra("ImgPath");

        userName.setText(name);
        userDate.setText(date);

        Glide.with(this).load("http://kghy234.dothome.co.kr/MyPets/uploadimg/"+imgPath).into(userImg);
        Log.d("imgpath",imgPath);


    }

    public void backBtn(View v){
        //글쓰기 액티비티종료 --> CommunityActivity로
        finish();

    }

    public void saveBtn(View v){


        Snackbar.make(v,"save",Snackbar.LENGTH_SHORT).show();
        cText = contentsTxt.getText().toString();
        cImgPath="Empty";
        cVideoPath ="Empty";

        //리사이클러에 리스트추가
        //WriteContents+
        //ReadContentsItem(list) , ContentsAdapter , CommunityActivity

new Thread(){
    @Override
    public void run() {

        MyPetsBoard();

    }
}.start();

        finish();

    }

    public void MyPetsBoard(){

        try {
            URL url = new URL(writeUrl);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");

            OutputStream os = conn.getOutputStream();
            if(cText==null) cText ="empty";
            if(cImgPath==null) cImgPath = "empty";
            if(cVideoPath==null) cVideoPath= "empty";

            String data = "username="+name+"&userimg="+imgPath+"&date="+dateANDtime+"&contentstext="+cText+
                    "&contentsimg="+cImgPath+"&contentsvideo="+cVideoPath;

            Log.d("contentsss",cImgPath+"\n"+cVideoPath+"\n"+cText);
            os.write(data.getBytes());
            os.flush();
            os.close();
            Log.d("WriteUp1",data);

            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line = reader.readLine();
            StringBuffer buffer  = new StringBuffer();

            while(line!=null){
                buffer.append(line);
                line = reader.readLine();
            }

            Log.d("WriteUp2",buffer.toString());



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
