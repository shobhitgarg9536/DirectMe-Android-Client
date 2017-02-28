package in.silive.directme.activity;

/**
 * Created by yesha on 20-01-2017.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import in.silive.directme.application.DirectMe;
import in.silive.directme.network.FetchData;
import in.silive.directme.NetworkUtils;
import in.silive.directme.fragments.ShipsFragment;
import in.silive.directme.listeners.AsyncResponse;
import in.silive.directme.R;
import in.silive.directme.utils.API_URL_LIST;



public class ShowroomActivity extends AppCompatActivity {
//    public static final String Authorization_Token = "Authorization_Token";
    JSONArray jArray;
    ViewPager mViewPager;
    int count = 1, slot;
    boolean network_available;
    FetchData apicalling;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage_viewpager);
        slot = getIntent().getIntExtra("slot", 0);
        Log.d("slot", "" + slot);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        //mViewPager.setOffscreenPageLimit(10);

        sharedpreferences = DirectMe.getInstance().sharedPrefs;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        connect();

    }

    void startfragments() {
        mViewPager.setAdapter(new ShowroomActivity.BoatPagerAdapter
                (getSupportFragmentManager()));
    }

    void connect() {
        final String token = sharedpreferences.getString("Authorization_Token", "");
        network_available = NetworkUtils.isNetConnected();
        if (network_available) {
            apicalling = new FetchData(new AsyncResponse() {
                @Override
                public void processFinish(String output) {
                    try {
                        jArray = new JSONArray(output);
                        count = jArray.length();
                        startfragments();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            apicalling.setArgs(API_URL_LIST.PARKED_URL, token, "");
            apicalling.execute();

        }
    }


    public class BoatPagerAdapter extends FragmentPagerAdapter {

        public BoatPagerAdapter(FragmentManager fm) {

            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            JSONObject json_send = null;
            if (jArray != null) {
                try {
                    json_send = jArray.getJSONObject(position);

                    Log.d("shipapi", json_send.toString());
                    ShipsFragment ships_fragment = new ShipsFragment();
                    Bundle args = new Bundle();
                    args.putString("data", json_send.toString());
                    args.putInt("slot", slot);
                    Log.d("args", args.toString());
                    ships_fragment.setArguments(args);
                    return ships_fragment;

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
}
