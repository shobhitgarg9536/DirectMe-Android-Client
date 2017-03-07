package in.silive.directme.fragments;

import android.app.AlertDialog;
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

import in.silive.directme.activity.DashboardActivity;
import in.silive.directme.activity.ParkNowActivity;
import in.silive.directme.utils.NetworkUtils;
import in.silive.directme.Controller;
import in.silive.directme.application.DirectMe;
import in.silive.directme.database.DatabaseHandler;
import in.silive.directme.database.ShipModel;
import in.silive.directme.R;

/**
 * Created by Lenovo on 01-Dec-16.
 */

public class UserShipsFragment extends Fragment implements View.OnClickListener, java.util.Observer {
//    parkonmineparking static final String MyPREFERENCES = "MyPrefs";
    public static int value;
    static JSONObject data;
    TextView parkingUserName, parkingBoatName, parkingIsland;
    HashMap<String, String> queryValues;
    ArrayList<HashMap<String, String>> parking_detail;
    Button undock;
    NetworkUtils network;
    boolean network_available;
    TextView timee, coin, fill, cost_multiplier, experience_gain, boatname;
    int comm[] = new int[5];
    String c[] = new String[5];
    DashboardActivity dashboard;
    int prevsecond;
    int updated;
    int finalhour;
    int finalmin;
    int finalsec;
    int filspeed;
    String prevsec, update, finalhou, finalmi, finalse, fillspeed;
    TextView bamboo, coconut, banana, timber, gold_coin;
    ImageView ship;
    Controller controller = new Controller();
    DatabaseHandler db;
    SharedPreferences sharedpreferences;
    String cost, boatnam, ide, Xp;

    public static UserShipsFragment newInstance(JSONObject jsonObject) {
        UserShipsFragment userships = new UserShipsFragment();
        data = jsonObject;
        return userships;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_parking, container,
                false);
        this.undock = (Button) v.findViewById(R.id.undocker);
        dashboard = new DashboardActivity();
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

        sharedpreferences = DirectMe.getInstance().sharedPrefs;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int hour = calendar.get(Calendar.HOUR) * 60 * 60;
        int min = calendar.get(Calendar.MINUTE) * 60;
        int secon = calendar.get(Calendar.SECOND);
        int Second = hour + min + secon;
        if (sharedpreferences.contains(ParkNowActivity.times)) {
            prevsec = sharedpreferences.getString(ParkNowActivity.times, "");
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
            sharedpreferences.edit().remove(ParkNowActivity.times).commit();
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

        this.undock.setOnClickListener(UserShipsFragment.this);

        parkingDetail(0);

        controller.addObserver(UserShipsFragment.this);
        count();

        return v;

    }

    public void count() {
        int i;
        for (i = 0; i < 5; i++) {
            if (sharedpreferences.contains(DashboardActivity.co[i])) {
                comm[i] = Integer.parseInt(sharedpreferences.getString(DashboardActivity.co[i], ""));


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

                        sharedpreferences.edit().remove(ParkNowActivity.times).commit();
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

        network_available = NetworkUtils.isNetConnected();
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
                db.addShip(new ShipModel("1", boatnam, ide, "4", cost, "6", "7", "8", "9", "10"));
                ShipModel shi = db.getShip("1");
                Toast.makeText(getActivity(), "id" + shi.get_Cost_multiplier(), Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        // When error occured


        else {
            ShipModel shi = db.getShip("1");
            cost_multiplier.setText(shi.get_Cost_multiplier());

            boatname.setText(shi.get_name());
            alertDialog("Error", "Sorry, your device doesn't connect to internet!");

        }
    }

    @Override
    public void update(Observable observable, Object o) {
        controller = (Controller) observable;
        System.out.println(controller.getBananaCount());
        bamboo.setText(String.valueOf(controller.getBambooCount()));
        coconut.setText(String.valueOf(controller.getCoconutCount()));
        banana.setText(String.valueOf(controller.getBananaCount()));
        timber.setText(String.valueOf(controller.getTimberCount()));
        gold_coin.setText(String.valueOf(controller.getGoldCoinCount()));

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
