package com.apriluziknaver.projectmypets;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

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

        View itemview = LayoutInflater.from(context).inflate(R.layout.gallery_item, parent, false);
        RecyclerView.ViewHolder holder = new GalleryHolder(itemview);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GalleryHolder mholder = (GalleryHolder) holder;


//        Glide.with(context).load(item.get(position).imgUri).into(mholder.img);

        if (item.get(position).imgUri.toString().contains("file://")) {
            Picasso.with(context)
                    .load(item.get(position).imgUri)
                    .resize(600, 400)
                   
                    .into(mholder.img);


        } else {
            Glide.with(context).load(item.get(position).imgUri).into(mholder.img);
        }


    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    class GalleryHolder extends RecyclerView.ViewHolder {

        ImageView img;


        public GalleryHolder(View itemView) {

            super(itemView);

            img = itemView.findViewById(R.id.gall_img);
        }
    }


    public static class PicassoTransformations {

        public static int targetWidth = 200;

        public static Transformation resizeTransformation = new Transformation() {
            @Override
            public Bitmap transform(Bitmap source) {
                double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
                int targetHeight = (int) (targetWidth * aspectRatio);
                Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                if (result != source) {
                    source.recycle();
                }
                return result;
            }

            @Override
            public String key() {
                return "resizeTransformation#" + System.currentTimeMillis();
            }
        };
    }


}
