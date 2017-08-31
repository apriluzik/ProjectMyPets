package com.apriluziknaver.projectmypets;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class AlarmNoteActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<AlarmItem> alarmItems = new ArrayList<>();
    AlarmAdapter alarmAdapter;
    FloatingActionButton fab;

    // TODO: 2017-08-30 언제(시간) 어디서(날짜) 무엇을(타이틀) 어떻게 (주기) 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_note);

        fab = (FloatingActionButton) findViewById(R.id.add_alarm);

        recyclerView  = (RecyclerView) findViewById(R.id.note_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        alarmAdapter = new AlarmAdapter(this,alarmItems);
        recyclerView.setAdapter(alarmAdapter);


    }

    // fab.OnClick
    public void addAlarms(View view){

        Intent intent = new Intent(AlarmNoteActivity.this,AlarmNoteDetailsActivity.class);
        startActivityForResult(intent,2962);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);






    }
}
