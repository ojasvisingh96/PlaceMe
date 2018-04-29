package com.example.ojasvisingh.placeme;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class TabPagerAdapter extends FragmentPagerAdapter {

    int tabCount;

    public TabPagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.tabCount = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Job_opening tab1 = new Job_opening();
                Log.d("YOLO", "getItem: called job opening ");
                return tab1;
            case 1:
                Enrolled tab2 = new Enrolled();
                Log.d("YOLO", "getItem: called enrolled ");
                return tab2;
            case 2:
                Statistics tab3 = new Statistics();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }

}