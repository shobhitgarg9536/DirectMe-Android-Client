package in.silive.directme.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.io.IOException;
import java.io.InputStream;

import in.silive.directme.CheckConnectivity;
import in.silive.directme.R;

public class MainActivity extends Activity {

    public static final String Authorization_Token = "Authorization_Token";
    // frame width
    private static final int FRAME_W = 300;
    // frame height
    private static final int FRAME_H = 180;
    // number of frames
    private static final int NB_FRAMES = 20;
    // nb of frames in x
    private static final int COUNT_X = 5;
    // nb of frames in y
    private static final int COUNT_Y = 4;
    // we can slow animation by changing frame duration
    private static final int FRAME_DURATION = 150; // in ms !
    // frame duration
    public boolean net_connected, play_services_available;
    ImageView boat_ImageView;
    RelativeLayout water_image;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sharedpreferences = getSharedPreferences(Authorization_Token, Context.MODE_PRIVATE);
        final String token = sharedpreferences.getString("Authorization_Token", "");
        boat_ImageView = (ImageView) findViewById(R.id.splashboat);
        water_image = (RelativeLayout) findViewById(R.id.spritesheet);
        Bitmap waterbmp = getBitmapFromAssets(this, "splashh.png");
        if (waterbmp != null) {

            Bitmap[] bitmaps = new Bitmap[NB_FRAMES];
            int currentFrame = 0;

            for (int i = 0; i < COUNT_Y; i++) {
                for (int j = 0; j < COUNT_X; j++) {
                    bitmaps[currentFrame] = Bitmap.createBitmap(waterbmp, FRAME_W
                            * j, FRAME_H * i, FRAME_W, FRAME_H);


                    if (++currentFrame >= NB_FRAMES) {
                        break;
                    }
                }
            }

            // create animation programmatically
            final AnimationDrawable animation = new AnimationDrawable();
            animation.setOneShot(false); // repeat animation

            for (int i = 0; i < NB_FRAMES; i++) {
                animation.addFrame(new BitmapDrawable(getResources(), bitmaps[i]),
                        FRAME_DURATION);
            }

            // load animation on image
            if (Build.VERSION.SDK_INT < 16) {
                water_image.setBackgroundDrawable(animation);
            } else {
                water_image.setBackground(animation);
            }

            // start animation on image
            water_image.post(new Runnable() {

                @Override
                public void run() {
                    animation.start();
                }

            });

        }


        net_connected = CheckConnectivity.isNetConnected(getApplicationContext());
        try {
            play_services_available = isGooglePlayServicesAvailable();
            if (play_services_available) {
                if (net_connected) {


                    Thread timer = new Thread() {
                        public void run() {
                            try {
                                Animation animation_boat = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.boatanim);


                                boat_ImageView.startAnimation(animation_boat);
                                sleep(5000);

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } finally {
                                if (!token.equals("")) {

                                    Intent i = new Intent(MainActivity.this, DashboardActivity.class);
                                    startActivity(i);
                                } else {
                                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
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
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onPause() {
        finish();
        super.onPause();
    }
    protected void onDestroy() {
        super.onDestroy();

        finish();
    }

    @Override
    public void onStop() {
        super.onStop();

    }


    public void alertDialog(String title, String message) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
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
                        onDestroy();
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

    private Bitmap getBitmapFromAssets(MainActivity mainActivity,
                                       String filepath) {
        AssetManager assetManager = mainActivity.getAssets();
        InputStream istr = null;
        Bitmap bitmap = null;

        try {
            istr = assetManager.open(filepath);
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException ioe) {
            // manage exception
            ioe.printStackTrace();
        } finally {
            if (istr != null) {
                try {
                    istr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return bitmap;
    }


}
