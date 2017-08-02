package com.apriluziknaver.projectmypets;

/**
 * Created by mapri on 2017-08-01.
 */

public class ProfileListItem {

    String name;
    int imgId;
    int imgIc;

    public ProfileListItem() {

        name = "NAME";
        imgId = R.drawable.zava;
        imgIc = R.drawable.m;
    }

    public ProfileListItem(String name, int imgId, int imgIc) {
        this.name = name;
        this.imgId = imgId;
        this.imgIc = imgIc;
    }
}
