package in.silive.directme.fragments;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import in.silive.directme.R;
import in.silive.directme.application.DirectMe;
import in.silive.directme.listeners.FetchDataListener;
import in.silive.directme.network.FetchData;
import in.silive.directme.utils.API_URL_LIST;
import in.silive.directme.utils.Constants;
import in.silive.directme.utils.NetworkUtils;

public class DockyardFargment extends Fragment implements View.OnClickListener
{
    static JSONObject json_data;
    ImageView ivBoatImage,ivShipUpgrade, ivBuySlot;
    ProgressBar pbGarageBoat;
    SharedPreferences prefrences;
    TextView tv_boat_name,tv_status;
    String boatName,boatImageUrl,status, dock_status;
    int flag=0;
    SharedPreferences sharedPreferences;
    FetchData apicalling;
    private boolean network_available;
    private String ship_id;
    JSONObject upgradeShipJsonArray;
    android.support.constraint.ConstraintLayout clDockyard;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        prefrences = DirectMe.getInstance().sharedPrefs;
        View rootView = inflater.inflate(R.layout.dockyard, container,
                false);

        try {
            json_data = new JSONObject(getArguments().getString("data", ""));
            boatName = json_data.getString("name");
            boatImageUrl = json_data.getString("ship_image");
            status = json_data.getString("ship_status");
            ship_id =json_data.getString("ship_id");
            dock_status = json_data.getString("dock_status");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        clDockyard = (android.support.constraint.ConstraintLayout) rootView.findViewById(R.id.constraintLayoutGarageLocked);
        tv_boat_name = (TextView) rootView.findViewById(R.id.boatname);
        tv_status = (TextView) rootView.findViewById(R.id.textviewgaragestatus);
        ivBoatImage = (ImageView) rootView.findViewById(R.id.imageViewGarageBoat);
        ivShipUpgrade = (ImageView) rootView.findViewById(R.id.imageview_garage_ship_upgrade);
        ivBuySlot = (ImageView) rootView.findViewById(R.id.imageViewGarageLocked);
        pbGarageBoat = (ProgressBar) rootView.findViewById(R.id.progressBarGarageBoat);
        ivShipUpgrade.setOnClickListener(this);
        Picasso.with(getContext())
                .load(boatImageUrl)
                .into(ivBoatImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        pbGarageBoat.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {

                    }
                });
        tv_boat_name.setText(boatName);
        tv_status.setText(status);
        sharedPreferences = DirectMe.getInstance().sharedPrefs;

        if(status.equals("Busy"))
            ivShipUpgrade.setVisibility(View.GONE);

        if(dock_status.equals("locked")) {
            clDockyard.setVisibility(View.VISIBLE);
           Drawable drawable = clDockyard.getBackground();
            drawable.setAlpha(100);
            ivShipUpgrade.setVisibility(View.GONE);
        }
        if(dock_status.equals("buy")){
            clDockyard.setVisibility(View.VISIBLE);
            Drawable drawable = clDockyard.getBackground();
            drawable.setAlpha(100);
            ivShipUpgrade.setVisibility(View.GONE);
            ivBuySlot.setImageResource(R.drawable.buybtn);
            ivBuySlot.setOnClickListener(this);
        }
        return rootView;
    }

    void buySlot() {
        final String token = sharedPreferences.getString(Constants.AUTH_TOKEN, "");
        network_available = NetworkUtils.isNetConnected();
        if (network_available) {
            apicalling = new FetchData(new FetchDataListener() {
                @Override
                public void processStart() {

                }

                @Override
                public void processFinish(String output) {
                    buySlotStatus();
                }
            });
            apicalling.setArgs(API_URL_LIST.BUY_SLOT, token, "");
            apicalling.execute();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.imageview_garage_ship_upgrade) {
            final String token = sharedPreferences.getString(Constants.AUTH_TOKEN, "");
            network_available = NetworkUtils.isNetConnected();
            if (network_available) {
                apicalling = new FetchData(new FetchDataListener() {
                    @Override
                    public void processStart() {

                    }

                    @Override
                    public void processFinish(String output) {

                        try {
                            upgradeShipJsonArray = new JSONObject(output);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        startFragment();
                    }
                });
                int next_ship_id = Integer.valueOf(ship_id) + 1;
                apicalling.setArgs(API_URL_LIST.GARAGE_SHIP_DETAIL_URL + next_ship_id + "/", token, "");
                apicalling.execute();
            }
        }else if(v.getId() == R.id.imageViewGarageLocked){
            buySlot();
        }

    }

    private void startFragment() {
        Fragment garageUpgradeFragment = new GarageUpgradeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("json", upgradeShipJsonArray.toString());
        bundle.putString("ship_id" , String.valueOf(ship_id));
        garageUpgradeFragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(android.R.id.content, garageUpgradeFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    void buySlotStatus()
    {
        final String Response=sharedPreferences.getString(Constants.RESPONSE_CODE,"");

        if (Response.equals("200")) {
            Toast.makeText(getActivity(), "You Buy a new slot", Toast.LENGTH_LONG).show();
            flag=0;
            ivShipUpgrade.setVisibility(View.GONE);
        } else {
            Toast.makeText(getActivity(), "Sorry you havn't buy a slot", Toast.LENGTH_LONG).show();
            flag=0;
        }

    }
}
