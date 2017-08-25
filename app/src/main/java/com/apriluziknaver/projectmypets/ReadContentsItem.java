package com.apriluziknaver.projectmypets;

/**
 * Created by mapri on 2017-08-22.
 */

public class ReadContentsItem {

    String titleUser;
    String titleUserImg;
    String titleTime;
    String conTxt;
    String conImg;

    public ReadContentsItem() {
    }

    public ReadContentsItem(String titleUser, String titleTime, String conTxt) {
        this.titleUser = titleUser;
        this.titleTime=titleTime;
        this.conTxt = conTxt;
    }
}
