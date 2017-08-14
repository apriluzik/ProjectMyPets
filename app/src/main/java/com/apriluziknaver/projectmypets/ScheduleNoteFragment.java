package com.apriluziknaver.projectmypets;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

// TODO: 2017-08-14  commit

public class ScheduleNoteFragment extends Fragment {

    SQLiteDatabase noteDB;
    String tablename;


    RecyclerView recyclerView;
    ScheduleListAdapter adapter;

    ArrayList<ScheduleListItem> lists = new ArrayList<>();

    Cursor cursor;

    FloatingActionButton fab;
    AlertDialog.Builder alert;
    String textValue = "";
  

    boolean isCreate = false;

    String username;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        username=bundle.getString("User");
        tablename=username+"Note";

        noteDB = getContext().openOrCreateDatabase("data.db", MODE_PRIVATE, null);

        noteDB.execSQL("CREATE TABLE IF NOT EXISTS " + tablename + "("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "list TEXT, "
                + "ischeck INTEGER"
                + ")");

        Log.d("username",username);
        Log.d("tablename",tablename);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_schedule_nt, container, false);

        fab = view.findViewById(R.id.fab_nt);
        recyclerView = (RecyclerView) view.findViewById(R.id.nt_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new ScheduleListAdapter(getContext(), lists, tablename);
        recyclerView.setAdapter(adapter);

        editList();
        isCreate = true;

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLists();
            }
        });

        return view;

    }


    //addList( editList()  )

    //DB
    public void addLists() {

        // TODO: 2017-08-07 리스트항목추가(Dialog)
        final EditText msg = new EditText(getContext());
        alert = new AlertDialog.Builder(getContext());
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


    //데이터베이스에 추가
    public void editList() {
        lists.clear();


        if (isCreate) {


            noteDB.execSQL("INSERT INTO " + tablename + "(list) values('" + textValue + "')");

        }//isCreate

        Log.d("iscreate", isCreate + "");

            cursor = noteDB.rawQuery("SELECT * FROM " + tablename, null);

            while (cursor.moveToNext()) {
                ScheduleListItem slt = new ScheduleListItem();
                slt.value = cursor.getString(cursor.getColumnIndex("list"));
                lists.add(slt);
                Log.d("value", slt.value);
            }
            adapter.notifyDataSetChanged();
    }

}
