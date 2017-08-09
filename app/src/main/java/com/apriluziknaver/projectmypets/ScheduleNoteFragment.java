package com.apriluziknaver.projectmypets;

import android.content.Context;
import android.content.DialogInterface;
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

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class ScheduleNoteFragment extends Fragment {

    SQLiteDatabase noteDB;
    String tablename = "note";

    RecyclerView recyclerView;
    ScheduleListAdapter adapter;
    ArrayList<ScheduleListItem> lists = new ArrayList<>();

    FloatingActionButton fab;
    AlertDialog.Builder alert;
    String textValue="";



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_schedule_nt,container,false);

        fab = view.findViewById(R.id.fab_nt);
        recyclerView = (RecyclerView) view.findViewById(R.id.nt_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        adapter = new ScheduleListAdapter(getContext(),lists);
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLists();
                adapter.notifyDataSetChanged();

            }
        });


        return view;

    }
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
                lists.add(new ScheduleListItem(textValue));
                Log.d("VALUe",textValue+"");


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
}
