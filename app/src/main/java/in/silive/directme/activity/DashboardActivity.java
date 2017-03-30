package in.silive.directme.activity;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Observable;
import butterknife.BindView;
import butterknife.ButterKnife;
import in.silive.directme.Controller;
import in.silive.directme.dialog.AlertDialog;
import in.silive.directme.fragments.LeaderBoardFragment;
import in.silive.directme.fragments.UserProfileFragment;
import in.silive.directme.utils.Keys;
import in.silive.directme.utils.NetworkUtils;
import in.silive.directme.R;
import in.silive.directme.application.DirectMe;
import in.silive.directme.listeners.FetchDataListener;
import in.silive.directme.network.FetchData;
import in.silive.directme.utils.API_URL_LIST;
import in.silive.directme.utils.Constants;
import in.silive.directme.utils.ToasterUtils;


public class DashboardActivity extends AppCompatActivity implements View.OnClickListener, java.util.Observer,Animation.AnimationListener {
    public static final String[] co = new String[7];
    public int[] commod = new int[7];
    String token;
    int i;
    SharedPreferences sharedpreferences;
    Controller controller = new Controller();
    boolean network_available;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    UserProfileFragment fragment;
    BroadcastReceiver mRegistrationBroadcastReceiver;
    FetchData apicalling;
    @BindView(R.id.tvBamboo)
    TextView bamboo;
    @BindView(R.id.tvBanana)
    TextView banana;
    @BindView(R.id.tvCoconut)
    TextView coconut;
    @BindView(R.id.tvTimber)
    TextView timber;
    @BindView(R.id.tvGold)
    TextView gold_coin;
    @BindView(R.id.tvExperience)
    TextView experiencetxtview;
    @BindView(R.id.imageviewpark)
    ImageView park;
    @BindView(R.id.imageviewparked)
    ImageView parked;
    @BindView(R.id.imageviewparking)
    ImageView parking;
    @BindView(R.id.imageviewgarage)
    ImageView garage;
    @BindView(R.id.imageviewshowroom)
    ImageView showroom;
    @BindView(R.id.leaderboard)
    ImageView iv_leader_board;
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
    @BindView(R.id.userprofile)
    ImageView avatar;
     Bundle args;
    JSONObject jsonObject;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        ButterKnife.bind(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//to make screen full screen
        sharedpreferences = DirectMe.getInstance().sharedPrefs;
        park.setOnClickListener(this);
        parked.setOnClickListener(this);
        parking.setOnClickListener(this);
        garage.setOnClickListener(this);
        showroom.setOnClickListener(this);
        iv_leader_board.setOnClickListener(this);
        avatar.setOnClickListener(this);
        avatar.setEnabled(false);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.waveanimation);
        animation.setFillAfter(true);
        animation.setAnimationListener(this);
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
        controller.addObserver(DashboardActivity.this);
        token = sharedpreferences.getString(Constants.AUTH_TOKEN, "");
        count();
        if (NetworkUtils.isNetConnected()) {
            String firebase_id_send_to_server_or_not = sharedpreferences.getString(Constants.FIREBASE_ID_SENT, "");
            if (firebase_id_send_to_server_or_not.equals("0")) {
                String Firebase_token = sharedpreferences.getString("regId", "");
                FetchData fetchData = new FetchData(new FetchDataListener() {
                    @Override
                    public void processStart() {
                    }
                    @Override
                    public void processFinish(String output) {
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(Constants.FIREBASE_ID_SENT, "1");//1 means firebase id is registered
                        editor.commit();
                    }
                }, this);
                String post_data = "";
                try {
                    post_data = URLEncoder.encode(Keys.fcm_token, "UTF-8") + "=" + URLEncoder.encode(Firebase_token, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                fetchData.setArgs(API_URL_LIST.FIREBASE_TOKEN_UPDATE, token, post_data);
                fetchData.execute();
            }
        }else {
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.alertDialog(this);
        }
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // checking for type intent filter
                if (intent.getAction().equals(Constants.REGISTRATION_COMPLETE)) {
                    // fcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Constants.TOPIC_GLOBAL);
                } else if (intent.getAction().equals(Constants.PUSH_NOTIFICATION)) {
                    // new push notification is received
                    String message = intent.getStringExtra("message");
                    ToasterUtils.toaster("Push notification: " + message);
                }
            }
        };
    }
    public void count() {
        network_available = NetworkUtils.isNetConnected();
        if (network_available) {
            apicalling = new FetchData(new FetchDataListener() {
                @Override
                public void processStart() {

                }
                @Override
                public void processFinish(String output) {
                    try {
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        jsonObject = new JSONObject(output);
                        String username = jsonObject.getString(Keys.username);
                        String user_id = jsonObject.getString(Keys.user_id);
                        String island_id = jsonObject.getString(Keys.island_id);
                        String island_name = jsonObject.getString(Keys.island_name);
                        String first_name=jsonObject.getString(Keys.firstname);
                        String experience=jsonObject.getString(Keys.experience);
                        String gravatar=jsonObject.getString(Keys.gravatar);
                        experiencetxtview.setText(experience);
                        editor.putString(Keys.username, username);
                        editor.putString(Keys.user_id, user_id);
                        editor.putString(Keys.island_id, island_id);
                        editor.putString(Keys.island_name, island_name);
                        editor.putString(Keys.firstname,first_name);
                        editor.putString(Keys.experience,experience);
                        editor.putString(Keys.gravatar,gravatar);
                        Picasso.with(getApplicationContext()).load(gravatar).into(avatar);
                        JSONArray things = jsonObject.getJSONArray(Keys.inventory);
                            for(i=0;i<5;i++) {
                                JSONObject jsonObject1 = things.getJSONObject(i);
                                commod[i] = Integer.parseInt(jsonObject1.getString(Keys.count));

                                //putting values
                                editor.putString(Keys.co[i], Integer.toString(commod[i]));
                                editor.commit();
                                avatar.setEnabled(true);
                            }
                            controller.setBambooCount(commod[1]);
                            controller.setBananaCount(commod[2]);
                            controller.setTimberCount(commod[3]);
                            controller.setCoconutCount(commod[4]);
                            controller.setGoldCoinCount(commod[0]);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, this);
            apicalling.setArgs(API_URL_LIST.COMODITY_URL, token, "");
            apicalling.execute();
        } else {
            for (i = 0; i < 5; i++) {
                    commod[i] = Integer.parseInt(sharedpreferences.getString(Keys.co[i], ""));
            }
            controller.setBambooCount(commod[1]);
            controller.setBananaCount(commod[2]);
            controller.setTimberCount(commod[3]);
            controller.setCoconutCount(commod[4]);
            controller.setGoldCoinCount(commod[0]);
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
                Intent intent4 = new Intent(this, ParkOnMineActivity.class);
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
            case R.id.userprofile:
                    fragmentInitialise();
                break;
            case R.id.leaderboard:
                leaderBoardFragment();
                break;

        }
    }
    private void leaderBoardFragment() {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left,
                R.anim.exit_to_right);
        Fragment fragment = new LeaderBoardFragment();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    @Override
    //updating values through observer
    public void update(Observable observable, Object o) {
        controller = (Controller) observable;
        bamboo.setText(String.valueOf(controller.getBambooCount()));
        coconut.setText(String.valueOf(controller.getCoconutCount()));
        banana.setText(String.valueOf(controller.getBananaCount()));
        timber.setText(String.valueOf(controller.getTimberCount()));
        gold_coin.setText(String.valueOf(controller.getGoldCoinCount()));
    }
    @Override
    protected void onResume() {
        super.onResume();
        count();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }
        else
        {super.onBackPressed();
            finish();

        }
    }
    @Override
    public void onAnimationStart(Animation animation) {

    }
    @Override
    public void onAnimationEnd(Animation animation) {

    }
    @Override
    public void onAnimationRepeat(Animation animation) {

    }
    void fragmentInitialise()
    {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left,
                R.anim.exit_to_right);
        fragment = new UserProfileFragment();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
