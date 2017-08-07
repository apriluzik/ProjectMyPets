package com.apriluziknaver.projectmypets;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

   /* TODO: 2017-08-07 저장할 사용자 정보 :
            [애견] 이름, 성별, 생년월일, 품종, 색 (--프로필),
                    체크노트에 적은 항목들, 체크노트의 체크(일일스케줄체크)횟수 */

//app_bar_main 에 fab


    Typeface typeface;
    ActionBar actionBar;
    FloatingActionButton fab;

    ArrayList<ProfileListItem> profiles = new ArrayList<>();
    RecyclerView pf_RecyclerView;
    RecyclerAdapterContent adapterContent;
    RecyclerView.LayoutManager manager;

    //펫 추가 햇는지 (펫추가 창에서 확인 누르면 true)
    boolean isResist = false;

    Intent intent;
    ProfileListItem profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // FloatingActionButton
        fab = (FloatingActionButton) findViewById(R.id.fab);


        //기본
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

///////////////////////////////////////////////////////////////////////////////////////////////////////////


        typeface = Typeface.createFromAsset(getAssets(), "fonts/SDMiSaeng.ttf");


        actionBar = getSupportActionBar();
        actionBar.setTitle(" My Pets ");


        // fab리스너 ( 프로필 추가 )
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddProfile();
            }
        });


//펫리스트 기본값 =비어잇음.
//프로필추가후 리스트로 보여주기

        //펫리스트
        pf_RecyclerView = (RecyclerView) findViewById(R.id.pf_recycler);
        adapterContent = new RecyclerAdapterContent(this, profiles);
        pf_RecyclerView.setAdapter(adapterContent);
        manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        pf_RecyclerView.setLayoutManager(manager);
        adapterContent.notifyDataSetChanged();
        //기본화면


    }//Main Activity


    //fab
    public void AddProfile() {
        isResist = true;
        intent = new Intent(this,AddProfileActivity.class);
        startActivityForResult(intent,1991);

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//프로필정보 가져오기
        switch (requestCode){
            case 1991:

                if(resultCode==RESULT_OK){
                   profile = new ProfileListItem();

                    profile.name = data.getStringExtra("Name");
                    profile.birth = data.getStringExtra("Birth");
                    profile.breed = data.getStringExtra("Breed");
                    profile.color = data.getStringExtra("Color");

                    profile.imgIc = data.getIntExtra("Icon",0);
                    profiles.add(profile);

                    if(profile.imgId==0){
                        profile.imgId = R.drawable.zava;
                    }

                    adapterContent.notifyDataSetChanged();

                }

                break;
        }

    }


    // ProfileActivity띄움
//    public void clickProfile(View v) {
//
//        intent = new Intent(this, ProfileActivity.class);
//
//        // 프로필 화면 보여주기(프로필추가해야 띄움)
//        if (isResist) {
//
//            startActivity(intent);
//
//        } else {
//            new AlertDialog.Builder(this)
//                    .setMessage("마이펫을 등록 해주세요")
//                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            return;
//                        }
//                    }).show();
//        }
//
//
//    }//clickProfile
//


    ////////////////////////////////////////////////// 써져있던것들
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }//onBackPressed

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }//onCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }//onOptionsItemSelected

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }//onNavigationItemSelected


}
