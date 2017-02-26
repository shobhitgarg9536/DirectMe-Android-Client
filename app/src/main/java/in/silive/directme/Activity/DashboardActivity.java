package in.silive.directme.Activity;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Observable;

import in.silive.directme.AsyncTask.FetchData;
import in.silive.directme.AsyncTask.FirebaseTokenBackgroundWorker;
import in.silive.directme.CheckConnectivity;
import in.silive.directme.Controller;
import in.silive.directme.Interface.AsyncResponse;
import in.silive.directme.R;
import in.silive.directme.Utils.API_URL_LIST;
import in.silive.directme.Utils.FCMConfig;


public class DashboardActivity extends AppCompatActivity implements View.OnClickListener, java.util.Observer {

    public static final String[] co = new String[5];
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Authorization_Token = "Authorization_Token";
    String token;
    public int[] commod = new int[5];
    int i;
    SharedPreferences sharedpreferences;
    Controller controller = new Controller();
    boolean network_available;
    BroadcastReceiver mRegistrationBroadcastReceiver;
    FetchData apicalling;
    private ImageView park, parked, parking, garage, showroom, coinimg, coinimg2, dashboard, Volume;
    private TextView bamboo, coconut, banana, timber, gold_coin, log_out;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//to make screen full screen


        bamboo = (TextView) findViewById(R.id.textviewbamboo);
        coconut = (TextView) findViewById(R.id.textviewcoconut);
        banana = (TextView) findViewById(R.id.textviewbanana);
        timber = (TextView) findViewById(R.id.textviewtimber);
        gold_coin = (TextView) findViewById(R.id.textviewgoldCoin);

        log_out = (TextView) findViewById(R.id.textviewgoldCoin);
        coinimg = (ImageView) findViewById(R.id.coin);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        park = (ImageView) findViewById(R.id.imageviewpark);
        parked = (ImageView) findViewById(R.id.imageviewparked);
        parking = (ImageView) findViewById(R.id.imageviewparking);
        garage = (ImageView) findViewById(R.id.imageviewgarage);
        showroom = (ImageView) findViewById(R.id.imageviewshowroom);

        park.setOnClickListener(this);
        parked.setOnClickListener(this);
        parking.setOnClickListener(this);
        garage.setOnClickListener(this);
        showroom.setOnClickListener(this);


        controller.addObserver(DashboardActivity.this);
        count();

        SharedPreferences sharedpreferences1 = getSharedPreferences(Authorization_Token , MODE_PRIVATE);
        token = sharedpreferences1.getString("Authorization_Token","");

        //// TODO: 2/20/2017 change with correct fcm url and uncomment

        if (CheckConnectivity.isNetConnected(DashboardActivity.this)) {

            SharedPreferences sharedPreferences = getSharedPreferences(FCMConfig.SHARED_PREF, 0);
            String firebase_id_send_to_server_or_not = sharedPreferences.getString("FirebaseIdSendToServer", "");

            if (firebase_id_send_to_server_or_not.equals("0")) {

                String Firebase_token = sharedPreferences.getString("regId", "");


                FirebaseTokenBackgroundWorker firebaseTokenBackgroundWorker = new FirebaseTokenBackgroundWorker(new AsyncResponse() {
                    @Override
                    public void processFinish(String output) {
                        System.out.println(output);
                    }
                });

                FetchData fetchData = new FetchData(new AsyncResponse() {
                    @Override
                    public void processFinish(String output) {

                    }
                });
                String post_data="";
                try {
                    post_data = URLEncoder.encode("access_token", "UTF-8") + "=" + URLEncoder.encode(Firebase_token, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                fetchData.execute(API_URL_LIST.FIREBASE_TOKEN_UPDATE, "POST", token, post_data);

                firebaseTokenBackgroundWorker.execute(token);

            }

        }
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(FCMConfig.REGISTRATION_COMPLETE)) {
                    // fcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(FCMConfig.TOPIC_GLOBAL);


                } else if (intent.getAction().equals(FCMConfig.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();


                }
            }
        };

    }

    public void count() {


        network_available = CheckConnectivity.isNetConnected(getApplicationContext());
        if (network_available) {

            apicalling = new FetchData(new AsyncResponse() {
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
            apicalling.execute(API_URL_LIST.COMODITY_URL, "GET", token, "" );
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
