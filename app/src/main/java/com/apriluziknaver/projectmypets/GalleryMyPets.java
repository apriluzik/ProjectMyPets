package com.apriluziknaver.projectmypets;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GalleryMyPets extends AppCompatActivity {
    private String name;
    Intent intent;
    final int PICK_GALLERY = 100;
    final int PICK_CAMERA = 200;
    SQLiteDatabase db;
    String tablename = "MyPetGallery";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    home();
                    return true;
                case R.id.navigation_camera:
                    camera();
                    return true;
                case R.id.navigation_gallery:
                    gallery();
                    return true;
            }
            return false;
        }

    };

    ///

    RecyclerView recyclerView;
    ArrayList<GalleryItems> galleryItems = new ArrayList<>();
    GalleryAdapter galleryAdapter;

    StaggeredGridLayoutManager manager;

    String currentImgPath;
    String fileImgPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pets_gallery);

        intent = getIntent();
        name = intent.getStringExtra("ProfileName");

        db = openOrCreateDatabase("data.db", MODE_PRIVATE, null);

        db.execSQL("CREATE TABLE IF NOT EXISTS " + tablename + "("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "img TEXT, "
                + "name TEXT)");


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.gall_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);


        recyclerView = (RecyclerView) findViewById(R.id.gall_recycler);
        recyclerView.setHasFixedSize(true);
        galleryAdapter = new GalleryAdapter(galleryItems, this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(galleryAdapter);


        loadImgFromDB();


    }

    public void home() {

    }

    public void camera() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = null;

        try {

            file = createImgFile();

        } catch (Exception e) {
        }

        if (file != null) {

            Uri uri = FileProvider.getUriForFile(this, "com.apriluziknaver.projectmypets", file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, PICK_CAMERA);


        }

    }

    public void gallery() {

        Intent intent = new Intent(Intent.ACTION_PICK);

        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_GALLERY);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case PICK_CAMERA:

                if (resultCode != RESULT_OK) {
                    Log.d("currenta", "aa");
                    File file = new File(currentImgPath);
                    file.delete();
                    galleryAddPic();

                    return;
                }

                File file = new File(currentImgPath);
                Log.d("currenta", "dd");
                Uri uri = Uri.parse(fileImgPath);

                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(uri);
                sendBroadcast(intent);

                if (currentImgPath.contains("content://")) {

                    ContentResolver resolver = getContentResolver();
                    Cursor cursor = resolver.query(uri, null, null, null, null);

                    if (cursor != null && cursor.getCount() != 0) {
                        cursor.moveToFirst();
                        currentImgPath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                        Log.d("currenta", currentImgPath);

                    }

                }

                GalleryItems item1 = new GalleryItems();
                Log.d("currentaaa", currentImgPath);

                item1.imgUri = Uri.parse("file://" + currentImgPath);

                db.execSQL("insert into " + tablename + " (img,name) values('" + fileImgPath + "'," +
                        "'" + name + "')");

                galleryItems.add(item1);
                galleryAdapter.notifyDataSetChanged();


                break;

            case PICK_GALLERY:

                if (resultCode != RESULT_OK) {
                    return;
                }
                Log.d("gallData", data.getData().toString());

                GalleryItems item = new GalleryItems();
                item.imgUri = data.getData();

                Log.d("gallDataa", data.getData().toString());

                db.execSQL("insert into " + tablename + " (img,name) values('" + data.getData().toString() + "'," +
                        "'" + name + "')");

                galleryItems.add(item);
                galleryAdapter.notifyDataSetChanged();

                break;
        }

    }

    public void loadImgFromDB() {

        Cursor cursor = db.rawQuery("SELECT * FROM " + tablename + " WHERE name='" + name + "'", null);

        while (cursor.moveToNext()) {
            String uri = cursor.getString(cursor.getColumnIndex("img"));
            GalleryItems item = new GalleryItems();
            item.imgUri = Uri.parse(uri);
            galleryItems.add(item);

            galleryAdapter.notifyDataSetChanged();


        }


    }

    public File createImgFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "MyPets_" + timeStamp;

        File storageDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyPets/");
        File img = File.createTempFile(fileName, ".jpg", storageDir);

        currentImgPath = img.getAbsolutePath();
        Log.d("currentaa", currentImgPath);

        fileImgPath = "file://" + currentImgPath;


        return img;
    }

    public void galleryAddPic() {

        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(currentImgPath);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        sendBroadcast(intent);


    }

}
