package in.silive.directme.activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import butterknife.BindView;
import butterknife.ButterKnife;
import in.silive.directme.R;
import in.silive.directme.application.DirectMe;
import in.silive.directme.fragments.UserDetailsFragment;
import in.silive.directme.utils.Constants;

public class ParkNowActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String times = "null";
    @BindView(R.id.wave1)
    RelativeLayout wave1;
    @BindView(R.id.wave2)
    RelativeLayout wave2;
    @BindView(R.id.wave3)
    RelativeLayout wave3;
    @BindView(R.id.wave4)
    RelativeLayout wave4;
    @BindView(R.id.wave5)
    RelativeLayout wave5;
    @BindView(R.id.wave6)
    RelativeLayout wave6;
    @BindView(R.id.wave7)
    RelativeLayout wave7;
    @BindView(R.id.wave8)
    RelativeLayout wave8;
    @BindView(R.id.wave9)
    RelativeLayout wave9;
    @BindView(R.id.wave10)
    RelativeLayout wave10;
    @BindView(R.id.imageViewisland1)
    ImageView  island1;
    @BindView(R.id.imageViewisland2)
    ImageView  island2;
    @BindView(R.id.imageViewisland3)
    ImageView  island3;
    @BindView(R.id.imageViewisland4)
    ImageView  island4;
    @BindView(R.id.imageViewpirateisland)
    ImageView  pirate_Island;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    UserDetailsFragment fragment;
    int offsetValue=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parknow);
        ButterKnife.bind(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        island1.setOnClickListener(this);
        island2.setOnClickListener(this);
        island3.setOnClickListener(this);
        island4.setOnClickListener(this);
        pirate_Island.setOnClickListener(this);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.waveanimationparknow);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener(){

            @Override
            public void onAnimationStart(Animation animation) {

            }
            @Override
            public void onAnimationEnd(Animation animation) {
                if(offsetValue>=10000) {
                    offsetValue=0;
                    offsetValue = offsetValue + 1000;
                    animation.setStartOffset(offsetValue);
                }
                else
                {
                    offsetValue = offsetValue + 1000;
                    animation.setStartOffset(offsetValue);
                }
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        wave1.startAnimation(animation);
        wave2.startAnimation(animation);
        wave3.startAnimation(animation);
        wave4.startAnimation(animation);
        wave5.startAnimation(animation);
        wave6.startAnimation(animation);
        wave7.startAnimation(animation);
        wave8.startAnimation(animation);
        wave9.startAnimation(animation);
        wave10.startAnimation(animation);
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
                editor.putString(Constants.ISLAND_ID, Constants.BANANA_ISLAND_ID);
                editor.commit();
                break;
            case R.id.imageViewisland2:
                fragmentInitialise();
                sharedpreferences = DirectMe.getInstance().sharedPrefs;
                editor = sharedpreferences.edit();
                editor.putString(Constants.ISLAND_ID, Constants.COCONUT_ISLAND_ID);
                editor.commit();
                break;
            case R.id.imageViewisland3:
                fragmentInitialise();
                sharedpreferences = DirectMe.getInstance().sharedPrefs;
                editor = sharedpreferences.edit();
                editor.putString(Constants.ISLAND_ID, Constants.BAMBOO_ISLAND_ID);
                editor.commit();
                break;
            case R.id.imageViewisland4:
                fragmentInitialise();
                sharedpreferences = DirectMe.getInstance().sharedPrefs;
                editor = sharedpreferences.edit();
                editor.putString(Constants.ISLAND_ID, Constants.TIMBER_ISLAND_ID);
                editor.commit();
                break;
            case R.id.imageViewpirateisland:
                sharedpreferences = DirectMe.getInstance().sharedPrefs;
                editor = sharedpreferences.edit();
                editor.putString(Constants.ISLAND_ID, Constants.PIRATE_ISLAND_ID);
                editor.commit();
                break;
        }
    }



    void fragmentInitialise() {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left,
                R.anim.exit_to_right);
        fragment = new UserDetailsFragment();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }
        else {
            final AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            final View dialogView= inflater.inflate(R.layout.alert_label_editor, null);
            builder1.setView(dialogView);
            builder1.setCancelable(false);
            final AlertDialog alertDialog = builder1.create();
            Button yes=(Button)dialogView.findViewById(R.id.yes);
            Button no=(Button)dialogView.findViewById(R.id.No);
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    finish();
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                }
            });
            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.cancel();

                }
            });
            builder1.setCancelable(true);

            alertDialog.show();
        }
    }

}




