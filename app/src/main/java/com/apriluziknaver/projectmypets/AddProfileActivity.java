package com.apriluziknaver.projectmypets;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class AddProfileActivity extends AppCompatActivity {

    Intent intent;
    ProfileListItem profileListItem;

    EditText name;
    EditText birth;
    EditText breed;
    EditText color;

    RadioGroup rbGroup;
    RadioButton male, female;
    int icon;

    String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile);

        intent = getIntent();

        profileListItem = new ProfileListItem();
        name = (EditText) findViewById(R.id.edit_name);
        birth = (EditText) findViewById(R.id.edit_birth);
        breed = (EditText) findViewById(R.id.edit_breed);
        color = (EditText) findViewById(R.id.edit_color);
        rbGroup = (RadioGroup) findViewById( R.id.rb_group);
        female = (RadioButton) findViewById(R.id.rb_female);
        male = (RadioButton) findViewById(R.id.rb_male);

        rbGroup.setOnCheckedChangeListener(listener);





    }

    //라디오버튼으로 아이콘 바꾸기
    RadioGroup.OnCheckedChangeListener listener=new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

            switch (i){
                case R.id.rb_male :

                    icon= R.drawable.m;
                    profileListItem.imgIc = icon;
                    gender = "male";
                    intent.putExtra("Icon",icon);
                    intent.putExtra("Gender",gender);
                    break;

                case R.id.rb_female:

                    icon = R.drawable.f;
                    profileListItem.imgIc = icon;
                    gender="female";
                    intent.putExtra("Icon",icon);
                    intent.putExtra("Gender",gender);
                    break;
            }



        }
    };


    public void clickOK(View v){

// 이름 미입력시
        if(name.getText().toString().equals("")){

            new AlertDialog.Builder(this)
                    .setMessage("이름을 입력해주세요.")
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).show();

        }


        intent.putExtra("Name",name.getText().toString());
        intent.putExtra("Birth",birth.getText().toString());
        intent.putExtra("Breed",breed.getText().toString());
        intent.putExtra("Color",color.getText().toString());



        setResult(RESULT_OK,intent);

        //이름이 "" (초기화값)이 아니면 액티비티 finish
        if(!name.getText().toString().equals("")) {

            finish();
        }

    }

    public void clickCANCEL(View v){

        setResult(RESULT_CANCELED,intent);
        finish();

    }
}
