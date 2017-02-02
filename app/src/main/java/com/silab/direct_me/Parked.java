package com.silab.direct_me;

/**
 * Created by Lenovo on 09-Nov-16.
 */

import android.app.AlertDialog;
import android.content.Context;
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
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.silab.direct_me.UserLogin.Authorization_Token;


public class Parked extends AppCompatActivity implements View.OnClickListener {

    ConstraintLayout rl;
    int i;
    Button undock;
    ImageView parkedShipDetial1,parkedShipDetial2,parkedShipDetial3,parkedShipDetial4,parkedShipDetial5;
    TextView parking,boat_dock,time,user_name,boat_name,parking_allowness;
    CheckConnectivity network;
    boolean network_available;
    DatabaseHandler db;
    MyAsyncTask myAsyncTask;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "UserName";
    String token;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parked);
        parkedShipDetial1 = (ImageView) findViewById(R.id.imageViewParkedShip1);
        parkedShipDetial2 = (ImageView) findViewById(R.id.imageViewParkedShip2);
        parkedShipDetial3 = (ImageView) findViewById(R.id.imageViewParkedShip3);
        parkedShipDetial4 = (ImageView) findViewById(R.id.imageViewParkedShip4);
        parkedShipDetial5 = (ImageView) findViewById(R.id.imageViewParkedShip5);
        undock = (Button) findViewById(R.id.remove);
        parking = (TextView) findViewById(R.id.ftd);
        boat_dock = (TextView) findViewById(R.id.std);
        time = (TextView) findViewById(R.id.fitd);
        parking_allowness = (TextView) findViewById(R.id.ftd);
        user_name = (TextView) findViewById(R.id.ttd);
        boat_name = (TextView) findViewById(R.id.fotd);
        rl=(ConstraintLayout) findViewById(R.id.relat);
        rl.setVisibility(View.VISIBLE);
        sharedpreferences = getSharedPreferences(Authorization_Token, Context.MODE_PRIVATE);
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

        switch (view.getId()){

            case R.id.imageViewParkedShip1:
                parkedDetail("0");

                undock.setVisibility(View.GONE);
                rl.setVisibility(View.VISIBLE);
                break;
            case R.id.imageViewParkedShip2:
                parkedDetail("1");

                undock.setVisibility(View.GONE);
                rl.setVisibility(View.VISIBLE);
                break;
            case R.id.imageViewParkedShip3:
                parkedDetail("2");

                undock.setVisibility(View.GONE);
                rl.setVisibility(View.VISIBLE);
                break;
            case R.id.imageViewParkedShip4:
                parkedDetail("3");

                undock.setVisibility(View.VISIBLE);
                rl.setVisibility(View.VISIBLE);
                break;
            case R.id.imageViewParkedShip5:
                parkedDetail("4");

                undock.setVisibility(View.VISIBLE);
                rl.setVisibility(View.VISIBLE);
                break;
            case R.id.remove:
                android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(Parked.this);
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
    String type;

    public void parkedDetail(final String parking_no) {

        final String token = sharedpreferences.getString("Authorization_Token" , "");
        network_available = network.isNetConnected(getApplicationContext());
        if (network_available) {
            myAsyncTask=new MyAsyncTask(new AsyncResponse()
            {
                @Override
                public void processFinish(String output) {
                    try {
                        JSONArray user = new JSONArray(output);

                        JSONObject jsonObject = user.getJSONObject(Integer.parseInt(parking_no));
                        type = jsonObject.get("type").toString();
                        parking_allowness.setText(type);
                        db.addPort(new User(parking_no, "2:00", "Yes", "N-A", type, "2"));
                    }catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            },this);
            myAsyncTask.execute("copy",token);

        }
    }
    public  void alertDialog(String title , String message){

        AlertDialog.Builder alertDialog=new AlertDialog.Builder(getApplicationContext());
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

}

