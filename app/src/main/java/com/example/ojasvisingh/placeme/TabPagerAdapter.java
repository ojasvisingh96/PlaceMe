package com.example.ojasvisingh.placeme;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.ArrayList;

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    int tabCount;
    ArrayList<ArrayList<String>> dataset;
    public TabPagerAdapter(FragmentManager fm, int numberOfTabs,ArrayList<ArrayList<String>> myDataset) {
        super(fm);
        this.tabCount = numberOfTabs;
        this.dataset=myDataset;
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