package in.silive.directme.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import org.json.JSONArray;
import org.json.JSONException;

import in.silive.directme.adapter.ParkingPagerAdapter;
import in.silive.directme.application.DirectMe;
import in.silive.directme.network.FetchData;
import in.silive.directme.utils.Constants;
import in.silive.directme.utils.NetworkUtils;
import in.silive.directme.listeners.FetchDataListener;
import in.silive.directme.R;
import in.silive.directme.utils.API_URL_LIST;


public class ParkingActivity extends AppCompatActivity {
    ViewPager mViewPager;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parkingmain_viewpager);
        mViewPager = (ViewPager) findViewById(R.id.pagerr);
        sharedpreferences = DirectMe.getInstance().sharedPrefs;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        startfragments();
    }


    void startfragments() {
        mViewPager.setAdapter(new ParkingPagerAdapter(
                getSupportFragmentManager()));
    }


}
