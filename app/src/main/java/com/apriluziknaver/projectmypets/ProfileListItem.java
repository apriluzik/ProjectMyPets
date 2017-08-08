package com.apriluziknaver.projectmypets;

/**
 * Created by mapri on 2017-08-01.
 */

public class ProfileListItem {


    String name="";
    int imgId = R.drawable.zava;
    int imgIc;
    String birth="";
    String breed="";
    String color ="";
    String gender = "";

    //체크노트
    //몸무게,산책,사료,시간 ++..
    //스케줄
    //이빨,귀,발톱,목욕,예방,구충

    int weight;




    public ProfileListItem() {

    }

    public ProfileListItem(String name, int imgId, int imgIc) {
        this.name = name;
        this.imgId = imgId;
        this.imgIc = imgIc;

    }

    public ProfileListItem(String name, int imgIc, String birth, String breed, String color, String gender) {
        this.name = name;
        this.imgIc = imgIc;
        this.birth = birth;
        this.breed = breed;
        this.color = color;
        this.gender = gender;

    }


}
