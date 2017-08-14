package com.apriluziknaver.projectmypets;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

/**
 * Created by mapri on 2017-08-09.
 */

public class ScheduleFragmentAdapter extends FragmentPagerAdapter {

    Fragment[] frags = new Fragment[2];
    String[] titles = new String[]{"Note","Training"};


    String username;


    public ScheduleFragmentAdapter(FragmentManager fm ,String username) {
        super(fm);
        this.username=username;

        Bundle bundle = new Bundle();
        bundle.putString("User",username);

        frags[0] = new ScheduleNoteFragment();
        frags[0].setArguments(bundle);
        frags[1] = new ScheduleTrainingFragment();


    }

    @Override
    public Fragment getItem(int position) {
        return frags[position];
    }

    @Override
    public int getCount() {
        return frags.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
