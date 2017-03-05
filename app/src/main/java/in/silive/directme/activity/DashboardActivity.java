package in.silive.directme.activity;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Observable;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.silive.directme.Controller;
import in.silive.directme.utils.NetworkUtils;
import in.silive.directme.R;
import in.silive.directme.application.DirectMe;
import in.silive.directme.listeners.AsyncResponse;
import in.silive.directme.network.FetchData;
import in.silive.directme.utils.API_URL_LIST;
import in.silive.directme.utils.Constants;
import in.silive.directme.utils.ToasterUtils;


public class DashboardActivity extends AppCompatActivity implements View.OnClickListener, java.util.Observer {

    public static final String[] co = new String[5];
    public int[] commod = new int[5];
    String token;
    int i;
    SharedPreferences sharedpreferences;
    Controller controller = new Controller();
    boolean network_available;
    BroadcastReceiver mRegistrationBroadcastReceiver;
    FetchData apicalling;

    @BindView(R.id.tvBamboo)
    TextView bamboo;
    @BindView(R.id.tvBanana)
    TextView banana;
    @BindView(R.id.tvCoconut)
    TextView coconut;
    @BindView(R.id.tvTimber)
    TextView timber;
    @BindView(R.id.tvGold)
    TextView gold_coin;

    @BindView(R.id.imageviewpark)
    ImageView park;
    @BindView(R.id.imageviewparked)
    ImageView parked;
    @BindView(R.id.imageviewparking)
    ImageView parking;
    @BindView(R.id.imageviewgarage)
    ImageView garage;
    @BindView(R.id.imageviewshowroom)
    ImageView showroom;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        ButterKnife.bind(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//to make screen full screen
        sharedpreferences = DirectMe.getInstance().sharedPrefs;

        park.setOnClickListener(this);
        parked.setOnClickListener(this);
        parking.setOnClickListener(this);
        garage.setOnClickListener(this);
        showroom.setOnClickListener(this);

        controller.addObserver(DashboardActivity.this);


        token = sharedpreferences.getString("Authorization_Token", "");



        //// TODO: 2/20/2017 change with correct fcm url and uncomment
        count();

        if (NetworkUtils.isNetConnected()) {

            String firebase_id_send_to_server_or_not = sharedpreferences.getString("FirebaseIdSendToServer", "");

            if (firebase_id_send_to_server_or_not.equals("0")) {

                String Firebase_token = sharedpreferences.getString("regId", "");

                FetchData fetchData = new FetchData(new AsyncResponse() {
                    @Override
                    public void processStart() {

                    }

                    @Override
                    public void processFinish(String output) {

                    }
                });
                String post_data = "";
                try {
                    post_data = URLEncoder.encode("fcm_token", "UTF-8") + "=" + URLEncoder.encode(Firebase_token, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                fetchData.setArgs(API_URL_LIST.FIREBASE_TOKEN_UPDATE, token, post_data);
                fetchData.execute();

            }

        }
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Constants.REGISTRATION_COMPLETE)) {
                    // fcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Constants.TOPIC_GLOBAL);


                } else if (intent.getAction().equals(Constants.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    ToasterUtils.toaster("Push notification: " + message);
                }
            }
        };

    }

    public void count() {


        network_available = NetworkUtils.isNetConnected();
        if (network_available) {

            apicalling = new FetchData(new AsyncResponse() {
                @Override
                public void processStart() {

                }

                @Override
                public void processFinish(String output) {
                    try {
                        JSONObject jsonObject = new JSONObject(output);
                        JSONArray things = jsonObject.getJSONArray("inventory");
                        for (i = 0; i < 5; i++) {
                            JSONObject jsonObject1 = things.getJSONObject(i);
                            commod[i] = Integer.parseInt(jsonObject1.getString("count"));

                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            //putting values
                            editor.putString(co[i], Integer.toString(commod[i]));
                            editor.apply();
                            controller.setBambooCount(commod[3]);
                            controller.setBananaCount(commod[2]);
                            controller.setTimberCount(commod[1]);
                            controller.setCoconutCount(commod[0]);
                            controller.setGoldCoinCount(commod[4]);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            apicalling.setArgs(API_URL_LIST.COMODITY_URL, token, "");
            apicalling.execute();
        } else {
            for (i = 0; i < 5; i++) {
                if (sharedpreferences.contains(DashboardActivity.co[i])) {
                    commod[i] = Integer.parseInt(sharedpreferences.getString(DashboardActivity.co[i], ""));
                }
            }
            controller.setBambooCount(commod[3]);
            controller.setBananaCount(commod[2]);
            controller.setTimberCount(commod[1]);
            controller.setCoconutCount(commod[0]);
            controller.setGoldCoinCount(commod[4]);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageviewpark:
                Intent intent = new Intent(this, ParkNowShipActivity.class);
                startActivity(intent);
                break;
            case R.id.imageviewparked:
                Intent intent4 = new Intent(this, ParkedActivity.class);
                startActivity(intent4);
                break;
            case R.id.imageviewparking:
                Intent intent1 = new Intent(this, ParkingActivity.class);
                startActivity(intent1);
                break;
            case R.id.imageviewshowroom:
                Intent i = new Intent(DashboardActivity.this, ShowroomActivity.class);
                startActivity(i);
                break;
            case R.id.imageviewgarage:
                Intent in = new Intent(DashboardActivity.this, DockyardActivity.class);
                startActivity(in);
                break;
        }
    }

    @Override
    //updating values through observer
    public void update(Observable observable, Object o) {
        controller = (Controller) observable;
        System.out.println(controller.getBananaCount());
        bamboo.setText(String.valueOf(controller.getBambooCount()));
        coconut.setText(String.valueOf(controller.getCoconutCount()));
        banana.setText(String.valueOf(controller.getBananaCount()));
        timber.setText(String.valueOf(controller.getTimberCount()));
        gold_coin.setText(String.valueOf(controller.getGoldCoinCount()));

    }
}
