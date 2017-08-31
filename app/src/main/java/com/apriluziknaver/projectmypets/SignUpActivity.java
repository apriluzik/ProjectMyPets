package com.apriluziknaver.projectmypets;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SignUpActivity extends AppCompatActivity {

    String checkIdUrl = "http://kghy234.dothome.co.kr/MyPets/checkID.php";
    String signUpUrl = "http://kghy234.dothome.co.kr/MyPets/signup.php";
    String volleyUrl = "http://kghy234.dothome.co.kr/MyPets/volleyimg.php";


    EditText editId;
    EditText editPass, editPassCheck;

    TextView infoId;
    TextView infoPass;
    String id = null;
    Button checkBtn;
    Button okBtn;

    boolean canMake = false;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setTitle("SIGN UP");

        editId = (EditText) findViewById(R.id.sign_userName);
        editPass = (EditText) findViewById(R.id.sign_pass);
        editPassCheck = (EditText) findViewById(R.id.sign_passcheck);
        checkBtn = (Button) findViewById(R.id.sign_checkbtn);
        okBtn = (Button) findViewById(R.id.btnok);

        infoId = (TextView) findViewById(R.id.infoname);
        infoPass = (TextView) findViewById(R.id.infopass);

        editId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!charSequence.equals("")) {

                    if (charSequence.toString().equals(id) == false) {
                        //에딧텍스트에 써져있는것과 id가 같지않다면
                        //에딧텍스트에 써져있는것과 table의 id값들과 비교?

                        infoId.setVisibility(View.INVISIBLE);
                        checkBtn.setEnabled(true);
                        canMake = false;


                    } else {

                        canMake = true;
                        checkBtn.setEnabled(false);
                    }

                } else {
                    //썻다가 지웠을때
                    checkBtn.setEnabled(false);
                    canMake = false;
                    id = null;

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.toString().equals(editPassCheck.getText().toString()) && canMake == true) {
                    okBtn.setEnabled(true);

                } else {
                    okBtn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editPassCheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.toString().equals(editPass.getText().toString()) && canMake == true) {
                    okBtn.setEnabled(true);

                } else {

                    okBtn.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }


    public void signCheck(final View view) {

        view.setEnabled(false);

        new Thread() {
            @Override
            public void run() {


                try {

                    URL url = new URL(checkIdUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setUseCaches(false);

                    OutputStream os = conn.getOutputStream();
                    String data = "username=" + editId.getText().toString();
                    os.write(data.getBytes());
                    os.flush();
                    os.close();

                    InputStream is = conn.getInputStream();
                    InputStreamReader isreader = new InputStreamReader(is);
                    BufferedReader reader = new BufferedReader(isreader);
                    StringBuffer buffer = new StringBuffer();

                    String line = reader.readLine();

                    while (line != null) {

                        buffer.append(line);
                        line = reader.readLine();

                    }

                    Log.d("buffer", buffer.toString());

                    int result = Integer.parseInt(buffer.toString());

                    //중복결과
                    if (result == 1) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                infoId.setVisibility(View.VISIBLE);
                                infoId.setTextColor(Color.RED);
                                infoId.setText("중복");
                            }
                        });

                        canMake = false;
                        Log.d("result", result + ": 실패");

                    } else {
                        //성공
                        id = editId.getText().toString();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                infoId.setVisibility(View.VISIBLE);
                                infoId.setTextColor(Color.GREEN);
                                infoId.setText("사용가능");

                            }
                        });

                        canMake = true;
                        Log.d("result", result + ": 성공");
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }.start();


    }

    public void passwordCheck() {


        String ps = editPass.getText().toString();
        String psc = editPassCheck.getText().toString();

        if (psc.equals(ps)) {

            infoPass.setText("비밀번호 일치");

        } else {

        }

    }

    //php에서 id(username)으로 table생성
    public void signUpOK(View view) {


        //enable ==false
        //true 조건 : 아이디중복 X, editPass,editPassCheck 텍스트 일치

        new Thread() {
            @Override
            public void run() {

//                try {
//                    URL url = new URL(signUpUrl);
//                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                    connection.setUseCaches(false);
//                    connection.setDoOutput(true);
//                    connection.setDoInput(true);
//                    connection.setRequestMethod("POST");
//
//                    OutputStream os = connection.getOutputStream();
//                    String data = "username=" + id + "&password=" + editPass.getText().toString();
//                    os.write(data.getBytes());
//                    os.flush();
//                    os.close();
//
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//
//                    String line = reader.readLine();
//                    StringBuffer buffer = new StringBuffer();
//
//                    while (line != null) {
//                        buffer.append(line);
//                        line = reader.readLine();
//
//                    }
//
//                    Log.d("signup", buffer.toString());
//
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }



                    intent = getIntent();

                    String imgPath = intent.getStringExtra("ImgPath");




                    //Volley를 통해 네트웍작업을 수행하는 큐가 필요.
                    RequestQueue requestQue = Volley.newRequestQueue(SignUpActivity.this);
                    SimpleMultiPartRequest smpr = new SimpleMultiPartRequest(Request.Method.POST, signUpUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(SignUpActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SignUpActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    });

                    if (imgPath == null) return;

                    smpr.addFile("upload", imgPath);
                    smpr.addStringParam("title",id+editPass.getText().toString());
                    smpr.addStringParam("username",id);
                    smpr.addStringParam("password",editPass.getText().toString());


                    requestQue.add(smpr);



                try {
                    OutputStream os = openFileOutput("User.json", MODE_PRIVATE);
                    JSONObject userObj = new JSONObject();
                    userObj.put("User", id);
                    userObj.put("PW", editPass.getText().toString());

                    os.write(userObj.toString().getBytes());
                    os.flush();
                    os.close();
                    Log.d("userJson", userObj.toString());

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();

        UploadImg(id,editPass.getText().toString());

        finish(); //--> CommunutyActivity


    }

    public void signUpCancel(View view) {

        finish();

    }

    public void UploadImg(String id,String pass) {
        {


            intent = getIntent();

            String imgPath = intent.getStringExtra("ImgPath");


            if (imgPath == null) return;

            //Volley를 통해 네트웍작업을 수행하는 큐가 필요.
            RequestQueue requestQue = Volley.newRequestQueue(this);
            SimpleMultiPartRequest smpr = new SimpleMultiPartRequest(Request.Method.POST, volleyUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(SignUpActivity.this, response, Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(SignUpActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            });

            smpr.addFile("upload", imgPath);
            smpr.addStringParam("title",id+pass);

            requestQue.add(smpr);

        }

    }



}

