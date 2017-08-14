package com.apriluziknaver.projectmypets;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class ScheduleTrainingFragment extends Fragment {

    SQLiteDatabase trainingDB;
    String tablename = "traing";

    RecyclerView recyclerView;
    ScheduleListAdapter adapter;

    ArrayList<ScheduleListItem> lists = new ArrayList<>();

    Cursor cursor;

    FloatingActionButton fab;
    AlertDialog.Builder alert;
    String textValue = "";

    boolean isCreate=false;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        trainingDB = getContext().openOrCreateDatabase("data.db", MODE_PRIVATE, null);
        trainingDB.execSQL("CREATE TABLE IF NOT EXISTS " + tablename + "("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "list TEXT"
                + ")");



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_schedule_tr, container, false);

        fab = view.findViewById(R.id.fab_tr);
        recyclerView = (RecyclerView) view.findViewById(R.id.tr_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new ScheduleListAdapter(getContext(), lists,tablename);

        recyclerView.setAdapter(adapter);

        editList();
        isCreate=true;

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLists();
            }
        });

        return view;

    }



//DB
    public void addLists(){

        // TODO: 2017-08-07 리스트항목추가(Dialog)
        final EditText msg = new EditText(getContext());
        alert=new AlertDialog.Builder(getContext());
        alert.setView(msg);
        msg.setInputType(InputType.TYPE_CLASS_TEXT);

        alert.setTitle("항목 추가");

        //확인버튼
        alert.setPositiveButton("추가", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                textValue = msg.getText().toString();

                editList();

                adapter.notifyDataSetChanged();
                dialogInterface.dismiss();
            }
        });

        //취소버튼
        alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();

            }
        });
        alert.show();
    }



    public void editList() {
        lists.clear();
        if(isCreate) {
            trainingDB.execSQL("INSERT INTO " + tablename + "(list) values('" + textValue + "')");
        }
        cursor = trainingDB.rawQuery("SELECT * FROM " + tablename, null);

        while (cursor.moveToNext()) {
            ScheduleListItem sl = new ScheduleListItem();
            sl.value = cursor.getString(cursor.getColumnIndex("list"));
            lists.add(sl);

            Log.d("value",sl.value);
        }
        adapter.notifyDataSetChanged();

    }




}
