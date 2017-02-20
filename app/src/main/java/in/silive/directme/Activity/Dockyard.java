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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import in.silive.directme.AsyncTask.ApiCalling;
import in.silive.directme.CheckConnectivity;
import in.silive.directme.Fragments.Boats_equipped;
import in.silive.directme.Interface.AsyncResponse;
import in.silive.directme.R;
import in.silive.directme.Utils.API_URL_LIST;

import static in.silive.directme.Activity.MainActivity.Authorization_Token;


public class Dockyard extends AppCompatActivity {
    public static final String MyPREFERENCES = "UserName";
    JSONArray jArray;
    ViewPager mViewPager;
    boolean network_available;
    ApiCalling apicalling;
    int count = 1;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage_viewpager);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        sharedpreferences = getSharedPreferences(Authorization_Token, Context.MODE_PRIVATE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        connect();

    }

    void startfragments() {
        mViewPager.setAdapter(new Dockyard.BoatPagerAdapter(
                getSupportFragmentManager()));
    }

    void connect() {
        final String token = sharedpreferences.getString("Authorization_Token", "");
        network_available = CheckConnectivity.isNetConnected(getApplicationContext());
        if (network_available) {
            apicalling = new ApiCalling(new AsyncResponse() {
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
            }, this);
            apicalling.execute(API_URL_LIST.PARKED_URL, token, "get");

        }
    }


    public class BoatPagerAdapter extends FragmentPagerAdapter {

        int slot = 0;

        public BoatPagerAdapter(FragmentManager fm) {

            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            JSONObject json_send = null;
            if (jArray != null) {
                try {
                    if (position == 0) {
                        slot = 1;
                        json_send = jArray.getJSONObject(0);
                        return Boats_equipped.newInstance(json_send, slot);
                    }
                    if (position == 1) {
                        slot = 2;
                        json_send = jArray.getJSONObject(1);
                        return Boats_equipped.newInstance(json_send, slot);
                    }
                    if (position == 2) {
                        slot = 3;
                        json_send = jArray.getJSONObject(2);
                        return Boats_equipped.newInstance(json_send, slot);
                    }
                    if (position == 3) {
                        slot = 4;
                        json_send = jArray.getJSONObject(3);
                        return Boats_equipped.newInstance(json_send, slot);
                    } else {
                        slot = 5;
                        json_send = jArray.getJSONObject(4);
                        return Boats_equipped.newInstance(json_send, slot);
                    }

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

