package in.silive.directme.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.constraint.ConstraintLayout;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.silive.directme.R;
import in.silive.directme.application.DirectMe;
import in.silive.directme.utils.BitmapUtils;
import in.silive.directme.utils.Constants;
import in.silive.directme.utils.NetworkUtils;

public class SplashActivity extends Activity implements Animation.AnimationListener {

    private static final int FRAME_W = 300;
    private static final int FRAME_H = 180;
    private static final int NB_FRAMES = 20;
    private static final int COUNT_X = 5;
    private static final int COUNT_Y = 4;
    private static final int FRAME_DURATION = 150; // in ms !

    @BindView(R.id.iv_boat)
    ImageView iv_boat;
    @BindView(R.id.rl)
    ConstraintLayout rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        ButterKnife.bind(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        startWaterAnimation();

        if (NetworkUtils.isGooglePlayServicesAvailable()) {
            if (NetworkUtils.isNetConnected()) {

                Animation animation = AnimationUtils.loadAnimation(this, R.anim.boatanim);
                animation.setFillAfter(true);
                animation.setAnimationListener(this);
                iv_boat.startAnimation(animation);
            } else {
                alertDialog("Error", "Sorry, your device isn't connected to internet");
            }
        } else {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Error");
            alertDialog.setMessage("Please install google play services in order to proceed...");
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
    }

    private void startWaterAnimation() {
        Bitmap waterbmp = BitmapUtils.getBitmapFromAssets("splashh.png");
        if (waterbmp != null) {
            Bitmap[] bitmaps = BitmapUtils.getBitmapsFromSprite(waterbmp, NB_FRAMES, COUNT_X, COUNT_Y, FRAME_H, FRAME_W);
            final AnimationDrawable animation = new AnimationDrawable();
            animation.setOneShot(false); // repeat animation

            for (int i = 0; i < NB_FRAMES; i++) {
                animation.addFrame(new BitmapDrawable(getResources(), bitmaps[i]),
                        FRAME_DURATION);
            }
            if (Build.VERSION.SDK_INT < 16) {
                rl.setBackgroundDrawable(animation);
            } else {
                rl.setBackground(animation);
            }
            rl.post(new Runnable() {

                @Override
                public void run() {
                    animation.start();
                }

            });
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

    @Override
    public void onAnimationStart(Animation animation) {
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        Intent intent;
        if (!DirectMe.getInstance().sharedPrefs.getString(Constants.AUTH_TOKEN, "").equals("")) {
            intent = new Intent(this, DashboardActivity.class);
        } else {
           intent = new Intent(this, LoginActivity.class);
        }
        startActivity(intent);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }
}
