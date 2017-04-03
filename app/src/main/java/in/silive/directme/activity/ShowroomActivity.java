package in.silive.directme.activity;

/**
 * Created by yesha on 20-01-2017.
 */

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;

import in.silive.directme.adapter.ShowroomAdapter;
import in.silive.directme.application.DirectMe;
import in.silive.directme.dialog.AlertDialog;
import in.silive.directme.network.FetchData;
import in.silive.directme.utils.Constants;
import in.silive.directme.utils.NetworkUtils;
import in.silive.directme.listeners.FetchDataListener;
import in.silive.directme.R;
import in.silive.directme.utils.API_URL_LIST;
import in.silive.directme.utils.ViewPagerAnimation;


public class ShowroomActivity extends AppCompatActivity {
    JSONArray jArray;
    ViewPager mViewPager;
    int count = 1, slot;
    boolean network_available;
    FetchData apicalling;
    SharedPreferences sharedpreferences;
    ImageView left,right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showroom_viewpager);
        slot = getIntent().getIntExtra("slot", 0);
        Log.d("slot", "" + slot);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setPageTransformer(false , new ViewPagerAnimation());

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

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position == 0)
                    left.setVisibility(View.GONE);
                else if(position == count-1)
                    right.setVisibility(View.GONE);
                else {
                    left.setVisibility(View.VISIBLE);
                    right.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    void startfragments() {
        mViewPager.setAdapter(new ShowroomAdapter
                (getSupportFragmentManager(), jArray ,count));
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
                        count = jArray.length();
                        startfragments();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, this);
            apicalling.setArgs(API_URL_LIST.SHOWROOM_SHIPS_URL, token, "");
            apicalling.execute();

        }else{
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.alertDialog(this);
        }
    }
}
