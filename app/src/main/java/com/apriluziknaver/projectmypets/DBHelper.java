package com.apriluziknaver.projectmypets;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by HSP on 2017-08-29.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME_ALARM = "alarm";
    public static final String TAG = "안녕";


    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        Log.i(TAG, "12345");
        String sql = "create table " + TABLE_NAME_ALARM +
                " (id integer primary key autoincrement, " +
                "title text, " +
                "startDate text, " +
                "repeat text, " +
                "time text, " +
                "OnOff integer," +
                "parent text );";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void UpdateDB(int id, String title, String startDate, String repeat, String time, int OnOff , String parent) {
        //사용자가 값을 셋팅한뒤 확인을 누르면 데이터베이스에 인서트
        SQLiteDatabase db = getWritableDatabase();
//        String sql = "select * from " + TABLE_NAME_ALARM + " where " + "id" + " = " + id + ";";
//        Cursor cursor = db.rawQuery(sql, null);

        ContentValues values = new ContentValues();

        values.put("title", title);
        values.put("startDate", startDate);
        values.put("repeat", repeat);
        values.put("time", time);
        values.put("OnOff", OnOff);
        values.put("parent", parent);




        //해당 아이디에 자료가 있음으로 업데이트
        int a=db.update(TABLE_NAME_ALARM, values, "id="+id, null);

        Log.i(TAG,a+"");
//        String upsql=
//                "UPDATE "+TABLE_NAME_ALARM+" SET "+values+"";

        Log.i(TAG, "업데이트 완료");

    }
    public void insultDB( String title, String startDate, String repeat, String time, int OnOff, String parent) {
        //사용자가 값을 셋팅한뒤 확인을 누르면 데이터베이스에 인서트
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("startDate", startDate);
        values.put("repeat", repeat);
        values.put("time", time);
        values.put("OnOff", OnOff);
        values.put("parent", parent);

        //해당아이디에 자료가 없어서 인서트 시키기
        db.insert(TABLE_NAME_ALARM, null, values);
        Log.i(TAG, "인서트 완료");



    }

    public ArrayList<AlarmItem> getItem(String parent) {
        ArrayList<AlarmItem> items = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String sql = "select * from " + TABLE_NAME_ALARM + " where parent='"+parent+"'";

        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()){
            AlarmItem item=new AlarmItem();
            item.id=cursor.getInt(cursor.getColumnIndex("id"));
            item.alarmtitle=cursor.getString(cursor.getColumnIndex("title"));
            item.alarmdate=cursor.getString(cursor.getColumnIndex("startDate"));
            item.alarmCycle=cursor.getString(cursor.getColumnIndex("repeat"));
            item.alarmTime=cursor.getString(cursor.getColumnIndex("time"));
            item.isOn=cursor.getInt(cursor.getColumnIndex("OnOff"));
            item.parent=cursor.getString(cursor.getColumnIndex("parent"));
            items.add(item);
        }
        return items;
    }

    public void deleteDB(int id) {

        SQLiteDatabase db = getWritableDatabase();
        String sql = "select * from " + TABLE_NAME_ALARM + " where " + "id" + " = " + id + ";";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.getCount() != 0) db.delete(TABLE_NAME_ALARM, "id=?", new String[]{id + ""});
        Log.i(TAG,"지우기 완료");

    }

}



