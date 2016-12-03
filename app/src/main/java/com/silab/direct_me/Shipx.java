package com.silab.direct_me;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Lenovo on 01-Dec-16.
 */

public class Shipx extends Fragment implements View.OnClickListener, java.util.Observer {
    TextView parkingUserName,parkingBoatName,parkingIsland;
    HashMap<String, String> queryValues;
    ArrayList<HashMap<String, String>> parking_detail;
    Button undock;
    TextView timee,coin,fill;
    int Second,prevsecond,updated,finalhour,finalmin,finalsec,filspeed;
    String prevsec,update,finalhou,finalmi,finalse,fillspeed;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    public static int value;
    TextView bamboo, coconut, banana, timber, gold_coin;
    Controller controller = new Controller();

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_parking, container,
                false);
        this.undock=(Button)v.findViewById(R.id.undocker);

        parkingUserName = (TextView) v.findViewById(R.id.odetail);
        parkingBoatName = (TextView) v.findViewById(R.id.odet);
        parkingIsland = (TextView) v.findViewById(R.id.sdet);
        this.timee=(TextView)v.findViewById(R.id.timeal);
        this.coin=(TextView)v.findViewById(R.id.coinvalue);
        this.fill=(TextView)v.findViewById(R.id.fillvalue);
        gold_coin=(TextView)v.findViewById(R.id.gold_no);
        banana=(TextView)v.findViewById(R.id.banana_no);
        coconut=(TextView)v.findViewById(R.id.coconut_no);
        bamboo=(TextView)v.findViewById(R.id.bamboo_no);
        timber=(TextView)v.findViewById(R.id.wood_no);
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int hour = calendar.get(Calendar.HOUR)*60*60;
        int min= calendar.get(Calendar.MINUTE)*60;
        int secon=calendar.get(Calendar.SECOND);
        int Second=hour+min+secon;
        if (sharedpreferences.contains(Parknow.times)) {
            prevsec=sharedpreferences.getString(Parknow.times, "");
            prevsecond=Integer.parseInt(prevsec);


            updated=Second-prevsecond;

        }
        if(updated<7200) {
            finalhour = updated / 3600;
            finalmin = (updated % 3600) / 60;
            finalsec = (updated % 60);

            finalhou = Integer.toString(finalhour);
            finalmi = Integer.toString(finalmin);
            finalse = Integer.toString(finalsec);
            timee.setText(finalhou+":"+finalmi+":"+finalse);
            filspeed=updated/72;
            fillspeed=Integer.toString(filspeed);
            fill.setText(fillspeed+"%");
        }
        else
        {
            sharedpreferences.edit().remove(Parknow.times).commit();
            timee.setText("Time over");
            String val= coin.getText().toString();
            value=Integer.parseInt(val);
            value=value+30;
            fill.setText(100+"%");
            String Earn=Integer.toString(value);
            coin.setText(Earn);

        }



        update=Integer.toString(updated);
        this.undock=(Button)v.findViewById(R.id.undocker);

        this.undock.setOnClickListener(Shipx.this);
        parkingDetail();

        controller.addObserver((java.util.Observer) Shipx.this);
        controller.setBambooCount(100);
        controller.setBananaCount(50);
        controller.setTimberCount(40);
        controller.setCoconutCount(150);
        controller.setGoldCoinCount(10);
        return v;

    }

    @Override
    public void onClick(View view) {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Undock");
        alertDialog.setMessage("Do you want to undock");
        alertDialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        timee.setText("Not parked");

                        sharedpreferences.edit().remove(Parknow.times).commit();
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
    public void parkingDetail(){

        AsyncHttpClient client = new AsyncHttpClient();

        client.get("http://demo8496338.mockable.io/parking", new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(String response) {
                try {

                    // Extract JSON array from the response
                    JSONArray arr = new JSONArray(response);
                    // If no of array elements is not zero
                    if(arr.length() != 0){
                        parking_detail = new ArrayList<HashMap<String, String>>();
                        // Loop through each array element, get JSON object which has userid and username
                        for (int i = 0; i < arr.length(); i++) {
                            // Get JSON object
                            JSONObject obj = (JSONObject) arr.get(i);
                            System.out.println(obj.get("boat_name"));
                            System.out.println(obj.get("island"));
                            System.out.println(obj.get("user_name"));

                            queryValues = new HashMap<String, String>();
                            // Add values extracted from Object
                            queryValues.put("boat_name", obj.get("boat_name").toString());
                            queryValues.put("island", obj.get("island").toString());
                            queryValues.put("user_name", obj.get("user_name").toString());

                            parkingBoatName.setText(obj.get("boat_name").toString());
                            parkingIsland.setText(obj.get("island").toString());
                            parkingUserName.setText(obj.get("user_name").toString());

                            parking_detail.add(queryValues);
                        }

                    }
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
    @Override
    public void update(Observable observable, Object o) {
        controller = (Controller) observable;
        System.out.println(controller.getBananaCount());
        bamboo.setText(Integer.toString(controller.getBambooCount()));
        coconut.setText(Integer.toString(controller.getCoconutCount()));
        banana.setText(Integer.toString(controller.getBananaCount()));
        timber.setText(Integer.toString(controller.getTimberCount()));
        gold_coin.setText(Integer.toString(controller.getGoldCoinCount()));

    }
}
