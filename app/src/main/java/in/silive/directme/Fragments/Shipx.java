package in.silive.directme.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Observable;

import in.silive.directme.Activity.Dashboard;
import in.silive.directme.Activity.Parknow;
import in.silive.directme.CheckConnectivity;
import in.silive.directme.Controller;
import in.silive.directme.Database.DatabaseHandler;
import in.silive.directme.Database.Ships_DB_Objects;
import in.silive.directme.R;

/**
 * Created by Lenovo on 01-Dec-16.
 */

public class Shipx extends Fragment implements View.OnClickListener, java.util.Observer {
    public static final String MyPREFERENCES = "MyPrefs";
    public static int value;
    static JSONObject data;
    TextView parkingUserName, parkingBoatName, parkingIsland;
    HashMap<String, String> queryValues;
    ArrayList<HashMap<String, String>> parking_detail;
    Button undock;
    CheckConnectivity network;
    boolean network_available;
    TextView timee, coin, fill, cost_multiplier, experience_gain, boatname;
    int comm[] = new int[5];
    String c[] = new String[5];
    Dashboard dashboard;
    int Second, prevsecond, updated, finalhour, finalmin, finalsec, filspeed;
    String prevsec, update, finalhou, finalmi, finalse, fillspeed;
    TextView bamboo, coconut, banana, timber, gold_coin;
    ImageView ship;
    Controller controller = new Controller();
    DatabaseHandler db;
    SharedPreferences sharedpreferences;
    String cost, boatnam, ide, Xp;

    public static Shipx newInstance(JSONObject jsonObject) {
        Shipx shipx = new Shipx();
        data = jsonObject;
        return shipx;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_parking, container,
                false);
        this.undock = (Button) v.findViewById(R.id.undocker);
        dashboard = new Dashboard();
        parkingUserName = (TextView) v.findViewById(R.id.odetail);
        boatname = (TextView) v.findViewById(R.id.boatname);
        parkingBoatName = (TextView) v.findViewById(R.id.odet);
        parkingIsland = (TextView) v.findViewById(R.id.sdet);
        cost_multiplier = (TextView) v.findViewById(R.id.capacityval);
        experience_gain = (TextView) v.findViewById(R.id.Xpvalue);
        this.timee = (TextView) v.findViewById(R.id.timeal);
        this.coin = (TextView) v.findViewById(R.id.coinvalue);
        this.fill = (TextView) v.findViewById(R.id.fillvalue);
        gold_coin = (TextView) v.findViewById(R.id.gold_no);
        banana = (TextView) v.findViewById(R.id.banana_no);
        coconut = (TextView) v.findViewById(R.id.coconut_no);
        bamboo = (TextView) v.findViewById(R.id.bamboo_no);
        timber = (TextView) v.findViewById(R.id.wood_no);
        ship = (ImageView) v.findViewById(R.id.imageship);
        db = new DatabaseHandler(getActivity());

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int hour = calendar.get(Calendar.HOUR) * 60 * 60;
        int min = calendar.get(Calendar.MINUTE) * 60;
        int secon = calendar.get(Calendar.SECOND);
        int Second = hour + min + secon;
        if (sharedpreferences.contains(Parknow.times)) {
            prevsec = sharedpreferences.getString(Parknow.times, "");
            prevsecond = Integer.parseInt(prevsec);


            updated = Second - prevsecond;

        }
        if (updated < 7200) {
            finalhour = updated / 3600;
            finalmin = (updated % 3600) / 60;
            finalsec = (updated % 60);

            finalhou = Integer.toString(finalhour);
            finalmi = Integer.toString(finalmin);
            finalse = Integer.toString(finalsec);
            timee.setText(finalhou + ":" + finalmi + ":" + finalse);
            filspeed = updated / 72;
            fillspeed = Integer.toString(filspeed);
            fill.setText(fillspeed + "%");
        } else {
            sharedpreferences.edit().remove(Parknow.times).commit();
            timee.setText("Time over");
            String val = coin.getText().toString();
            value = Integer.parseInt(val);
            value = value + 30;
            fill.setText(100 + "%");
            String Earn = Integer.toString(value);
            coin.setText(Earn);
        }


        update = Integer.toString(updated);
        this.undock = (Button) v.findViewById(R.id.undocker);

        this.undock.setOnClickListener(Shipx.this);

        parkingDetail(0);

        controller.addObserver((java.util.Observer) Shipx.this);
        count();

        return v;

    }

    public void count() {
        int i;
        for (i = 0; i < 5; i++) {
            if (sharedpreferences.contains(Dashboard.co[i])) {
                comm[i] = Integer.parseInt(sharedpreferences.getString(Dashboard.co[i], ""));


            }
            c[i] = Integer.toString(comm[i]);

        }
        controller.setBambooCount(comm[3]);
        controller.setBananaCount(comm[2]);
        controller.setTimberCount(comm[1]);
        controller.setCoconutCount(comm[0]);
        controller.setGoldCoinCount(comm[4]);

    }

    @Override
    public void onClick(View view) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
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

    public void parkingDetail(final int id) {

        network_available = network.isNetConnected(getActivity());
        if (network_available) {


            try {
                cost = data.get("cost_multiplier").toString();

                boatnam = data.get("name").toString();
                ide = data.get("id").toString();
                Xp = data.get("experience_gain").toString();
                cost_multiplier.setText(cost);
                experience_gain.setText(Xp);
                boatname.setText(boatnam);
                Picasso.with(getActivity())
                        .load(data.get("image").toString())
                        .into(ship);
                db.addShip(new Ships_DB_Objects("1", boatnam, ide, "4", cost, "6", "7", "8", "9", "10"));
                Ships_DB_Objects shi = db.getShip("1");
                Toast.makeText(getActivity(), "id" + shi.get_Cost_multiplier(), Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        // When error occured


        else {
            Ships_DB_Objects shi = db.getShip("1");
            cost_multiplier.setText(shi.get_Cost_multiplier());

            boatname.setText(shi.get_name());
            alertDialog("Error", "Sorry, your device doesn't connect to internet!");

        }
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

    public void alertDialog(String title, String message) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
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
