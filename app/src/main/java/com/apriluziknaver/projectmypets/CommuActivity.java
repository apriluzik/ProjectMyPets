package com.apriluziknaver.projectmypets;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class CommuActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ContentsAdpater adapter;
    ArrayList<ContentsItem> contentsItems=new ArrayList<>();

    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commu);



        recyclerView = (RecyclerView)findViewById(R.id.cm_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true));
        adapter = new ContentsAdpater(this,contentsItems);
        recyclerView.setAdapter(adapter);

        contentsItems.add(new ContentsItem("user1","contents","yyyymmdd"));
        contentsItems.add(new ContentsItem("user2","contents","yyyymmdd"));
        contentsItems.add(new ContentsItem("user3","contents","yyyymmdd"));

        adapter.notifyDataSetChanged();

    }
}
