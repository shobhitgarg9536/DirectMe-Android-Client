package in.silive.directme.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import in.silive.directme.R;
import in.silive.directme.application.DirectMe;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by YESH AGNIHOTRI on 19-11-2016.
 */

public class DockyardFargment extends Fragment

{
    static JSONObject json_data;
    static int slot;
    int dockstatus = 1;
    ImageView img, ivBoatImage;
    SharedPreferences prefrences;
    TextView tv_boat_name,tv_cost_multiplier,tv_buy_cost,tv_experience;
    String boatName,costMultiplier,buyCost,experienceGain,boatImageUrl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        prefrences = DirectMe.getInstance().sharedPrefs;
        View rootView = inflater.inflate(R.layout.dockyard, container,
                false);

        try {
            json_data = new JSONObject(getArguments().getString("data", ""));
            boatName = json_data.getString("name");
            costMultiplier = json_data.getString("cost_multiplier");
            boatImageUrl = json_data.getString("image");

            buyCost = json_data.getString("buy_cost");
            experienceGain = json_data.getString("experience_gain");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        tv_boat_name = (TextView) rootView.findViewById(R.id.boatname);
        tv_cost_multiplier = (TextView) rootView.findViewById(R.id.textviewgaragecostmultiplier);
        tv_experience = (TextView) rootView.findViewById(R.id.textviewgarageexperiencegain);
        tv_buy_cost = (TextView) rootView.findViewById(R.id.textviewgaragebuycost);
        ivBoatImage = (ImageView) rootView.findViewById(R.id.imageViewGarageBoat);
        Picasso.with(getContext())
                .load(boatImageUrl)
                .into(ivBoatImage);
        tv_boat_name.setText(boatName);
        tv_cost_multiplier.setText(costMultiplier);
        tv_experience.setText(experienceGain);
        tv_buy_cost.setText(buyCost);


        return rootView;
    }
}
