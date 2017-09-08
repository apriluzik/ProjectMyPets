package com.apriluziknaver.projectmypets;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AlarmNoteActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<AlarmItem> alarmItems = new ArrayList<>();
    AlarmAdapter alarmAdapter;
    FloatingActionButton fab;
    String name;
    DBHelper helper;
    Typeface typeface;
    TextView exTitle;

    // TODO: 2017-08-30 언제(시간) 어디서(날짜) 무엇을(타이틀) 어떻게 (주기) 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_note);
        typeface = Typeface.createFromAsset(getAssets(), "fonts/SDMiSaeng.ttf");
        exTitle = (TextView)findViewById(R.id.ex_title);
        exTitle.setTypeface(typeface);


        fab = (FloatingActionButton) findViewById(R.id.add_alarm);
        name=getIntent().getStringExtra("ProfileName");

        recyclerView = (RecyclerView) findViewById(R.id.note_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        alarmAdapter = new AlarmAdapter(this, alarmItems);
        recyclerView.setAdapter(alarmAdapter);

        helper=new DBHelper(this,"pets",null,1);

        loadDB();
    }

    // fab.OnClick
    public void addAlarms(View view) {

        Intent intent = new Intent(AlarmNoteActivity.this, AlarmNoteDetailsActivity.class);

        intent.putExtra("ProfileName",name);
        startActivityForResult(intent, 2962);


    }

    public void updateAlarms(View view){
        if(view.getTag()!=null){
            alarmItems=alarmAdapter.items;

            for(int i=0;i<alarmItems.size();i++){
                if(alarmItems.get(i).id==(Integer) view.getTag()){
                    Intent intent = new Intent(AlarmNoteActivity.this, AlarmNoteDetailsActivity.class);
                    intent.putExtra("ProfileName",name);
                    intent.putExtra("item",alarmItems.get(i));
                    startActivityForResult(intent, 2962);
                }
            }

            Toast.makeText(this, view.getTag().toString(), Toast.LENGTH_SHORT).show();
        }else {
            addAlarms(view);
        }



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        loadDB();

        switch (requestCode) {
            case 2962:

                if (resultCode != RESULT_OK) {
                    Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
                    return;
                }

                AlarmItem item = data.getParcelableExtra("Alarm");
                alarmItems.add(item);
                break;
        }

        alarmAdapter.notifyDataSetChanged();
    }

    public void loadDB(){
        alarmAdapter.refresh(helper.getItem(name));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(R.anim.normal,R.anim.note_out);
    }
}
