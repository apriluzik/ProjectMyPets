package com.apriluziknaver.projectmypets;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mapri on 2017-08-22.
 */

public class ContentsAdpater extends RecyclerView.Adapter {


    Context context;
    ArrayList<ContentsItem> items;

    public ContentsAdpater(Context context, ArrayList<ContentsItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemview = LayoutInflater.from(context).inflate(R.layout.commu_item,parent,false);
        RecyclerView.ViewHolder holder = new ViewHolder(itemview);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ViewHolder mholder = (ViewHolder)holder;

        Glide.with(context).load(items.get(position).titleUserImg).into(mholder.titleImg);
        mholder.titleName.setText(items.get(position).titleUser);
        mholder.titleTime.setText(items.get(position).titleTime);

        Glide.with(context).load(items.get(position).conImg).into(mholder.contentImg);
        mholder.contents.setText(items.get(position).conTxt);


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView titleName;
        TextView titleTime;
        CircleImageView titleImg;

        ImageView contentImg;
        TextView contents;

        public ViewHolder(View itemView) {
            super(itemView);

            titleName = itemView.findViewById(R.id.cm_name);
            titleTime = itemView.findViewById(R.id.cm_time);
            titleImg = itemView.findViewById(R.id.cm_userimg);

            contents = itemView.findViewById(R.id.cm_content_tv);
            contentImg = itemView.findViewById(R.id.cm_content_img);
        }
    }
}
