package com.apriluziknaver.projectmypets;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by mapri on 2017-08-14.
 */

public class GalleryAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<GalleryItem> items;

    public GalleryAdapter(Context context, ArrayList<GalleryItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.content_user_gallery,parent,false);
        RecyclerView.ViewHolder holder = new ViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ViewHolder mholder = (ViewHolder)holder;

        Glide.with(context).load(items.get(position).imgId).into(mholder.img);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView img ;

        public ViewHolder(View itemView) {
            super(itemView);

            img =  itemView.findViewById(R.id.gr_img);
        }
    }
}
