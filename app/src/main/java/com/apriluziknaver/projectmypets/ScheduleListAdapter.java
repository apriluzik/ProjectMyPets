package com.apriluziknaver.projectmypets;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

/**
 * Created by mapri on 2017-08-07.
 */

public class ScheduleListAdapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<ScheduleListItem> items;
    String tablename;
    Cursor cursor;
    String sqlUpdate;
    SQLiteDatabase db;



    public ScheduleListAdapter(Context context, ArrayList<ScheduleListItem> items, String tablename) {
        this.context = context;
        this.items = items;
        this.tablename = tablename;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.schedule_item, parent, false);
        RecyclerView.ViewHolder holder = new ViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder mholder = (ViewHolder) holder;


        db = context.openOrCreateDatabase("data.db",Context.MODE_PRIVATE,null);

        mholder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                return;
            }
        });
        mholder.icon.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                final String str = mholder.value.getText().toString();
                Toast.makeText(context, position + "", Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(context).setTitle("항목 삭제").setMessage("항목을 완료하시겠습니까?")
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }

                        }).setPositiveButton("완료", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //누른 항목삭제(데이터베이스)
                        SQLiteDatabase db = context.openOrCreateDatabase("data.db", Context.MODE_PRIVATE, null);
                        db.execSQL("DELETE FROM " + tablename + " WHERE list=?", new String[]{str});
                        items.remove(position);
                        notifyDataSetChanged();
                        dialogInterface.dismiss();

                    }
                }).show();



                return true;
            }
        });//onLongClickListener

        mholder.value.setText(items.get(position).value);
        mholder.date.setText(items.get(position).date);

    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {


        TextView value;
        TextView date;
        ImageView icon;
        Typeface typeface;

        public ViewHolder(View itemView) {
            super(itemView);


            value = (TextView) itemView.findViewById(R.id.lists_text_sc);
            value.setTypeface(typeface);
            icon = (ImageView) itemView.findViewById(R.id.toggle_icon);
            date = (TextView) itemView.findViewById(R.id.list_date);
            date.setTypeface(typeface);


//            icon.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View view) {
//
//                    new AlertDialog.Builder(context).setTitle("항목 삭제").setMessage("항목을 삭제하시겠습니까?")
//                            .setNegativeButton("취소", new DialogInterface.OnClickListener() {
//
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    dialogInterface.cancel();
//                                }
//                            }).setPositiveButton("삭제", new DialogInterface.OnClickListener() {
//
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//
//                            //항목삭제
//                            items.remove(getLayoutPosition());
//                            notifyDataSetChanged();
//                            dialogInterface.dismiss();
//                        }
//                    }).show();
//
//                    return true;
//                }
//            });

        }
    }

    public void change(){

/*                switch (check){
                    case 0:

                        mholder.value.setTextColor(Color.BLACK);
                        mholder.itemView.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                        mholder.value.setPaintFlags(mholder.toggleButton.getPaintFlags());

                        break;

                    case 1:

                        mholder.value.setPaintFlags(mholder.toggleButton.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        mholder.value.setTextColor(Color.GRAY);
                        mholder.itemView.setBackgroundTintList(ColorStateList.valueOf(Color.LTGRAY));


                        break;*/
//
//                }

    }





}
