package in.silive.directme.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import in.silive.directme.fragments.DockyardFargment;
import in.silive.directme.fragments.ParknowUsershipselectFragment;

/**
 * Created by simran on 3/4/2017.
 */

public class BoatPagerAdapterParkNow extends FragmentPagerAdapter {

    int slot = 0;
    JSONArray jArray;
    int count;


    public BoatPagerAdapterParkNow(FragmentManager fm , JSONArray jsonArray , int c) {

        super(fm);
        jArray = jsonArray;
        count = c;
    }

    @Override
    public Fragment getItem(int position) {
        JSONObject json_send = null;
        if (jArray != null) {
            try {
                json_send = jArray.getJSONObject(position);


                ParknowUsershipselectFragment parknowUsershipselectFragment = new ParknowUsershipselectFragment();
                Bundle args = new Bundle();
                args.putString("data", json_send.toString());


                parknowUsershipselectFragment.setArguments(args);
                return parknowUsershipselectFragment;

            } catch (JSONException e) {
                e.printStackTrace();
                return null;

            }
        } else
            return null;
    }

    @Override
    public int getCount() {
        return count;

    }
}