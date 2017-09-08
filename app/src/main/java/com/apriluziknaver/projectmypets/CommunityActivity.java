package com.apriluziknaver.projectmypets;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class CommunityActivity extends AppCompatActivity {

    FloatingActionButton fab;

    RecyclerView recyclerView;
    ReadContentsAdpater adapter;
    ArrayList<ReadContentsItem> readContentsItems = new ArrayList<>();

    FloatingActionButton fab_signup;

    boolean isSignup = false;

    String imgPath;
    String name;
    String pw;
    LoadBoardTask task ;

    SwipeRefreshLayout refreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        if(task==null) {
            task = new LoadBoardTask();
            task.execute();
        }

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.cm_swipe);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                readContentsItems.clear();
                task = new LoadBoardTask();
                task.execute();

            }
        });



        fab = (FloatingActionButton) findViewById(R.id.cm_fab);
        fab_signup = (FloatingActionButton) findViewById(R.id.sign_fab);

        Intent intent = getIntent();
        imgPath = intent.getStringExtra("ImgPath").toString();

        recyclerView = (RecyclerView) findViewById(R.id.cm_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new ReadContentsAdpater(this, readContentsItems);
        recyclerView.setAdapter(adapter);




        //이름받아서 WriteContentsActivity로
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
            pw = (String )obj.get("PW");


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {

            if(name==null){
                return;
            }

            recyclerView = (RecyclerView) findViewById(R.id.cm_recycler);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            adapter = new ReadContentsAdpater(this, readContentsItems,name,pw);
            recyclerView.setAdapter(adapter);


            adapter.notifyDataSetChanged();
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
        String imgPath2 = name+pw+".jpg";

        Snackbar.make(v, "write", Snackbar.LENGTH_SHORT).show();
        // TODO: 2017-08-24 signupFab 에서 받아온 user 정보랑 같이 보내주기
        Intent intent = new Intent(this, WriteContentsActivity.class);
        intent.putExtra("ImgPath", imgPath2);

        intent.putExtra("Name",name);
        startActivity(intent);


    }

    public void signupFab(View v) {

        // TODO: 2017-08-24 SignUpActivity 에서 user 정보 가져오기 
        Intent intent = new Intent(CommunityActivity.this, SignUpActivity.class);
        intent.putExtra("ImgPath",imgPath);
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

    public void moreReply(View v){

        Toast.makeText(this, "more?", Toast.LENGTH_SHORT).show();

    }



    class LoadBoardTask extends AsyncTask<Void,Void,Void>{

        String readUrl = "http://kghy234.dothome.co.kr/MyPets/readBoard.php";
        String tablename = "MyPetsBoard";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

            adapter.notifyDataSetChanged();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                URL url = new URL(readUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);

                InputStream is = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                String line= reader.readLine();
                String[] lines ;

                while(line!=null){

                    lines = line.split("&");
                    ReadContentsItem con = new ReadContentsItem();

                    con.id = Integer.parseInt(lines[0]);
                    con.titleUser = lines[1];
                    con.titleUserImg = lines[2];
                    con.titleTime=lines[3];
                    con.conTxt = lines[4];
                    con.conImg = lines[5];
                    con.conVdo = lines[6];

                    readContentsItems.add(con);
                    Log.d("READC",line);

                    publishProgress();//onProgressUpdate()를 실행
                    line=reader.readLine();

                }
                



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            refreshLayout.setRefreshing(false);

        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.normal,R.anim.note_out);
    }
}
