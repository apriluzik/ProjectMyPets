package com.apriluziknaver.projectmypets;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class CommuActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ContentsAdpater adapter;
    ArrayList<ContentsItem> contentsItems=new ArrayList<>();

    FloatingActionButton fab;

    String serverUrl="http://kghy234.dothome.co.kr/Android/insertDB.php";
    String loadUrl="http://kghy234.dothome.co.kr/Android/loadDB,php";


    String userId;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commu);
        fab = (FloatingActionButton)findViewById(R.id.cm_fab);
        fab.setOnClickListener(fabListener);



        recyclerView = (RecyclerView)findViewById(R.id.cm_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true));
        adapter = new ContentsAdpater(this,contentsItems);
        recyclerView.setAdapter(adapter);

        contentsItems.add(new ContentsItem("user1","contents","yyyymmdd"));
        contentsItems.add(new ContentsItem("user2","contents","yyyymmdd"));
        contentsItems.add(new ContentsItem("user3","contents","yyyymmdd"));

        adapter.notifyDataSetChanged();

    }


    View.OnClickListener fabListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Toast.makeText(CommuActivity.this, "action", Toast.LENGTH_SHORT).show();


            Intent intent = new Intent(CommuActivity.this,SignUpActivity.class);
            startActivity(intent);

        }
    };


    public void checkId(){


        //// TODO: 2017-08-22 파라미터로 아이디받아 비교해서
        // TODO: 2017-08-22 불린값을 리턴(중복O:true / 중복X:false)







    }



}
