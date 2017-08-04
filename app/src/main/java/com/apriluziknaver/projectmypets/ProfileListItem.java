package com.apriluziknaver.projectmypets;

/**
 * Created by mapri on 2017-08-01.
 */

public class ProfileListItem {

    String name="";
    int imgId;
    int imgIc;
    String birth="";
    String breed="";
    String color ="";
    String memo="";



    public ProfileListItem() {

        name = "";

    }

    public ProfileListItem(String name, int imgId, int imgIc) {
        this.name = name;
        this.imgId = imgId;
        this.imgIc = imgIc;

    }
}
