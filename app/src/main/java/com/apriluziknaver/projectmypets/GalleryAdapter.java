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

/**
 * Created by mapri on 2017-09-01.
 */

public class GalleryAdapter extends RecyclerView.Adapter {

    ArrayList<GalleryItems> item;
    Context context;

    public GalleryAdapter(ArrayList<GalleryItems> item, Context context) {
        this.item = item;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemview = LayoutInflater.from(context).inflate(R.layout.gallery_item,parent,false);
        RecyclerView.ViewHolder holder = new GalleryHolder(itemview);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GalleryHolder mholder = (GalleryHolder)holder;

//        Glide.with(context).load(item.get(position).galleryImg).into(mholder.img);
        Glide.with(context).load(item.get(position).testImg).into(mholder.img);
        mholder.msg.setText(item.get(position).galleryMessage);

    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    class GalleryHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView msg;

        public GalleryHolder(View itemView) {

            super(itemView);

            img = itemView.findViewById(R.id.gall_img);
            msg = itemView.findViewById(R.id.gall_msg);
        }
    }
}
