package in.silive.directme.fragments;

/**
 * Created by simran on 2/24/2017.
 */


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import in.silive.directme.R;
import in.silive.directme.activity.ParkNowActivity;
import in.silive.directme.application.DirectMe;
import in.silive.directme.utils.Constants;

import static in.silive.directme.fragments.DockyardFargment.json_data;

/**
 * Created by simran on 2/23/2017.
 */

public class ParknowUsershipselectFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    @Nullable
    public static int FRAME_W = 720;
    // frame height
    public static int FRAME_H;
    // number of frames
    public static int NB_FRAMES = 20;
    // nb of frames in x
    public static int COUNT_X = 5;
    // nb of frames in y
    public static int COUNT_Y = 4;
    public static String spritesheetimage;

    Button select;
    private Bitmap[] bitmaps;
    String boatName,status,boatImageUrl;
    ImageView boat_image;
    TextView boat_name;
    String id;
    ProgressBar pbShip;
    public ParknowUsershipselectFragment() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.parknow_ship_select, container,
                false);
        boat_image=(ImageView)v.findViewById(R.id.boatimage);
        boat_name=(TextView)v.findViewById(R.id.txtboatname);
        select = (Button) v.findViewById(R.id.select);
        pbShip = (ProgressBar) v.findViewById(R.id.progressBarParkNowShipSelect);
        select.setOnClickListener(this);
        try {
            json_data = new JSONObject(getArguments().getString("data", ""));
            boatName = json_data.getString("name");
            boatImageUrl = json_data.getString("ship_image");
            status = json_data.getString("ship_status");
            id=json_data.getString("ship_id");
            //  buyCost = json_data.getString("buy_cost");
            //  experienceGain = json_data.getString("experience_gain");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(status.equals("Idle"))
        { Picasso.with(getContext())
                .load(boatImageUrl)
                .into(boat_image, new Callback() {
                    @Override
                    public void onSuccess() {
                        pbShip.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {

                    }
                });
            select.setEnabled(true);
            boat_name.setText(boatName);

        }
        else
        {
            boat_image.setImageResource(R.drawable.ship1);
            select.setEnabled(false);
            boat_name.setText(boatName);
        }
        return v;

    }


    @Override
    public void onClick(View v) {
        SharedPreferences sharedpreferences = DirectMe.getInstance().sharedPrefs;
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Constants.SHIP_IMAGE_URL, boatImageUrl);
        editor.putString(Constants.SHIP_ID,id);
        editor.commit();
        Intent intent = new Intent(getActivity(), ParkNowActivity.class);
        startActivity(intent);

    }
}

