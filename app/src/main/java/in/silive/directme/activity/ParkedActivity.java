package in.silive.directme.activity;

/**
 * Created by Lenovo on 09-Nov-16.
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import in.silive.directme.Controller;
import in.silive.directme.fragments.ParkingDetailsFragment;
import in.silive.directme.utils.Constants;
import in.silive.directme.utils.NetworkUtils;
import in.silive.directme.R;
import in.silive.directme.application.DirectMe;
import in.silive.directme.database.DatabaseHandler;
import in.silive.directme.database.UserModel;
import in.silive.directme.listeners.FetchDataListener;
import in.silive.directme.network.FetchData;
import in.silive.directme.utils.API_URL_LIST;


public class ParkedActivity extends AppCompatActivity implements View.OnClickListener {

    //    public static final String MyPREFERENCE = "MyPrefs";
//    public static final String MyPREFERENCES = "UserName";
    ConstraintLayout rl;
    int i;
    Button undock;
    ConstraintLayout parkedShipDetial1, parkedShipDetial2, parkedShipDetial3, parkedShipDetial4, parkedShipDetial5;
    boolean network_available;
    DatabaseHandler db;
    int comm[] = new int[5];
    FetchData apiCalling;
    Controller controller = new Controller();
    SharedPreferences sharedpreference;
    String token;
    String type;
    String id;
    TextView port1status,port2status,port3status,port4status,port5status;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ParkingDetailsFragment fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parked);
        parkedShipDetial1 = (ConstraintLayout) findViewById(R.id.port1);
        parkedShipDetial2 = (ConstraintLayout) findViewById(R.id.port2);
        parkedShipDetial3 = (ConstraintLayout) findViewById(R.id.port3);
        parkedShipDetial4 = (ConstraintLayout) findViewById(R.id.port4);
        parkedShipDetial5 = (ConstraintLayout) findViewById(R.id.port5);

        port1status=(TextView)findViewById(R.id.NamePort1);
        port2status=(TextView)findViewById(R.id.NamePort2);
        port3status=(TextView)findViewById(R.id.NamePort3);
        port4status=(TextView)findViewById(R.id.NamePort4);
        port5status=(TextView)findViewById(R.id.NamePort5);
        sharedpreference = DirectMe.getInstance().sharedPrefs;
        parkedDetail("0");

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        db = new DatabaseHandler(getApplicationContext());

        parkedShipDetial1.setOnClickListener(this);
        parkedShipDetial2.setOnClickListener(this);
        parkedShipDetial3.setOnClickListener(this);
        parkedShipDetial4.setOnClickListener(this);
        parkedShipDetial5.setOnClickListener(this);


    }
    Bundle args;

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.port1:

                parkedDetail("0");
                if(status!=null) {
                    port1status.setText(status);
                }

                fragmentInitialise();



                break;
            case R.id.port2:
                parkedDetail("1");
                if(status!=null) {
                    port2status.setText(status);
                }
                fragmentInitialise();



                break;
            case R.id.port3:
                parkedDetail("2");
                if(status!=null) {
                    port3status.setText(status);
                }
               fragmentInitialise();



                break;
            case R.id.port4:
                parkedDetail("3");
                if(status!=null) {
                    port4status.setText(status);
                }
               fragmentInitialise();


                break;
            case R.id.port5:
                parkedDetail("4");
                if(status!=null) {
                    port5status.setText(status);
                }
                fragmentInitialise();


                break;
           /*case R.id.catchbutton:
                android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(ParkedActivity.this);
                alertDialog.setTitle("UNDOCK");
                alertDialog.setMessage("Are you sure you want to this boat from your non parking area");
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDialog.create();
                alertDialog.show();

                break;*/
        }

    }
    String status;
    String user_id;
    JSONObject jsonObject;
    public void parkedDetail(final String parking_no) {
        final String token = sharedpreference.getString(Constants.AUTH_TOKEN, "");
        user_id=sharedpreference.getString(Constants.USER_ID,"");
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

                         jsonObject = user.getJSONObject(Integer.parseInt(parking_no));
                        type = jsonObject.get("type").toString();
                        id=jsonObject.get("id").toString();
                        JSONArray logs=jsonObject.getJSONArray("logs");
                        if(logs.length()>0)
                        { status="Busy";

                        }
                        else
                        {
                            status="Empty";
                        }

                        db.addPort(new UserModel(parking_no, "2:00", "Yes", "N-A", type, "2"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            apiCalling.setArgs(API_URL_LIST.PORTS_URL+user_id+"/", token, "");
            apiCalling.execute();


        }
    }

    public void alertDialog(String title, String message) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getApplicationContext());
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


    void fragmentInitialise()
    {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left,
                R.anim.exit_to_right);
        args = new Bundle();
        args.putString("data", jsonObject.toString());



        fragment = new ParkingDetailsFragment();
        fragment.setArguments(args);
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }


}

