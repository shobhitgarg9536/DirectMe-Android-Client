package in.silive.directme.Activity;

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

import in.silive.directme.network.FetchData;
import in.silive.directme.CheckConnectivity;
import in.silive.directme.Fragments.UserShipsFragment;
import in.silive.directme.Interface.AsyncResponse;
import in.silive.directme.R;
import in.silive.directme.Utils.API_URL_LIST;

import static in.silive.directme.Activity.MainActivity.Authorization_Token;


/**
 * Created by Lenovo on 01-Dec-16.
 */

public class ParkingActivity extends AppCompatActivity {
    public static final String MyPREFERENCES = "UserName";
    ViewPager mViewPager;
    boolean network_available;
    FetchData apicalling;
    int count = 1;
    JSONArray user;
    ImageView leftNavigation, rightNavigation;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parkingmain_viewpager);
        mViewPager = (ViewPager) findViewById(R.id.pagerr);
        sharedpreferences = getSharedPreferences(Authorization_Token, Context.MODE_PRIVATE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        leftNavigation = (ImageView) findViewById(R.id.left_navigation);
        rightNavigation = (ImageView) findViewById(R.id.right_navigation);


        connect();
    }

    void startfragments() {
        mViewPager.setAdapter(new BoatPagerAdapter(
                getSupportFragmentManager()));
    }

    void connect() {
        final String token = sharedpreferences.getString("Authorization_Token", "");
        network_available = CheckConnectivity.isNetConnected(getApplicationContext());
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
