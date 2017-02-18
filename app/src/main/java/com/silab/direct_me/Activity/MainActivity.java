package com.silab.direct_me.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import com.silab.direct_me.CheckConnectivity;
import com.silab.direct_me.R;

public class MainActivity extends Activity {
    TextView textView;
    MediaPlayer mp;
    CheckConnectivity check_connectivity ;
    Context c;
    ImageView directme_ImageView,boat_image;


  //  public static final String MyPREFERENCES = "MyPrefs" ;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST =9000 ;
    public static final String Authorization_Token = "Authorization_Token" ;
    SharedPreferences sharedpreferences;
    public  boolean net_connected,play_services_available ;
    MediaPlayer media_player;
    Context context;

    @Override    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fb);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sharedpreferences = getSharedPreferences(Authorization_Token, Context.MODE_PRIVATE);
        final String token = sharedpreferences.getString("Authorization_Token" , "");
        directme_ImageView = (ImageView) findViewById(R.id.imageView);
          boat_image = (ImageView) findViewById(R.id.imageView8);

        //textView = (TextView) findViewById(R.id.text);
        net_connected = CheckConnectivity.isNetConnected(getApplicationContext());
        try{
            play_services_available = isGooglePlayServicesAvailable();
            if (play_services_available) {
                if (net_connected) {


                    Thread timer = new Thread() {
                        public void run() {
                            try {
                                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
                                Animation animationb= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.boatanim);
                                directme_ImageView.startAnimation(animationb);
                                boat_image.startAnimation(animation);
                                sleep(5000);

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } finally {
                                if (!token.equals("")) {

                                    Intent i = new Intent(MainActivity.this, Dashboard.class);
                                    startActivity(i);
                                } else {
                                    Intent i = new Intent(MainActivity.this, CreateAccount.class);
                                    startActivity(i);
                                }
                            }
                        }
                    };
                    timer.start();
                } else

                {

                    alertDialog("Error", "Sorry, your device doesn't connect to internet!");
                }

            } else {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("error");
                alertDialog.setMessage("please install google paly services");
                alertDialog.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                onDestroy();
                            }
                        });

                alertDialog.create();
                alertDialog.setCancelable(false);
                alertDialog.show();

            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }

    @Override
//to stop sound when minimised



    protected void onDestroy() {
        super.onDestroy();

        finish();
    }

    @Override
    public void onStop(){
        super.onStop();

    }



    public  void alertDialog(String title , String message){

        AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_WIFI_SETTINGS);
                        startActivity(intent);
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

    }

    public boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability gApi = GoogleApiAvailability.getInstance();
        int resultCode = gApi.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (gApi.isUserResolvableError(resultCode)) {
                return false;
            }
        }
        return true;
    }


}

