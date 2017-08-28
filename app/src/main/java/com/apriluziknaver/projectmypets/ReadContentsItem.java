package com.apriluziknaver.projectmypets;

/**
 * Created by mapri on 2017-08-22.
 */

public class ReadContentsItem {
    int id;
    String titleUser;
    String titleUserImg;
    String titleTime;

    String conTxt;
    String conImg;
    String conVdo;



    public ReadContentsItem() {

    }

    public ReadContentsItem(String titleUser, String titleTime, String conTxt) {
        this.titleUser = titleUser;
        this.titleTime=titleTime;
        this.conTxt = conTxt;
    }
}
