package com.apriluziknaver.projectmypets;

import android.Manifest;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddProfileActivity extends AppCompatActivity {

    Intent intent;
    ProfileListItem profileListItem;

    Uri imgUri = null;
    String serverUrl = "http://kghy234.dothome.co.kr/MyPets/volleyimg.php";

    EditText name;
    EditText birth;
    EditText breed;
    EditText color;
    CircleImageView selectGallery;
    CircleImageView picImg;
    boolean isSelect = false;

    RadioGroup rbGroup;
    RadioButton male, female;
    int icon;
    String imgPath;
    String gender;
    int defaultimg = R.drawable.zava;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile);

        intent = getIntent();

        profileListItem = new ProfileListItem();
        name = (EditText) findViewById(R.id.edit_name);
        birth = (EditText) findViewById(R.id.edit_birth);
        breed = (EditText) findViewById(R.id.edit_breed);
        color = (EditText) findViewById(R.id.edit_color);
        rbGroup = (RadioGroup) findViewById(R.id.rb_group);
        female = (RadioButton) findViewById(R.id.rb_female);
        male = (RadioButton) findViewById(R.id.rb_male);

        picImg = (CircleImageView) findViewById(R.id.pic_img);

        rbGroup.setOnCheckedChangeListener(chlistener);


        selectGallery = (CircleImageView) findViewById(R.id.select_gallery);
        selectGallery.setOnClickListener(galListener);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 400:

                if (resultCode != RESULT_OK) {

                    return;
                }
                imgUri = data.getData();
                Glide.with(this).load(imgUri).into(picImg);

                getMyPath(imgUri);

                Log.d("imguri", imgUri.toString());
                break;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 200:
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, "허용안함", Toast.LENGTH_SHORT).show();
                    finish();

                }
                break;
        }

    }

    //라디오버튼으로 아이콘 바꾸기
    RadioGroup.OnCheckedChangeListener chlistener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

            switch (i) {
                case R.id.rb_male:

                    icon = R.drawable.m;
                    profileListItem.imgIc = icon;
                    gender = "male";
                    intent.putExtra("Icon", icon);
                    intent.putExtra("Gender", gender);
                    break;

                case R.id.rb_female:

                    icon = R.drawable.f;
                    profileListItem.imgIc = icon;
                    gender = "female";
                    intent.putExtra("Icon", icon);
                    intent.putExtra("Gender", gender);
                    break;
            }


        }
    };

    //이미지선택(갤러리)
    View.OnClickListener galListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(AddProfileActivity.this, "openGallery", Toast.LENGTH_SHORT).show();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                int checkpermission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);

                if (checkpermission == PackageManager.PERMISSION_DENIED) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 200);
                }
            }

            Intent itt = new Intent();
            itt.setAction(itt.ACTION_PICK).setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(itt, 400);
        }
    };


    public void clickOK(View v) {

// 이름 미입력시
        if (name.getText().toString().equals("")) {

            new AlertDialog.Builder(this)
                    .setMessage("이름을 입력해주세요.")
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).show();
        }


        if (imgPath == null) {
            intent.putExtra("PicPath", R.drawable.zava + "");
        } else {
            intent.putExtra("PicPath", imgPath.toString());
        }
        intent.putExtra("Name", name.getText().toString());
        intent.putExtra("Birth", birth.getText().toString());
        intent.putExtra("Breed", breed.getText().toString());
        intent.putExtra("Color", color.getText().toString());


        setResult(RESULT_OK, intent);

        //이름이 "" (초기화값)이 아니면 액티비티 finish
        if (!name.getText().toString().equals("")) {

            finish();
        }

    }

    public void clickCANCEL(View v) {

        setResult(RESULT_CANCELED, intent);
        finish();

    }

    public String getMyPath(Uri imgUri) {


        imgPath = imgUri.toString();
        Log.i("==imgPATH==", imgPath);

        if (imgPath.contains("content://")) {
            ContentResolver resolver = getContentResolver();
            Cursor cursor = resolver.query(imgUri, null, null, null, null);

            if (cursor != null && cursor.getCount() != 0) {
                cursor.moveToFirst();
                imgPath = cursor.getString(cursor.getColumnIndex("_data"));
            }
        }

        return imgPath;

    }

    public void UploadImg(Uri imgUri) {
        {


            intent = getIntent();

            String imgPath = null;

            imgPath = getMyPath(imgUri);

            if (imgPath == null) return;

            //Volley를 통해 네트웍작업을 수행하는 큐가 필요.
            RequestQueue requestQue = Volley.newRequestQueue(this);
            SimpleMultiPartRequest smpr = new SimpleMultiPartRequest(Request.Method.POST, serverUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(AddProfileActivity.this, response, Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AddProfileActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            });

            smpr.addFile("upload", imgPath);
            smpr.addStringParam("title", "this is title");

            requestQue.add(smpr);

        }

    }
}
