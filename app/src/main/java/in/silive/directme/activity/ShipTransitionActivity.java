package in.silive.directme.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.silive.directme.R;
import in.silive.directme.application.DirectMe;
import in.silive.directme.utils.BitmapUtils;
import in.silive.directme.utils.Constants;

/**
 * Created by simran on 3/3/2017.
 */

public class ShipTransitionActivity extends AppCompatActivity implements Animation.AnimationListener{
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

    @BindView(R.id.boatanimate)
    ImageView iv_boat;
    @BindView(R.id.constraintLayout)
    ConstraintLayout rl;
    @BindView(R.id.island)
    ImageView island;
    Animation animation_boat;
    private Thread thread;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transitionparknow);
        ButterKnife.bind(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        startAnimation();
        sharedPreferences = DirectMe.getInstance().sharedPrefs;
        final String id=sharedPreferences.getString(Constants.ISLAND_ID,"");
        islandImage(id);
         animation_boat = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.boatanimtransition);

        animation_boat.setAnimationListener(this);
       startBoatanimation();
    }
    private void startBoatanimation()
    {
        final String ship_img_url=sharedPreferences.getString("SHIP_IMAGE_URL","");
        Picasso.with(getApplicationContext())
                .load(ship_img_url)
                .into(iv_boat);
        iv_boat.startAnimation(animation_boat);

    }
    private void startAnimation() {
        Bitmap waterbmp = BitmapUtils.getBitmapFromAssets("splashh.png");
        if (waterbmp != null) {

            Bitmap[] bitmaps = BitmapUtils.getBitmapsFromSprite(waterbmp, NB_FRAMES, COUNT_X, COUNT_Y, FRAME_H, FRAME_W);

            // create animation programmatically
            final AnimationDrawable animation = new AnimationDrawable();
            animation.setOneShot(false); // repeat animation
            for (int i = 0; i < NB_FRAMES; i++) {
                animation.addFrame(new BitmapDrawable(getResources(), bitmaps[i]),
                        FRAME_DURATION);
            }

            // load animation on image
            if (Build.VERSION.SDK_INT < 16) {
                rl.setBackgroundDrawable(animation);
            } else {
                rl.setBackground(animation);
            }

            // start animation on image
            rl.post(new Runnable() {

                @Override
                public void run() {
                    animation.start();
                }

            });

        }
    }
    void alertdialog()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Congratulation");
        alertDialog.setMessage("Ship parked succesfully");
        alertDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i=new Intent(getApplicationContext(),DashboardActivity.class);
                        startActivity(i);

                    }
                });
        alertDialog.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }
    void islandImage(String island_id)
    {
        int id=Integer.parseInt(island_id);
        switch (id)
        {
            case 1:island.setImageResource(R.drawable.ic_coconut_island);
                break;
            case 2:island.setImageResource(R.drawable.ic_wood_island);
                break;
            case 3:island.setImageResource(R.drawable.ic_banana_island);
                break;
            case 4:island.setImageResource(R.drawable.ic_banboo_island);
                break;
            case 5:island.setImageResource(R.drawable.ic_pyrate_island);
                break;
        }

    }


    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        Intent i=new Intent(getApplicationContext(),ParkedActivity.class);
        startActivity(i);

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
