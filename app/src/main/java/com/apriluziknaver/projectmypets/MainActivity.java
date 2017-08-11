package com.apriluziknaver.projectmypets;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

   /* TODO: 2017-08-07 저장할 사용자 정보 :
            [애견] 이름, 성별, 생년월일, 품종, 색 (--프로필),
                    체크노트에 적은 항목들, 체크노트의 체크(일일스케줄체크)횟수 */

    //app_bar_main 에 fab
    String name;
    String gender;
    String birth;
    String breed;
    String color;
    int imgIc;

    SQLiteDatabase db;
    String tablename = "user";
    Cursor cursor;


    Typeface typeface;
    ActionBar actionBar;
    FloatingActionButton fab;

    ArrayList<ProfileListItem> profiles = new ArrayList<>();
    RecyclerView pf_RecyclerView;
    ProfileAdapter adapterContent;
    RecyclerView.LayoutManager manager;

    //펫 추가 햇는지 (펫추가 창에서 확인 누르면 true)
    boolean isResist = false;

    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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



        //펫리스트
        pf_RecyclerView = (RecyclerView) findViewById(R.id.pf_recycler);
        adapterContent = new ProfileAdapter(this, profiles,db,tablename);
        pf_RecyclerView.setAdapter(adapterContent);
        manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        pf_RecyclerView.setLayoutManager(manager);
        adapterContent.notifyDataSetChanged();


        //database 열기
        openDB();

        cursor = db.rawQuery("SELECT * FROM " + tablename, null);

        while (cursor.moveToNext()) {
            ProfileListItem profile = new ProfileListItem();
            profile.name = cursor.getString(cursor.getColumnIndex("name"));
            profile.gender = cursor.getString(cursor.getColumnIndex("gender"));
            profile.birth = cursor.getString(cursor.getColumnIndex("birth"));
            profile.breed = cursor.getString(cursor.getColumnIndex("breed"));
            profile.color = cursor.getString(cursor.getColumnIndex("color"));
            profile.imgIc = cursor.getInt(cursor.getColumnIndex("imgIc"));

            profiles.add(profile);

        }

        adapterContent.notifyDataSetChanged();


    }//Main Activity


    //DB
    public void openDB() {

        db = openOrCreateDatabase("data.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS " + tablename + "("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT, "
                + "gender TEXT, "
                + "birth INTEGER, " +
                "breed TEXT, " +
                "color TEXT, " +
                "imgIc INTEGER)");
    }



    //AddProfileActivity로가서 정보 얻어오기
    public void AddProfile() {
        isResist = true;
        intent = new Intent(this, AddProfileActivity.class);
        startActivityForResult(intent, 1991);
    }

    //AddProfileActivity에서 put 햇던거 가져오기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1991:

                if (resultCode == RESULT_OK) {

                    profiles.clear();

                    name = data.getStringExtra("Name");
                    gender = data.getStringExtra("Gender");
                    birth = data.getStringExtra("Birth");
                    breed = data.getStringExtra("Breed");
                    color = data.getStringExtra("Color");
                    imgIc = data.getIntExtra("Icon", 0);

                    db.execSQL("INSERT INTO " + tablename + "(name, gender, birth, breed, color, imgIc) " +
                            "values('" + name + "','" + gender + "','" + birth + "','" + breed + "','" + color + "'," + imgIc + ")");

                    cursor = db.rawQuery("SELECT * FROM " + tablename, null);

                    while (cursor.moveToNext()) {
                        ProfileListItem profile = new ProfileListItem();
                        profile.name = cursor.getString(cursor.getColumnIndex("name"));
                        profile.gender = cursor.getString(cursor.getColumnIndex("gender"));
                        profile.birth = cursor.getString(cursor.getColumnIndex("birth"));
                        profile.breed = cursor.getString(cursor.getColumnIndex("breed"));
                        profile.color = cursor.getString(cursor.getColumnIndex("color"));
                        profile.imgIc = cursor.getInt(cursor.getColumnIndex("imgIc"));

                        profiles.add(profile);

                    }

                    adapterContent.notifyDataSetChanged();
                }

                break;
        }

    }




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
