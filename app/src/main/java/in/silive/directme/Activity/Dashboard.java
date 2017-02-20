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
import in.silive.directme.AsyncTask.ApiCalling;
import in.silive.directme.CheckConnectivity;
import in.silive.directme.Controller;
import in.silive.directme.AsyncTask.FirebaseTokenBackgroundWorker;
import in.silive.directme.Interface.AsyncResponse;
import in.silive.directme.R;
import in.silive.directme.Utils.API_URL_LIST;
import in.silive.directme.Utils.FCMConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Observable;

public class Dashboard extends AppCompatActivity implements View.OnClickListener, java.util.Observer {

    private ImageView park, parked, parking, garage, showroom, coinimg, coinimg2, dashboard, Volume;
    private TextView bamboo, coconut, banana, timber, gold_coin, log_out;
    int i;
    Intent svc;
    SharedPreferences sharedpreferences;
    public int[] commod = new int[5];
    public static final String[] co = new String[5];
    Controller controller = new Controller();
    public static final String MyPREFERENCES = "MyPrefs";
    CheckConnectivity network;
    boolean network_available;
    BroadcastReceiver mRegistrationBroadcastReceiver;
    ApiCalling apicalling;
    private boolean im=true;
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

        String tok = sharedpreferences.getString("fidKey", "");

        controller.addObserver(Dashboard.this);
        count();

        if(CheckConnectivity.isNetConnected(Dashboard.this)) {

            SharedPreferences sharedPreferences = getSharedPreferences(FCMConfig.SHARED_PREF, 0);
            String firebase_id_send_to_server_or_not = sharedPreferences.getString("FirebaseIdSendToServer", "");

            if (firebase_id_send_to_server_or_not.equals("0")) {
            //    String token = sharedPreferences.getString("regId", "");
            //    sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
            //    String UserContact = sharedPreferences.getString("UserContact", "");
                FirebaseTokenBackgroundWorker firebaseTokenBackgroundWorker = new FirebaseTokenBackgroundWorker(new AsyncResponse() {
                    @Override
                    public void processFinish(String output) {
                        System.out.println(output);
                    }
                });
                firebaseTokenBackgroundWorker.execute("", tok);
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


        network_available = network.isNetConnected(getApplicationContext());
        if (network_available) {
            apicalling = new ApiCalling(new AsyncResponse() {
                @Override
                public void processFinish(String output) {
                    try {
                        JSONObject jsonObject = new JSONObject(output);
                        JSONArray things = jsonObject.getJSONArray("inventory");
                        for (i = 0; i < 5; i++) {
                            JSONObject jsonObject1 = things.getJSONObject(i);
                            commod[i] = Integer.parseInt(jsonObject1.getString("count").toString());

                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            //putting values
                            editor.putString(co[i], Integer.toString(commod[i]));


                            editor.commit();
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
            },this);
            apicalling.execute(API_URL_LIST.COMODITY_URL,"0","get");
        }
       else
            {
                for(i=0;i<5;i++)
                {   if (sharedpreferences.contains(Dashboard.co[i])) {
                    commod[i]= Integer.parseInt(sharedpreferences.getString(Dashboard.co[i],""));



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
                Intent intent = new Intent(this, Parknow.class);
                startActivity(intent);
                break;
            case R.id.imageviewparked:
                Intent intent4 = new Intent(this, Parked.class);
                startActivity(intent4);
                break;
            case R.id.imageviewparking:
                Intent intent1 = new Intent(this, Parkinge.class);
                startActivity(intent1);
                break;
            case R.id.imageviewshowroom:
                Intent i = new Intent(Dashboard.this, Show_room.class);
                startActivity(i);
                break;
            case R.id.imageviewgarage:
                Intent in = new Intent(Dashboard.this, Dockyard.class);
                startActivity(in);
                break;


        }

    }
    @Override
    //updating values through observer
    public void update(Observable observable, Object o) {
        controller = (Controller) observable;
        System.out.println(controller.getBananaCount());
        bamboo.setText(Integer.toString(controller.getBambooCount()));
        coconut.setText(Integer.toString(controller.getCoconutCount()));
        banana.setText(Integer.toString(controller.getBananaCount()));
        timber.setText(Integer.toString(controller.getTimberCount()));
        gold_coin.setText(Integer.toString(controller.getGoldCoinCount()));

    }
}
