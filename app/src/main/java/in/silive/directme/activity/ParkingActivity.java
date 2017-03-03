package in.silive.directme.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;

import in.silive.directme.application.DirectMe;
import in.silive.directme.network.FetchData;
import in.silive.directme.NetworkUtils;
import in.silive.directme.fragments.UserShipsFragment;
import in.silive.directme.listeners.AsyncResponse;
import in.silive.directme.R;
import in.silive.directme.utils.API_URL_LIST;


/**
 * Created by Lenovo on 01-Dec-16.
 */

public class ParkingActivity extends AppCompatActivity {
//    public static final String MyPREFERENCES = "UserName";
    ViewPager mViewPager;
    boolean network_available;
    FetchData apicalling;
    int count = 1;
    JSONArray user;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parkingmain_viewpager);
        mViewPager = (ViewPager) findViewById(R.id.pagerr);
        sharedpreferences = DirectMe.getInstance().sharedPrefs;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);



        connect();
    }

    void startfragments() {
        mViewPager.setAdapter(new BoatPagerAdapter(
                getSupportFragmentManager()));
    }

    void connect() {
        final String token = sharedpreferences.getString("Authorization_Token", "");
        network_available = NetworkUtils.isNetConnected();
        if (network_available) {
            apicalling = new FetchData(new AsyncResponse() {
                @Override
                public void processFinish(String output) {
                    try {
                        user = new JSONArray(output);
                        count = user.length();
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
            /*if (position == 0)
            {
                try {
                    return UserShipsFragment.newInstance(user.getJSONObject(position));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if(position==1) {
                return  new Boatx();
            }
            else
                return new UserShipsFragment();
        }*/
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
            return count;
        }
    }

}
