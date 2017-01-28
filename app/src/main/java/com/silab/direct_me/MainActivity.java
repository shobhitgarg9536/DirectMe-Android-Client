package com.silab.direct_me;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.List;

public class MainActivity extends Activity {
    TextView textView;
    MediaPlayer mp;
    CheckConnectivity n;
    Context c;
    ImageView animImageView,img;
    Intent svc;

  //  public static final String MyPREFERENCES = "MyPrefs" ;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST =9000 ;
    public static final String Authorization_Token = "Authorization_Token" ;
    SharedPreferences sharedpreferences;
    public  boolean m,g ;
    MediaPlayer player;
    Context context;
    BackgroundSoundService bs;
    @Override    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fb);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sharedpreferences = getSharedPreferences(Authorization_Token, Context.MODE_PRIVATE);
        final String token = sharedpreferences.getString("Authorization_Token" , "");
        animImageView = (ImageView) findViewById(R.id.imageView);
          img = (ImageView) findViewById(R.id.imageView8);
        svc=new Intent(MainActivity.this, BackgroundSoundService.class);
        startService(svc);
        textView = (TextView) findViewById(R.id.text);
        m = n.isNetConnected(getApplicationContext());
        try{
            g = isGooglePlayServicesAvailable();
            if (g) {
                if (m) {


                    Thread timer = new Thread() {
                        public void run() {
                            try {
                                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
                                Animation animationb= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.boatanim);
                                animImageView.startAnimation(animationb);
                                img.startAnimation(animation);
                                sleep(5000);

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } finally {
                                if (!token.equals("")) {
                                    svc=new Intent(MainActivity.this, BackgroundSoundService.class);
                                    startService(svc);
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
                alertDialog.setPositiveButton("Settings",
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


    public void onResume() {
        super.onResume();
    }
    protected void onPause() {
        if (this.isFinishing()){ //basically BACK was pressed from this activity
            player.stop();

        }
        Context context = getApplicationContext();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        if (!taskInfo.isEmpty()) {
            ComponentName topActivity = taskInfo.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                player.stop();

            }
            else {

            }
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(svc);
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
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_HOME)) {
            Toast.makeText(this, "You pressed the home button!", Toast.LENGTH_LONG).show();
            stopService(svc);
            return true;
        }
        return super.onKeyDown(keyCode, event);
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

