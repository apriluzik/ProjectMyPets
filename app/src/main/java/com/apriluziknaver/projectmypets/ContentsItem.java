package com.apriluziknaver.projectmypets;

/**
 * Created by mapri on 2017-08-22.
 */

public class ContentsItem {

    String titleUser;
    String titleUserImg;
    String titleTime;
    String conTxt;
    String conImg;

    public ContentsItem() {
    }

    public ContentsItem(String titleUser,String titleTime, String conTxt) {
        this.titleUser = titleUser;
        this.titleTime=titleTime;
        this.conTxt = conTxt;
    }
}
