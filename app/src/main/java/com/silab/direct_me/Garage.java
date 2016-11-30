package com.silab.direct_me;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by YESH AGNIHOTRI on 19-11-2016.
 */

public class Garage extends AppCompatActivity
{
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(new Garage.BoatPagerAdapter(
                getSupportFragmentManager()));
    }

    public class BoatPagerAdapter extends FragmentPagerAdapter
    {

        public BoatPagerAdapter(FragmentManager fm) {

            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            if (position == 0)
            {
                return new dock1();
            }
            if(position==1)
            {
                return  new dock2();
            }
            if(position==2) {
                return new dock3();
            }
            if(position==3) {
                return new dock4();
            }
              else
                return new dock5();
        }

        @Override
        public int getCount()
        {
            return 5;

        }
    }
}

