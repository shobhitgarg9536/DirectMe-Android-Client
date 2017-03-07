package in.silive.directme.fragments;


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

public class ShowroomFragment extends Fragment {
    JSONObject json_data;
    int slot;
    ImageView ivBoatImage;
    TextView banana_req, gold_req, wood_req, bamboo_req, coconut_req,tv_boat_name,tv_cost_multiplier,tv_buy_cost,tv_experience;
    int banana_r = 0, gold_r = 0, bamboo_r = 0, wood_r = 0, coconut_r = 0;
    String boatName,costMultiplier,buyCost,experienceGain,boatImageUrl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        slot = getArguments().getInt("slot");
        try {
            json_data = new JSONObject(getArguments().getString("data", ""));
            boatName = json_data.getString("name");
            boatImageUrl = json_data.getString("image");
            costMultiplier = json_data.getString("cost_multiplier");
            buyCost = json_data.getString("buy_cost");
            experienceGain = json_data.getString("experience_gain");
        } catch (JSONException e) {
            json_data = null;
            e.printStackTrace();
        }
        View rootView = inflater.inflate(R.layout.showroom_fragment, container,
                false);

        banana_req = (TextView) rootView.findViewById(R.id.textView_showroom_banana_count);
        bamboo_req = (TextView) rootView.findViewById(R.id.textView_showroom_bamboo_count);
        gold_req = (TextView) rootView.findViewById(R.id.textView_showroom_gold_count);
        wood_req = (TextView) rootView.findViewById(R.id.textView_showroom_wood_count);
        coconut_req = (TextView) rootView.findViewById(R.id.textView_showroom_coconut_count);
        tv_boat_name = (TextView) rootView.findViewById(R.id.textviewshowroomboatname);
        tv_cost_multiplier = (TextView) rootView.findViewById(R.id.textviewshowroomcostmultiplier);
        tv_experience = (TextView) rootView.findViewById(R.id.textviewshowroomexperiencegain);
        tv_buy_cost = (TextView) rootView.findViewById(R.id.textviewshowroombuycost);
        tv_boat_name.setText(boatName);
        tv_cost_multiplier.setText(costMultiplier);
        tv_experience.setText(experienceGain);
        tv_buy_cost.setText(buyCost);
        ivBoatImage = (ImageView) rootView.findViewById(R.id.imageViewShowroomBoat);
        Picasso.with(DirectMe.getInstance())
                .load(boatImageUrl)
                .into(ivBoatImage);
        banana_req.setText(String.valueOf(banana_r));
        bamboo_req.setText(String.valueOf(bamboo_r));
        gold_req.setText(String.valueOf(gold_r));
        wood_req.setText(String.valueOf(wood_r));
        coconut_req.setText(String.valueOf(coconut_r));


        return rootView;

    }
}
