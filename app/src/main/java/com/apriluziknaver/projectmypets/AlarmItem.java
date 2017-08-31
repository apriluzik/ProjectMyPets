package com.apriluziknaver.projectmypets;

/**
 * Created by mapri on 2017-08-29.
 */

public class AlarmItem {

    int id;

    String alarmtitle;
    String alarmdate;
    String alarmTime;
    String alarmCycle;
    int isOn = 0;

    public AlarmItem(String alarmtitle, String alarmdate) {
        this.alarmtitle = alarmtitle;
        this.alarmdate = alarmdate;
    }

    public AlarmItem() {
    }
}
