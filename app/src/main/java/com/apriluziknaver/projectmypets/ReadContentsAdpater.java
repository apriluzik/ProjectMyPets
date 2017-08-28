package com.apriluziknaver.projectmypets;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

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
 * Created by mapri on 2017-08-22.
 */

public class ReadContentsAdpater extends RecyclerView.Adapter {


    Context context;
    ArrayList<ReadContentsItem> items;
    String id;
    String pw;


    public ReadContentsAdpater(Context context, ArrayList<ReadContentsItem> items) {
        this.context = context;
        this.items = items;
    }

    public ReadContentsAdpater(Context context, ArrayList<ReadContentsItem> items, String id, String pw) {
        this.context = context;
        this.items = items;
        this.id = id;
        this.pw = pw;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemview = LayoutInflater.from(context).inflate(R.layout.commu_item, parent, false);
        RecyclerView.ViewHolder holder = new ViewHolder(itemview);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ViewHolder mholder = (ViewHolder) holder;

        Glide.with(context).load("http://kghy234.dothome.co.kr/MyPets/uploadimg/" + items.get(position).titleUserImg).into(mholder.titleImg);
        mholder.titleName.setText(items.get(position).titleUser);
        mholder.titleTime.setText(items.get(position).titleTime);

        Glide.with(context).load(items.get(position).conImg).into(mholder.contentImg);
        mholder.contents.setText(items.get(position).conTxt);

       ReplyAdapter adapter = new ReplyAdapter(context,items.get(position).id);
        mholder.replyView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        mholder.replyView.setAdapter(adapter);


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleName;
        TextView titleTime;
        CircleImageView titleImg;

        ImageView contentImg;
        TextView contents;

        EditText reply;
        Button addReply;

        RecyclerView replyView;



        public ViewHolder(View itemView) {
            super(itemView);

            titleName = itemView.findViewById(R.id.cm_name);
            titleTime = itemView.findViewById(R.id.cm_time);
            titleImg = itemView.findViewById(R.id.cm_userimg);

            contents = itemView.findViewById(R.id.cm_content_tv);
            contentImg = itemView.findViewById(R.id.cm_content_img);


            reply = itemView.findViewById(R.id.edit_reply);
            addReply = itemView.findViewById(R.id.btn_reply);

            //reply ok버튼
            addReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (reply.getText().toString().length() == 0 || id == null) {
                        return;
                    }

                    addReplyDB(items.get(getLayoutPosition()).id, reply.getText().toString(),reply);


                }
            });

            Log.d("addReply2", items.size() + "");

            // Reply recycler

            replyView = itemView.findViewById(R.id.reply_recycler);
//            adapter = new ReplyAdapter(context,items.get(getLayoutPosition()).id);
//            replyView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
//            replyView.setAdapter(adapter);


        }//viewHolder 생성자
    }//viewhoder

    public void addReplyDB(final int i, final String s,final EditText editText) {


        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://kghy234.dothome.co.kr/MyPets/writeReply.php");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setUseCaches(false);

                    OutputStream os = connection.getOutputStream();
                    String data = "parent=" + i + "&msg=" + s + "&name=" + id + "&img=" + "" + id + pw + ".jpg";
                    os.write(data.getBytes());
                    os.flush();
                    os.close();



                    InputStreamReader isr=new InputStreamReader(connection.getInputStream());
                    BufferedReader reader = new BufferedReader(isr);
                    String line = reader.readLine();
                    while(line!=null){
                        Log.i("asd",line);
                        line=reader.readLine();
                    }

                    ((CommunityActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            editText.setText("");
                        }
                    });

                    //$parent = $_POST['parent'];$name=$_POST['name'];$msg=$_POST['msg'];$img=$_POST['img'];


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


        }.start();

        //$parent = $_POST['parent'];$name=$_POST['name'];$msg=$_POST['msg'];$img=$_POST['img'];


    }
}
