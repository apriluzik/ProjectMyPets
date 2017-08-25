package com.apriluziknaver.projectmypets;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;

public class CommunityActivity extends AppCompatActivity {

    //fabs move
    RelativeLayout layout_roof;
    android.transition.Scene from;
    android.transition.Scene to;
    Transition transition;
    FloatingActionButton fab;

    RecyclerView recyclerView;
    ReadContentsAdpater adapter;
    ArrayList<ReadContentsItem> readContentsItems = new ArrayList<>();

    ArrayList<WriteContents> contentses = new ArrayList<>();
    FloatingActionButton fab_signup;

    boolean isSignup = false;

    String imgPath;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commu);
        fab = (FloatingActionButton) findViewById(R.id.cm_fab);
        fab_signup = (FloatingActionButton) findViewById(R.id.sign_fab);

        Intent intent = getIntent();
        imgPath = intent.getStringExtra("ImgPath").toString();


        recyclerView = (RecyclerView) findViewById(R.id.cm_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        adapter = new ReadContentsAdpater(this, readContentsItems);
        recyclerView.setAdapter(adapter);

        readContentsItems.add(new ReadContentsItem("user1", "contents", "yyyymmdd"));
        readContentsItems.add(new ReadContentsItem("user2", "contents", "yyyymmdd"));
        readContentsItems.add(new ReadContentsItem("user3", "contents", "yyyymmdd"));

        adapter.notifyDataSetChanged();

        try {
            InputStream is = openFileInput("User.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = reader.readLine();
            StringBuffer buffer = new StringBuffer();

            while (line != null) {

                buffer.append(line);
                line = reader.readLine();

            }
            JSONObject obj = new JSONObject(buffer.toString());

            name = (String) obj.get("User");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void communityFab(final View vi) {

        try {

            InputStream is = openFileInput("User.json");
            writeFab(vi);

        } catch (FileNotFoundException e) {

            new AlertDialog.Builder(this).setTitle("로그인 필요").setMessage("글을 작성하려면 로그인이 필요합니다\n로그인 하시겠습니까?")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            signupFab(vi);
                        }
                    }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            }).show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void writeFab(View v) {



        Snackbar.make(v, "write", Snackbar.LENGTH_SHORT).show();
        // TODO: 2017-08-24 signupFab 에서 받아온 user 정보랑 같이 보내주기
        Intent intent = new Intent(this, WriteContentsActivity.class);
        intent.putExtra("ImgPath", imgPath);
        intent.putExtra("Name",name);
        startActivity(intent);


    }

    public void signupFab(View v) {

        // TODO: 2017-08-24 SignUpActivity 에서 user 정보 가져오기 
        Intent intent = new Intent(CommunityActivity.this, SignUpActivity.class);
        startActivityForResult(intent, 9214);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            //signup
            case 9214:

                if (resultCode == RESULT_OK) {

                } else {

                }
                isSignup = true;
                Toast.makeText(this, "로그인 되었습니다", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    public String jsonParse() {

        String str = null;


        return str;
    }

    public void fabActioninit() {

        layout_roof = (RelativeLayout) findViewById(R.id.roof);
        transition = TransitionInflater.from(this).inflateTransition(R.transition.tran);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            from = android.transition.Scene.getSceneForLayout(layout_roof, R.layout.scene_fabs_from, this);
            to = android.transition.Scene.getSceneForLayout(layout_roof, R.layout.scene_fabs_to, this);

            from.enter();

        } else {
            View view = LayoutInflater.from(this).inflate(R.layout.scene_fabs_from, layout_roof, false);
            layout_roof.addView(view);

        }


    }


}
