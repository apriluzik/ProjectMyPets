package com.apriluziknaver.projectmypets;

import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mapri on 2017-08-29.
 */

public class AlarmAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<AlarmItem> items;
    DBHelper helper;

    public AlarmAdapter(Context context, ArrayList<AlarmItem> items) {
        this.context = context;
        this.items = items;
        helper=new DBHelper(context,"pets",null,1);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.alarm_item,parent,false);
        RecyclerView.ViewHolder holder  = new AlarmViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        AlarmViewHolder mholder = (AlarmViewHolder)holder;


        holder.itemView.setTag(items.get(position).id);

        mholder.title.setText(items.get(position).alarmtitle);
        mholder.date.setText(items.get(position).alarmdate);


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class AlarmViewHolder extends RecyclerView.ViewHolder{

        ImageView icon;
        TextView title;
        TextView date;
        ImageView btn;

        public AlarmViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.title1_ic);
            title = itemView.findViewById(R.id.title1);
            date = itemView.findViewById(R.id.title1_date);
            btn = itemView.findViewById(R.id.title1_btn);



            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                   new AlertDialog.Builder(context).setTitle("알람삭제")
                           .setMessage("삭제하시겠습니까").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           helper.deleteDB(items.get(getLayoutPosition()).id);
                           ((AlarmNoteActivity)context).loadDB();


                       }
                   }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {

                       }
                   }).show();

                    return false;
                }
            });
        }
    }

    public void refresh(ArrayList<AlarmItem> items){
        this.items=items;
        notifyDataSetChanged();
    }
}
