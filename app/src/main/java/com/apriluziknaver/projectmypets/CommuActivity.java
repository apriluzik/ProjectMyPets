package com.apriluziknaver.projectmypets;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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


        }
    };
}
