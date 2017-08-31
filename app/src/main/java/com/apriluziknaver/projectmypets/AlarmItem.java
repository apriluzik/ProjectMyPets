package com.apriluziknaver.projectmypets;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mapri on 2017-08-29.
 */

public class AlarmItem implements Parcelable{

    protected AlarmItem(Parcel in) {
        id = in.readInt();
        alarmtitle = in.readString();
        alarmdate = in.readString();
        alarmTime = in.readString();
        alarmCycle = in.readString();
        isOn = in.readInt();
        parent = in.readString();
    }

    public static final Creator<AlarmItem> CREATOR = new Creator<AlarmItem>() {
        @Override
        public AlarmItem createFromParcel(Parcel in) {
            return new AlarmItem(in);
        }

        @Override
        public AlarmItem[] newArray(int size) {
            return new AlarmItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(alarmtitle);
        parcel.writeString(alarmdate);
        parcel.writeString(alarmTime);
        parcel.writeString(alarmCycle);
        parcel.writeInt(isOn);
        parcel.writeString(parent);
    }

    int id;
    String parent;
    String alarmtitle;
    String alarmdate;
    String alarmTime;
    String alarmCycle;

    int isOn = 0;



    public AlarmItem() {
    }
}
