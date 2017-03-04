package in.silive.directme.activity;

import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;

import in.silive.directme.R;
import in.silive.directme.fragments.UserDetailsFragment;

//// TODO: 2/22/2017 set animation in a method and uncomment

public class ParkNowActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String times = "null";
    HashMap<String, String> queryValues;
    ArrayList<HashMap<String, String>> users;
    ImageView cloud1, cloud2;
    String token;

    SharedPreferences sharedPreferences, sharedPreferences1;
    SharedPreferences.Editor editor1;
    Thread thread;
    Animation animation, animationb;
    View frag;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    UserDetailsFragment fragment;
    private ImageView island1, island2, island3, island4, pirate_Island;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parknow);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        island1 = (ImageView) findViewById(R.id.imageViewisland1);
        island2 = (ImageView) findViewById(R.id.imageViewisland2);
        island3 = (ImageView) findViewById(R.id.imageViewisland3);
        island4 = (ImageView) findViewById(R.id.imageViewisland4);
        pirate_Island = (ImageView) findViewById(R.id.imageViewpirateisland);
        //  lvUsers =(ListView) findViewById(R.id.listviewusers);

        //     llUserList = (LinearLayout) findViewById(R.id.linearLayoutUserList);
        //    removeuserList = (Button) findViewById(R.id.buttonRemoveUserList);

        island1.setOnClickListener(this);
        island2.setOnClickListener(this);
        island3.setOnClickListener(this);
        island4.setOnClickListener(this);
        pirate_Island.setOnClickListener(this);


        final ImageView animImageView = (ImageView) findViewById(R.id.water);

        animImageView.setBackgroundResource(R.drawable.animation);
        animImageView.post(new Runnable() {
            @Override
            public void run() {
                AnimationDrawable frameAnimation =
                        (AnimationDrawable) animImageView.getBackground();
                frameAnimation.start();
            }
        });
        // removeuserList.setOnClickListe
        // ner(this);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.imageViewisland1:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_left,
                        R.anim.exit_to_right);
                fragment = new UserDetailsFragment();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;
            case R.id.imageViewisland2:

                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_left,
                        R.anim.exit_to_right);
                fragment = new UserDetailsFragment();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;
            case R.id.imageViewisland3:

                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_left,
                        R.anim.exit_to_right);
                fragment = new UserDetailsFragment();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case R.id.imageViewisland4:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_left,
                        R.anim.exit_to_right);
                fragment = new UserDetailsFragment();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;

            case R.id.imageViewpirateisland:

                break;
        }
    }
}






