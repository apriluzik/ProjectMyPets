package com.apriluziknaver.projectmypets;

import android.content.ClipData;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mapri on 2017-08-25.
 */

public class ReplyAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<ReplyItems> items=new ArrayList<>();
    int parent;

    public ReplyAdapter(Context context, int parent) {
        this.context = context;
        this.parent=parent;

        LoadReplyTask replyTask = new LoadReplyTask();
        replyTask.execute();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemview = LayoutInflater.from(context).inflate(R.layout.commu_reply_item,parent,false);
        RecyclerView.ViewHolder holder = new ContentViewHolder(itemview);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ContentViewHolder mholder = (ContentViewHolder) holder;

        Glide.with(context).load(items.get(position).userimg).into(mholder.userimg);
        mholder.username.setText(items.get(position).username);
        mholder.date.setText(items.get(position).date);
        mholder.msg.setText(items.get(position).message);


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ContentViewHolder extends RecyclerView.ViewHolder{

        CircleImageView userimg;
        TextView username;
        TextView date;
        TextView msg;

        public ContentViewHolder(View itemView) {
            super(itemView);

            userimg = itemView.findViewById(R.id.reply_img);
            username = itemView.findViewById(R.id.reply_name);
            date = itemView.findViewById(R.id.reply_date);
            msg = itemView.findViewById(R.id.reply_msg);


        }
    }

    class LoadReplyTask extends AsyncTask<Void,Void,Void>{

        String replywrite ="http://kghy234.dothome.co.kr/MyPets/writeReply.php";
        String imgUrl="http://kghy234.dothome.co.kr/MyPets/uploadimg/";
        String replyload ="http://kghy234.dothome.co.kr/MyPets/loadReply.php";


        //시작전
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        //스레드 중간중간 UI작업 대신


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            notifyDataSetChanged();
        }

        //런메소드
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url =new URL(replyload);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setDoInput(true);
                con.setDoInput(true);
                con.setUseCaches(false);


                OutputStream os = con.getOutputStream();
                OutputStreamWriter osw=new OutputStreamWriter(os);
                osw.write("parent="+parent);


                osw.flush();
                osw.close();

                InputStream is = con.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(isr);
                String line = reader.readLine();
                StringBuffer buffer = new StringBuffer();

                while (line !=null){
                    ReplyItems item=new ReplyItems();
                    String[] s=line.split("&");
                    Log.i("asd1",line);
                    item.userimg=s[0];
                    item.username=s[1];
                    item.message=s[2];
                    item.date=s[3];
                    items.add(item);

                    line=reader.readLine();
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }


        //스레드런메소드 끝났을때
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
