package com.apriluziknaver.projectmypets;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class UserGalleryActivity extends AppCompatActivity {

    Uri imgUri;
    String imgPath;

    RecyclerView recyclerView;
    ArrayList<GalleryItem> galleryItems = new ArrayList<>();
    GalleryAdapter galleryAdapter;
    Intent intent;
    GridLayoutManager manager;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_gallery);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(fabListener);

        intent = getIntent();

        img = (ImageView)findViewById(R.id.gr_img);

        recyclerView = (RecyclerView) findViewById(R.id.gr_recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        galleryAdapter = new GalleryAdapter(this,galleryItems);

        recyclerView.setAdapter(galleryAdapter);

        galleryAdapter.notifyDataSetChanged();



    }

    // TODO: 2017-08-16 fab버튼 누르면 카메라눌러 사진 올리기/앨범선택해 사진 올리기 
    
    //fab리스너
    View.OnClickListener fabListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            //이미지선택(갤러리)
            selectGallery();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 500:

                if(resultCode!= RESULT_OK){
                    return;
                }
                imgUri = data.getData();
                Glide.with(this).load(imgUri).into(img);
                getMyPath(imgUri);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case 300:
                if(grantResults[0]==PackageManager.PERMISSION_DENIED){
                    Toast.makeText(this, "허용안함", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;

        }
    }

    //imgPath 얻어오기
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
    
    public void selectGallery(){


        //이미지선택(갤러리)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int checkpermission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);

            if (checkpermission == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 300);
            }
        }

        Intent itt = new Intent();
        itt.setAction(itt.ACTION_PICK).setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(itt, 500);
    }
    
    
    
}
