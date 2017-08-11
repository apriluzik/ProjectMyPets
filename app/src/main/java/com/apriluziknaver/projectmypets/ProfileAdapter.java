package com.apriluziknaver.projectmypets;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.graphics.drawable.DrawableWrapper;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DrawableUtils;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class ProfileAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<ProfileListItem> items;
    SQLiteDatabase db;
    String tablename;

    public ProfileAdapter(Context context, ArrayList<ProfileListItem> items,SQLiteDatabase db,String tablename) {
        this.context = context;
        this.items = items;
        this.db = db;
        this.tablename = tablename;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.content_recycler_item,parent,false);
        RecyclerView.ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        ViewHolder mholder = (ViewHolder)holder;

        mholder.pfName.setText(items.get(position).name);
        Glide.with(context).load(items.get(position).imgIc).into(mholder.pfIcon);
        Glide.with(context).load(items.get(position).picPath).into(mholder.pfImg);


        mholder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                new AlertDialog.Builder(context).setTitle("항목 삭제").setMessage("항목을 삭제하시겠습니까?")
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).setPositiveButton("삭제", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //누른 항목삭제
                        items.remove(position);
                        notifyDataSetChanged();
                        dialogInterface.dismiss();
                    }
                }).show();

                return true;
            }
        });


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
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.pf_cardview);
            pfName = (TextView) itemView.findViewById(R.id.pf_name);
            pfImg = (ImageView) itemView.findViewById(R.id.pf_img);
            pfIcon = (ImageView) itemView.findViewById(R.id.pf_gender);

            tf = Typeface.createFromAsset(context.getAssets(),"fonts/SDMiSaeng.ttf");

            pfName.setTypeface(tf);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent intent = new Intent(context,ProfileActivity.class);


                    ProfileListItem profile = items.get(getLayoutPosition());


                    intent.putExtra("PicPath",profile.picPath);
                    intent.putExtra("Icon",profile.imgIc);
                    intent.putExtra("Name",profile.name);
                    intent.putExtra("Birth",profile.birth);
                    intent.putExtra("Breed",profile.breed);
                    intent.putExtra("Color",profile.color);

                    context.startActivity(intent);

                }
            });


        }
    }

}
