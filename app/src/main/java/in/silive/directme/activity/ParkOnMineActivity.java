package in.silive.directme.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.silive.directme.R;
import in.silive.directme.application.DirectMe;
import in.silive.directme.dialog.AlertDialog;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parkonmine);
        ButterKnife.bind(this);
        sharedpreference = DirectMe.getInstance().sharedPrefs;

        parkingport1.setOnClickListener(this);
        parkingport2.setOnClickListener(this);
        nonparkingport3.setOnClickListener(this);
        nonparkingport4.setOnClickListener(this);
        nonparkingport5.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.parkingport1:

                parkedDetail("0");


                if (jsonObject != null) {
                    fragmentInitialise();

                }


                break;
            case R.id.parkingport2:
                parkedDetail("1");
                if (jsonObject != null) {
                    fragmentInitialise();

                }

                break;
            case R.id.nonparkingport3:
                parkedDetail("2");
                if (jsonObject != null) {
                    fragmentInitialise();

                }

                break;
            case R.id.nonparkingport4:
                parkedDetail("3");

                if (jsonObject != null) {
                    fragmentInitialise();

                }


                break;
            case R.id.nonparkingport5:
                parkedDetail("4");

                if (jsonObject != null) {
                    fragmentInitialise();

                }


                break;

        }

    }

    public void parkedDetail(final String parking_no) {
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
                        JSONArray user = new JSONArray(output);
                        if (parking_no != null) {
                            jsonObject = user.getJSONObject(Integer.parseInt(parking_no));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, this);
            apiCalling.setArgs(API_URL_LIST.PORTS_URL, token, "");
            apiCalling.execute();

        }else{
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.alertDialog(this);
        }
    }

    void fragmentInitialise() {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right,
                R.anim.exit_to_left);
        args = new Bundle();
        args.putString("data", jsonObject.toString());
        fragment = new PortDetailsFragment();
        fragment.setArguments(args);
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

    }


}
