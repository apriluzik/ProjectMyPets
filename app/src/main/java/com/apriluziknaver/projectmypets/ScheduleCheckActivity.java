package com.apriluziknaver.projectmypets;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class ScheduleCheckActivity extends AppCompatActivity {


    // TODO: 2017-08-07 사용자 정의 리스트말고 기본폼(?)으로 있는 리스트만들기 (그래프에 필요함)
    // TODO: 2017-08-07 카테고리를 체크노트 하나 아래에 1개이상의 카테고리로 묶기 
    // TODO: 2017-08-07 리스트(펫프로필)당 하나씩 사진첩 존재하기 
    // TODO: 2017-08-07 DB만들기................... 
    


    ToggleButton toggle;
    Toolbar toolbar;
    FloatingActionButton fabSc;
    RecyclerView recyclerSc;
    ArrayList<ScheduleListItem> lists = new ArrayList<>();
    ScheduleListAdapter scAdapter;
    AlertDialog.Builder alert;

    //추가한항목 텍스트 저장
    String textValue;

    ImageView listIc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_check);

        toggle = (ToggleButton) findViewById(R.id.list_toggle_sc);
        toolbar = (Toolbar) findViewById(R.id.toolbar_sc);
        listIc = (ImageView) findViewById(R.id.toggle_icon);

        fabSc = (FloatingActionButton)findViewById(R.id.fab_sc);
        recyclerSc = (RecyclerView) findViewById(R.id.sc_recycler);
        recyclerSc.setLayoutManager( new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        scAdapter = new ScheduleListAdapter(this,lists);

        recyclerSc.setAdapter(scAdapter);

        fabSc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addLists();
                Log.d("VALUe",textValue+"");

                Log.d("lists.size",lists.size()+"");

                scAdapter.notifyDataSetChanged();

            }
        });
        ////플로팅버튼





    }








    public void addLists(){


        // TODO: 2017-08-07 리스트항목추가(Dialog)
        final EditText msg = new EditText(ScheduleCheckActivity.this);
        alert=new AlertDialog.Builder(ScheduleCheckActivity.this);
        alert.setView(msg);
        msg.setInputType(InputType.TYPE_CLASS_TEXT);

        alert.setTitle("항목 추가");

        //확인버튼
        alert.setPositiveButton("추가", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                textValue = msg.getText().toString();
                lists.add(new ScheduleListItem(textValue));
                Log.d("VALUe",textValue+"");


                dialogInterface.dismiss();
            }
        });

        //취소버튼
        alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();


            }
        });
        alert.show();
    }

}
