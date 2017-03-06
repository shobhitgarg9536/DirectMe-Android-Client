package in.silive.directme.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import in.silive.directme.fragments.ParkingWorldViewFragment;

/**
 * Created by Shobhit-pc on 3/6/2017.
 */

public class ParkingPagerAdapter extends FragmentPagerAdapter {

    public ParkingPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        return new ParkingWorldViewFragment();
    }

    @Override
    public int getCount() {
        return 1;
    }
}