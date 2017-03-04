package in.silive.directme.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import in.silive.directme.fragments.UserShipsFragment;

/**
 * Created by simran on 3/4/2017.
 */


public class BoatPagerAdapter extends FragmentPagerAdapter {

    private final JSONArray user;

    public BoatPagerAdapter(JSONArray user, FragmentManager fm) {
        super(fm);
        this.user = user;
    }

    @Override
    public Fragment getItem(int position) {

        if (user != null) {
            try {

                return UserShipsFragment.newInstance(user.getJSONObject(0));
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        return user.length();
    }
}