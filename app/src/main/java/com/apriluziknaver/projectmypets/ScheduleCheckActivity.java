package com.apriluziknaver.projectmypets;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
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

    String username;


    Toolbar toolbar;

    ImageView listIc;

    TabLayout tabLayout;
    ViewPager viewPager;



    ActionBar actionBar;
    ScheduleFragmentAdapter fragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_check);

        actionBar = getSupportActionBar();

        toolbar = (Toolbar) findViewById(R.id.toolbar_sc);
        listIc = (ImageView) findViewById(R.id.toggle_icon);


        //탭레이아웃 뷰페이저
        tabLayout = (TabLayout) findViewById(R.id.layout_tab_schedule);
        viewPager = (ViewPager) findViewById(R.id.viewpager_schedule);

        Intent ipIntent = getIntent();
        username = ipIntent.getStringExtra("ProfileName");

        fragmentAdapter = new ScheduleFragmentAdapter(getSupportFragmentManager(), username);

        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.white));
        tabLayout.setTabTextColors(getResources().getColor(R.color.PetSub2), getResources().getColor(R.color.white));

        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);

        actionBar.setTitle(" " + username + " ");



    }


}
