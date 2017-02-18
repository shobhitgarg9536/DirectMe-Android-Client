package com.silab.direct_me.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.silab.direct_me.AsyncTask.ApiCalling;
import com.silab.direct_me.CheckConnectivity;
import com.silab.direct_me.Interface.AsyncResponse;
import com.silab.direct_me.R;
import com.silab.direct_me.Fragments.Shipx;
import com.silab.direct_me.Utils.API_URL_LIST;

import org.json.JSONArray;
import org.json.JSONException;

import static com.silab.direct_me.Activity.UserLogin.Authorization_Token;

/**
 * Created by Lenovo on 01-Dec-16.
 */

public class Parkinge extends AppCompatActivity {
    ViewPager mViewPager;
    CheckConnectivity network;
    boolean network_available;
    ApiCalling apicalling;
    int i;
    int count=1;
    JSONArray user;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "UserName";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parkingmain);
        mViewPager = (ViewPager) findViewById(R.id.pagerr);
        sharedpreferences = getSharedPreferences(Authorization_Token, Context.MODE_PRIVATE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        connect();
    }

    void startfragments()
    {
        mViewPager.setAdapter(new BoatPagerAdapter(
                getSupportFragmentManager()));
    }
    void connect() {
        final String token = sharedpreferences.getString("Authorization_Token" , "");
        network_available = network.isNetConnected(getApplicationContext());
        if (network_available) {
            apicalling = new ApiCalling(new AsyncResponse() {
                @Override
                public void processFinish(String output) {
                    try {
                        user = new JSONArray(output);
                        count=user.length();
                        startfragments();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },this);
            apicalling.execute(API_URL_LIST.PARKED_URL,token,"get");

        }
    }

    public class BoatPagerAdapter extends FragmentPagerAdapter
    {

        public BoatPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            /*if (position == 0)
            {
                try {
                    return Shipx.newInstance(user.getJSONObject(position));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if(position==1) {
                return  new Boatx();
            }
            else
                return new Shipx();
        }*/
            if(user!=null) {
                try {

                    return Shipx.newInstance(user.getJSONObject(0));
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            return null;
        }

        @Override
        public int getCount()
        {
            return count;
        }
    }

}
