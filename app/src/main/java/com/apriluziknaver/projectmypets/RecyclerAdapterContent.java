package com.apriluziknaver.projectmypets;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by mapri on 2017-08-01.
 */

public class RecyclerAdapterContent extends RecyclerView.Adapter {

    Context context;
    ArrayList<ProfileListItem> items;

    public RecyclerAdapterContent(Context context, ArrayList<ProfileListItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.content_recycler_item,parent,false);
        RecyclerView.ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ViewHolder mholder = (ViewHolder)holder;

        mholder.pfName.setText(items.get(position).name);
        Glide.with(context).load(items.get(position).imgId).into(mholder.pfImg);
        Glide.with(context).load(items.get(position).imgIc).into(mholder.pfIcon);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{


        Typeface tf;
        TextView pfName;
        ImageView pfImg;
        ImageView pfIcon;

        public ViewHolder(View itemView) {
            super(itemView);

            pfName = (TextView) itemView.findViewById(R.id.pf_name);
            pfImg = (ImageView) itemView.findViewById(R.id.pf_img);
            pfIcon = (ImageView) itemView.findViewById(R.id.pf_gender);

            tf = Typeface.createFromAsset(context.getAssets(),"fonts/SDMiSaeng.ttf");

            pfName.setTypeface(tf);

        }
    }
}
