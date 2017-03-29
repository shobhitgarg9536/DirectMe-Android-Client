package in.silive.directme.activity;

/**
 * Created by simran on 2/24/2017.
 */

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import in.silive.directme.R;
import in.silive.directme.adapter.BoatPagerAdapterParkNow;
import in.silive.directme.application.DirectMe;
import in.silive.directme.dialog.AlertDialog;
import in.silive.directme.listeners.FetchDataListener;
import in.silive.directme.network.FetchData;
import in.silive.directme.utils.API_URL_LIST;
import in.silive.directme.utils.Constants;
import in.silive.directme.utils.NetworkUtils;


public class ParkNowShipActivity extends AppCompatActivity {
    ViewPager mViewPager;
    int count = 1;
    ImageView left, right;
    private SharedPreferences sharedpreferences;
    boolean network_available;
    private FetchData apicalling;
    JSONArray jArray;
    JSONArray jsonArray;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage_viewpager);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        sharedpreferences = DirectMe.getInstance().sharedPrefs;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        left = (ImageView) findViewById(R.id.imageView_garage_left_navigation);
        right = (ImageView) findViewById(R.id.imageView_garage_right_navigation);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.arrowScroll(View.FOCUS_LEFT);
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.arrowScroll(View.FOCUS_RIGHT);
            }
        });
      connect();
    }
    void connect() {
        final String token = sharedpreferences.getString(Constants.AUTH_TOKEN, "");
        network_available = NetworkUtils.isNetConnected();
        if (network_available) {
            apicalling = new FetchData(new FetchDataListener() {
                @Override
                public void processStart() {

                }

                @Override
                public void processFinish(String output) {
                    try {
                        jArray = new JSONArray(output);
                        String ship_status;
                        jsonArray = new JSONArray();
                        for(int i =0; i<jArray.length(); i++) {
                            JSONObject jsonObject = jArray.getJSONObject(i);
                            ship_status = jsonObject.getString("ship_status");
                            if(!ship_status.equals("null"))
                                jsonArray.put(jsonObject);
                        }
                        count = jsonArray.length();
                        startfragments();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, this);
            apicalling.setArgs(API_URL_LIST.GARAGE_SHIPS_URL, token, "");
            apicalling.execute();

        }else{
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.alertDialog(this);
        }
    }
    void startfragments() {
        mViewPager.setAdapter(new BoatPagerAdapterParkNow(
                getSupportFragmentManager() , jsonArray , count));
    }


}
