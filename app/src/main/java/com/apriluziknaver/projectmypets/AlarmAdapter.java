package com.apriluziknaver.projectmypets;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
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

    public AlarmAdapter(Context context, ArrayList<AlarmItem> items) {
        this.context = context;
        this.items = items;
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

        }
    }
}
