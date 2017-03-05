package in.silive.directme.fragments;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import in.silive.directme.R;
import in.silive.directme.application.DirectMe;
import in.silive.directme.listeners.AsyncResponse;
import in.silive.directme.network.FetchData;
import in.silive.directme.utils.API_URL_LIST;
import in.silive.directme.utils.Constants;
import in.silive.directme.utils.NetworkUtils;


/**
 * Created by simran on 3/5/2017.
 */

public class ParkingDetailsFragment extends Fragment implements View.OnClickListener {
    TextView BoatnameTextview,UsernameTextview,TypeTextView,TimeTextView,CommodityTextView;
    JSONObject json_data;
    Button Dock;
    private String type,id;
    SharedPreferences sharedPreferences;
    FetchData apicalling;

    private boolean network_available;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.customdialog, container,
                false);
        BoatnameTextview = (TextView) v.findViewById(R.id.BOATNAMEVALUE);

        UsernameTextview = (TextView) v.findViewById(R.id.UserNameValue);

        TypeTextView = (TextView) v.findViewById(R.id.TYPEVALUE);

        TimeTextView = (TextView) v.findViewById(R.id.TIMEVALUE);
        Dock=(Button)v.findViewById(R.id.dock);
        Dock.setOnClickListener(this);
        sharedPreferences = DirectMe.getInstance().sharedPrefs;
        CommodityTextView = (TextView) v.findViewById(R.id.CommoditiesVALUE);
        try {
            json_data= new JSONObject(getArguments().getString("data", ""));
            type = json_data.get("type").toString();
            id=json_data.get("id").toString();
            JSONArray logs=json_data.getJSONArray("logs");
            if(logs.length()>0)
            {
                BoatnameTextview.setText("N-A");
                UsernameTextview.setText("N-A");
                TypeTextView.setText(type);
                TimeTextView.setText("N-A");
                CommodityTextView.setText("N-A");

            }
            else
            {
                BoatnameTextview.setText("N-A");
                UsernameTextview.setText("N-A");
                TypeTextView.setText(type);
                TimeTextView.setText("N-A");
                CommodityTextView.setText("N-A");

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return v;
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage("Do you want to Dock your ship");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        connect();
                        Toast.makeText(getActivity(),"Your ship is docked succesfully",Toast.LENGTH_LONG).show();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Toast.makeText(getActivity(),"Your ship is not docked",Toast.LENGTH_LONG).show();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }
    void connect() {
        final String token = sharedPreferences.getString(Constants.AUTH_TOKEN, "");
        final String ship_id=sharedPreferences.getString(Constants.SHIP_ID,"");
        network_available = NetworkUtils.isNetConnected();
        if (network_available) {
            apicalling = new FetchData(new AsyncResponse() {
                @Override
                public void processStart() {

                }

                @Override
                public void processFinish(String output) {

                }
            });
            String post_data = "";

            try {
                post_data= URLEncoder.encode("ship_id", "UTF-8") + "=" + URLEncoder.encode(ship_id, "UTF-8");
                post_data+="&"+URLEncoder.encode("port_id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            apicalling.setArgs(API_URL_LIST.Dock_Url, token, post_data);
            apicalling.execute();

        }
    }

}
