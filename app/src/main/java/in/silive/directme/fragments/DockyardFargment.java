package in.silive.directme.fragments;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import in.silive.directme.R;
import in.silive.directme.application.DirectMe;
import in.silive.directme.listeners.FetchDataListener;
import in.silive.directme.network.FetchData;
import in.silive.directme.utils.API_URL_LIST;
import in.silive.directme.utils.Constants;
import in.silive.directme.utils.NetworkUtils;

import static android.content.Context.MODE_PRIVATE;

public class DockyardFargment extends Fragment implements View.OnClickListener

{
    static JSONObject json_data;
    static int slot;
    int dockstatus = 1;
    ImageView img, ivBoatImage,ivShipUpgrade;
    SharedPreferences prefrences;
    TextView tv_boat_name,tv_status,tv_buy_cost,tv_experience;
    String boatName,costMultiplier,buyCost,experienceGain,boatImageUrl,status;

    int flag=0;
    SharedPreferences sharedPreferences;
    
    FetchData apicalling;
    private boolean network_available;
    private String id;

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
            status = json_data.getString("status");
            id=json_data.getString("id");
          //  buyCost = json_data.getString("buy_cost");
          //  experienceGain = json_data.getString("experience_gain");

        } catch (JSONException e) {
            e.printStackTrace();
        }


        tv_boat_name = (TextView) rootView.findViewById(R.id.boatname);
        tv_status = (TextView) rootView.findViewById(R.id.textviewgaragestatus);
        //tv_experience = (TextView) rootView.findViewById(R.id.textviewgarageexperiencegain);
        //tv_buy_cost = (TextView) rootView.findViewById(R.id.textviewgaragebuycost);
        ivBoatImage = (ImageView) rootView.findViewById(R.id.imageViewGarageBoat);
        ivShipUpgrade = (ImageView) rootView.findViewById(R.id.imageview_garage_ship_upgrade);
        ivShipUpgrade.setOnClickListener(this);
        Picasso.with(getContext())
                .load(boatImageUrl)
                .into(ivBoatImage);
        tv_boat_name.setText(boatName);
        tv_status.setText(status);
       // tv_experience.setText(experienceGain);
       // tv_buy_cost.setText(buyCost);
        sharedPreferences = DirectMe.getInstance().sharedPrefs;

        if(status.equals("Busy"))
            ivShipUpgrade.setVisibility(View.GONE);

        return rootView;
    }
    void connect() {
        final String token = sharedPreferences.getString(Constants.AUTH_TOKEN, "");

        network_available = NetworkUtils.isNetConnected();
        if (network_available) {
            apicalling = new FetchData(new FetchDataListener() {
                @Override
                public void processStart() {

                }

                @Override
                public void processFinish(String output) {
                    Upgrade();

                }
            });
            String post_data = "";

            try {
                post_data= URLEncoder.encode("ship_id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
                
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            apicalling.setArgs(API_URL_LIST.UPGRADE_URL, token, post_data);
            apicalling.execute();

        }
    }

    @Override
    public void onClick(View v) {


        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage("Do you want to Upgrade ship");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        connect();




                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                        Toast.makeText(getActivity(),"Your ship is not upgraded",Toast.LENGTH_LONG).show();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }
    void Upgrade()
    { final String Response=sharedPreferences.getString(Constants.RESPONSE_CODE,"");

        if (Response.equals("200")) {
            Toast.makeText(getActivity(), "You upgraded ship", Toast.LENGTH_LONG).show();
            flag=0;
            ivShipUpgrade.setVisibility(View.GONE);
        } else {
            Toast.makeText(getActivity(), "Sorry your ship is not upgraded", Toast.LENGTH_LONG).show();
            flag=0;
        }

    }
}
