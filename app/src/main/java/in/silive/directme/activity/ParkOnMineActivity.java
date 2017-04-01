package in.silive.directme.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.silive.directme.R;
import in.silive.directme.application.DirectMe;
import in.silive.directme.fragments.PortDetailsFragment;
import in.silive.directme.listeners.FetchDataListener;
import in.silive.directme.network.FetchData;
import in.silive.directme.utils.API_URL_LIST;
import in.silive.directme.utils.Constants;
import in.silive.directme.utils.NetworkUtils;

/**
 * Created by simran on 3/6/2017.
 */

public class ParkOnMineActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.parkingport1)
    ImageView parkingport1;
    @BindView(R.id.parkingport2)
    ImageView parkingport2;
    @BindView(R.id.nonparkingport3)
    ImageView nonparkingport3;
    @BindView(R.id.nonparkingport4)
    ImageView nonparkingport4;
    @BindView(R.id.nonparkingport5)
    ImageView nonparkingport5;
    SharedPreferences sharedpreference;
    JSONObject jsonObject = null;
    FetchData apiCalling;
    boolean network_available;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    PortDetailsFragment fragment;
    Bundle args;
    Typeface type;
    final Context context=this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parkonmine);
        ButterKnife.bind(this);
        sharedpreference = DirectMe.getInstance().sharedPrefs;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//to make screen full screen
        parkingport1.setOnClickListener(this);
        parkingport2.setOnClickListener(this);
        nonparkingport3.setOnClickListener(this);
        nonparkingport4.setOnClickListener(this);
        nonparkingport5.setOnClickListener(this);
        type = Typeface.createFromAsset(getAssets(),"fonts/CarnevaleeFreakshow.ttf");
        api_calling();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.parkingport1:
                parkedDetail(0);
                break;
            case R.id.parkingport2:
                parkedDetail(1);
                break;
            case R.id.nonparkingport3:
                parkedDetail(2);
                break;
            case R.id.nonparkingport4:
                parkedDetail(3);
                break;
            case R.id.nonparkingport5:
                parkedDetail(4);
                break;
        }

    }

    JSONArray user,logs;
    public void api_calling() {
        final String token = sharedpreference.getString(Constants.AUTH_TOKEN, "");
        network_available = NetworkUtils.isNetConnected();
        if (network_available) {
            apiCalling = new FetchData(new FetchDataListener() {
                @Override
                public void processStart() {
                }

                @Override
                public void processFinish(String output) {
                    try {
                        user = new JSONArray(output);
                        for (int i = 0; i < 5; i++) {
                            jsonObject = user.getJSONObject(i);
                             logs = jsonObject.getJSONArray("logs");
                            if (logs.length() > 0) {

                                JSONObject logdetails = logs.getJSONObject(0);
                                String ship_img_url = logdetails.get("ship_image").toString();
                                showPort(i, ship_img_url);
                            } else {
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, this);
            apiCalling.setArgs(API_URL_LIST.PORTS_URL, token, "");
            apiCalling.execute();

        }else{
            in.silive.directme.dialog.AlertDialog alertDialog = new in.silive.directme.dialog.AlertDialog();
            alertDialog.alertDialog(this);
        }
    }

    JSONObject jsonObject1;
    public void parkedDetail(final int parking_no) {

        try {
            jsonObject1= user.getJSONObject(parking_no);

        } catch(JSONException e)
        {
            e.printStackTrace();
        }
        if(jsonObject1!=null&&logs.length()>0)

        {
            fragmentInitialise();
        }
        else
        {
            alertDialog("Port is empty");
        }

    }
    void alertDialog(String message)
    {
        final android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView= inflater.inflate(R.layout.alert_label_editor, null);
        builder1.setView(dialogView);
        builder1.setCancelable(false);
        final android.support.v7.app.AlertDialog alertDialog = builder1.create();
        TextView message_textview=(TextView)dialogView.findViewById(R.id.message);
        message_textview.setText(message);
        message_textview.setTextSize(30);
        message_textview.setTypeface(type);
        Button yes=(Button)dialogView.findViewById(R.id.yes);
        yes.setVisibility(View.GONE);
        Button no=(Button)dialogView.findViewById(R.id.No);

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();

            }
        });
        builder1.setCancelable(true);

        alertDialog.show();



    }


    void showPort(int position , String ship_url ){
        switch (position){
            case 0:
                Picasso.with(getApplicationContext()).load(ship_url).into(parkingport1);
                break;
            case 1:
                Picasso.with(getApplicationContext()).load(ship_url).into(parkingport2);
                break;
            case 2:
                Picasso.with(getApplicationContext()).load(ship_url).into(nonparkingport3);
                break;
            case 3:
                Picasso.with(getApplicationContext()).load(ship_url).into(nonparkingport4);
                break;
            case 5:
                Picasso.with(getApplicationContext()).load(ship_url).into(nonparkingport5);
                break;
        }
    }

    void fragmentInitialise() {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right,
                R.anim.exit_to_left);
        args = new Bundle();
        args.putString("data", jsonObject1.toString());
        fragment = new PortDetailsFragment();
        fragment.setArguments(args);
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }


}
