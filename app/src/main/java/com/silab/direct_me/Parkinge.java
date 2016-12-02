package com.silab.direct_me;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Lenovo on 01-Dec-16.
 */

public class Parkinge extends AppCompatActivity {
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parkingmain);
        mViewPager = (ViewPager) findViewById(R.id.pagerr);
        mViewPager.setAdapter(new BoatPagerAdapter(
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
                return new Shipx();
            }
            if(position==1) {
                return  new Boatx();
            }
            else
                return new Shipx();
        }

        @Override
        public int getCount()
        {
            return 3;
        }
    }

}
