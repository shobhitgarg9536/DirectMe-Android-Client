package in.silive.directme.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import in.silive.directme.R;

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
        View rootView = inflater.inflate(R.layout.raft_fragment, container,
                false);

        banana_req = (TextView) rootView.findViewById(R.id.getbanana);
        bamboo_req = (TextView) rootView.findViewById(R.id.getbamboo);
        gold_req = (TextView) rootView.findViewById(R.id.getgold);
        wood_req = (TextView) rootView.findViewById(R.id.getwood);
        coconut_req = (TextView) rootView.findViewById(R.id.getcoconut);
        tv_boat_name = (TextView) rootView.findViewById(R.id.textviewshowroomboatname);
        tv_cost_multiplier = (TextView) rootView.findViewById(R.id.textviewshowroomcostmultiplier);
        tv_experience = (TextView) rootView.findViewById(R.id.textviewshowroomexperiencegain);
        tv_buy_cost = (TextView) rootView.findViewById(R.id.textviewshowroombuycost);
        tv_boat_name.setText(boatName);
        tv_cost_multiplier.setText(costMultiplier);
        tv_experience.setText(experienceGain);
        tv_buy_cost.setText(buyCost);
        ivBoatImage = (ImageView) rootView.findViewById(R.id.imageViewShowroomBoat);
        Picasso.with(getContext())
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
