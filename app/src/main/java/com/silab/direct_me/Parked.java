package com.silab.direct_me;

/**
 * Created by Lenovo on 09-Nov-16.
 */

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class Parked extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout rl;
    Button undock;
    Button parkedShipDetial1,parkedShipDetial2,parkedShipDetial3,parkedShipDetial4,parkedShipDetial5;
    TextView parking,boat_dock,time,user_name,boat_name,parking_allowness;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parked);
        parkedShipDetial1 = (Button)findViewById(R.id.buttonParkedShip1);
        parkedShipDetial2 = (Button)findViewById(R.id.buttonParkedShip2);
        parkedShipDetial3 = (Button)findViewById(R.id.buttonParkedShip3);
        parkedShipDetial4 = (Button)findViewById(R.id.buttonParkedShip4);
        parkedShipDetial5 = (Button)findViewById(R.id.buttonParkedShip5);
        undock = (Button) findViewById(R.id.remove);
        parking = (TextView) findViewById(R.id.ftd);
        boat_dock = (TextView) findViewById(R.id.std);
        time = (TextView) findViewById(R.id.fitd);
        parking_allowness = (TextView) findViewById(R.id.ftd);
        user_name = (TextView) findViewById(R.id.ttd);
        boat_name = (TextView) findViewById(R.id.fotd);
        rl=(RelativeLayout)findViewById(R.id.relat);
        rl.setVisibility(View.INVISIBLE);
        undock.setOnClickListener(this);

        parkedShipDetial1.setOnClickListener(this);
        parkedShipDetial2.setOnClickListener(this);
        parkedShipDetial3.setOnClickListener(this);
        parkedShipDetial4.setOnClickListener(this);
        parkedShipDetial5.setOnClickListener(this);
    }





    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.buttonParkedShip1:
                parkedDetail("1");
                parking_allowness.setText("YES");
                undock.setVisibility(View.GONE);
                rl.setVisibility(View.VISIBLE);
                break;
            case R.id.buttonParkedShip2:
                parkedDetail("2");
                parking_allowness.setText("YES");
                undock.setVisibility(View.GONE);
                rl.setVisibility(View.VISIBLE);
                break;
            case R.id.buttonParkedShip3:
                parkedDetail("3");
                parking_allowness.setText("YES");
                undock.setVisibility(View.GONE);
                rl.setVisibility(View.VISIBLE);
                break;
            case R.id.buttonParkedShip4:
                parkedDetail("4");
                parking_allowness.setText("NO");
                undock.setVisibility(View.VISIBLE);
                rl.setVisibility(View.VISIBLE);
                break;
            case R.id.buttonParkedShip5:
                parkedDetail("5");
                parking_allowness.setText("No");
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

    public void parkedDetail(final String parking_no){

        AsyncHttpClient client = new AsyncHttpClient();

        client.get("http://demo8496338.mockable.io/parked", new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonObject1 = jsonObject.getJSONObject(parking_no);


                                    boat_dock.setText(jsonObject1.get("dock_or_not").toString());
                            user_name.setText(jsonObject1.get("user_name").toString());
                            boat_name.setText(jsonObject1.get("boat_name").toString());
                            time.setText(jsonObject1.get("time").toString());




                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            // When error occured

            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                // TODO Auto-generated method stub
                // Hide ProgressBar
                if (statusCode == 404) {

                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                } else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet]",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}

