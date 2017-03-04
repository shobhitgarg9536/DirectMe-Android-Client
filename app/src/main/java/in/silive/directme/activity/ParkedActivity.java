package in.silive.directme.activity;

/**
 * Created by Lenovo on 09-Nov-16.
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import in.silive.directme.Controller;
import in.silive.directme.utils.NetworkUtils;
import in.silive.directme.R;
import in.silive.directme.application.DirectMe;
import in.silive.directme.database.DatabaseHandler;
import in.silive.directme.database.UserModel;
import in.silive.directme.listeners.AsyncResponse;
import in.silive.directme.network.FetchData;
import in.silive.directme.utils.API_URL_LIST;


public class ParkedActivity extends AppCompatActivity implements View.OnClickListener {

    //    public static final String MyPREFERENCE = "MyPrefs";
//    public static final String MyPREFERENCES = "UserName";
    ConstraintLayout rl;
    int i;
    Button undock;
    ConstraintLayout parkedShipDetial1, parkedShipDetial2, parkedShipDetial3, parkedShipDetial4, parkedShipDetial5;
    TextView boat_dock, time, user_name, boat_name, parking_allowness;
    NetworkUtils network;
    boolean network_available;
    DatabaseHandler db;
    int comm[] = new int[5];
    FetchData apiCalling;
    TextView gold_coin_textview, banana_textview, coconut_textview, bamboo_textview, timber_textview;
    Controller controller = new Controller();
    SharedPreferences sharedpreferences, sharedpreference;
    String token;
    String type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parked);
        parkedShipDetial1 = (ConstraintLayout) findViewById(R.id.port1);
        parkedShipDetial2 = (ConstraintLayout) findViewById(R.id.port2);
        parkedShipDetial3 = (ConstraintLayout) findViewById(R.id.port3);
        parkedShipDetial4 = (ConstraintLayout) findViewById(R.id.port4);
        parkedShipDetial5 = (ConstraintLayout) findViewById(R.id.port5);
        undock = (Button) findViewById(R.id.catchbutton);


        sharedpreference = DirectMe.getInstance().sharedPrefs;
        parkedDetail("0");
        undock.setOnClickListener(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        db = new DatabaseHandler(getApplicationContext());

        parkedShipDetial1.setOnClickListener(this);
        parkedShipDetial2.setOnClickListener(this);
        parkedShipDetial3.setOnClickListener(this);
        parkedShipDetial4.setOnClickListener(this);
        parkedShipDetial5.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.port1:
                parkedDetail("0");
                customdialog("null", "null", "null", "null", "null");


                break;
            case R.id.port2:
                parkedDetail("1");
                customdialog("null", "null", "null", "null", "null");


                break;
            case R.id.port3:
                parkedDetail("2");
                customdialog("null", "null", "null", "null", "null");


                break;
            case R.id.port4:
                parkedDetail("3");
                customdialog("null", "null", "null", "null", "null");

                break;
            case R.id.port5:
                parkedDetail("4");
                customdialog("null", "null", "null", "null", "null");

                break;
            case R.id.catchbutton:
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

                break;
        }

    }

    public void parkedDetail(final String parking_no) {


        network_available = NetworkUtils.isNetConnected();
        if (network_available) {
            apiCalling = new FetchData(new AsyncResponse() {
                @Override
                public void processStart() {

                }

                @Override
                public void processFinish(String output) {
                    try {
                        JSONArray user = new JSONArray(output);

                        JSONObject jsonObject = user.getJSONObject(Integer.parseInt(parking_no));
                        type = jsonObject.get("type").toString();

                        db.addPort(new UserModel(parking_no, "2:00", "Yes", "N-A", type, "2"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            apiCalling.setArgs(API_URL_LIST.PORTS_URL, token, "");
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

    public void customdialog(String Username, String Type, String BoatName, String Time, String Commodity_Filled) {

        final Dialog dialog = new Dialog(ParkedActivity.this);

//        dialog.setContentView(R.layout.parked);
        View view = View.inflate(this, R.layout.customdialog, null);
        dialog.setContentView(view);
        TextView BoatnameTextview = (TextView) dialog.findViewById(R.id.BOATNAMEVALUE);
        BoatnameTextview.setText(BoatName);
        TextView UsernameTextview = (TextView) dialog.findViewById(R.id.UserNameValue);
        UsernameTextview.setText(Username);
        TextView TypeTextView = (TextView) dialog.findViewById(R.id.TYPEVALUE);
        TypeTextView.setText(Type);
        TextView TimeTextView = (TextView) dialog.findViewById(R.id.TIMEVALUE);
        TimeTextView.setText(Time);
        TextView CommodityTextView = (TextView) dialog.findViewById(R.id.CommoditiesVALUE);
        CommodityTextView.setText(Commodity_Filled);
        dialog.show();
    }


}

