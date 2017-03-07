package in.silive.directme.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import in.silive.directme.R;
import in.silive.directme.application.DirectMe;
import in.silive.directme.fragments.UserDetailsFragment;
import in.silive.directme.utils.Constants;

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
    String id;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.imageViewisland1:

                fragmentInitialise();
                 sharedpreferences = DirectMe.getInstance().sharedPrefs;
                 editor = sharedpreferences.edit();
                editor.putString(Constants.ISLAND_ID,Constants.BANANA_ISLAND_ID);
                editor.commit();

                break;
            case R.id.imageViewisland2:
                 fragmentInitialise();

                 sharedpreferences = DirectMe.getInstance().sharedPrefs;
                 editor = sharedpreferences.edit();
                editor.putString(Constants.ISLAND_ID,Constants.COCONUT_ISLAND_ID);
                editor.commit();

                break;
            case R.id.imageViewisland3:

                fragmentInitialise();
                sharedpreferences = DirectMe.getInstance().sharedPrefs;
                editor = sharedpreferences.edit();
                editor.putString(Constants.ISLAND_ID,Constants.BAMBOO_ISLAND_ID);
                editor.commit();
                break;
            case R.id.imageViewisland4:
                  fragmentInitialise();

                sharedpreferences = DirectMe.getInstance().sharedPrefs;
                editor = sharedpreferences.edit();
                editor.putString(Constants.ISLAND_ID,Constants.TIMBER_ISLAND_ID);
                editor.commit();
                break;

            case R.id.imageViewpirateisland:

                sharedpreferences = DirectMe.getInstance().sharedPrefs;
                editor = sharedpreferences.edit();
                editor.putString(Constants.ISLAND_ID,Constants.PIRATE_ISLAND_ID);
                editor.commit();

                break;
        }
    }
    void fragmentInitialise()
    {fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left,
                R.anim.exit_to_right);
        fragment = new UserDetailsFragment();
        fragmentTransaction.replace(R.id.fragment_container, fragment);

        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Do you want to Go Back ");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent=new Intent(getApplicationContext(),DashboardActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        finish();
                        startActivity(intent);


                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }
}






