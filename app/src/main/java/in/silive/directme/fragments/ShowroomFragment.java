package in.silive.directme.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import in.silive.directme.R;
import in.silive.directme.application.DirectMe;
import in.silive.directme.listeners.FetchDataListener;
import in.silive.directme.network.FetchData;
import in.silive.directme.utils.Constants;
import in.silive.directme.utils.NetworkUtils;

public class ShowroomFragment extends Fragment {
    JSONObject json_data;
    int slot;
    ImageView ivBoatImage;
    ProgressBar pbShip;
    TextView tv_banana, tv_gold, tv_wood, tv_bamboo, tv_coconut,tv_boat_name,tv_cost_multiplier,tv_buy_cost,tv_experience;
    int banana_req = 0, bamboo_req = 0, timber_req = 0, coconut_req = 0, item_id, count;
    String boatName,costMultiplier,buyCost,experienceGain,boatImageUrl;
    ImageView iv_buy_ship;
    SharedPreferences sharedPreferences;
    android.support.constraint.ConstraintLayout clfragment1, clfragment2;

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
            JSONArray jsonArray = json_data.getJSONArray("items_required");
            for(int i= 0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                item_id = jsonObject.getInt("item_id");
                count = jsonObject.getInt("count");
                setInventoriesCount(item_id, count);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        View rootView = inflater.inflate(R.layout.showroom_fragment, container,
                false);

        tv_banana = (TextView) rootView.findViewById(R.id.textView_showroom_banana_count);
        tv_bamboo = (TextView) rootView.findViewById(R.id.textView_showroom_bamboo_count);
        tv_gold = (TextView) rootView.findViewById(R.id.textView_showroom_gold_count);
        tv_wood = (TextView) rootView.findViewById(R.id.textView_showroom_wood_count);
        tv_coconut = (TextView) rootView.findViewById(R.id.textView_showroom_coconut_count);
        tv_boat_name = (TextView) rootView.findViewById(R.id.textviewshowroomboatname);
        tv_cost_multiplier = (TextView) rootView.findViewById(R.id.textviewshowroomcostmultiplier);
        tv_experience = (TextView) rootView.findViewById(R.id.textviewshowroomexperiencegain);
        tv_buy_cost = (TextView) rootView.findViewById(R.id.textviewshowroombuycost);
        pbShip = (ProgressBar) rootView.findViewById(R.id.progressBarShowroomShip);
        iv_buy_ship = (ImageView) rootView.findViewById(R.id.imageViewShowroomBuy);
        clfragment1 = (android.support.constraint.ConstraintLayout) rootView.findViewById(R.id.constraintLayoutfragment1);
        clfragment2 = (android.support.constraint.ConstraintLayout) rootView.findViewById(R.id.constraintLayoutfragment2);

        tv_boat_name.setText(boatName);
        tv_cost_multiplier.setText(costMultiplier);
        tv_experience.setText(experienceGain);
        tv_buy_cost.setText(buyCost);
        tv_gold.setText(buyCost);
        ivBoatImage = (ImageView) rootView.findViewById(R.id.imageViewShowroomBoat);
        Picasso.with(DirectMe.getInstance())
                .load(boatImageUrl)
                .into(ivBoatImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        pbShip.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {

                    }
                });
        sharedPreferences = DirectMe.getInstance().sharedPrefs;
        tv_banana.setText(String.valueOf(banana_req));
        tv_bamboo.setText(String.valueOf(bamboo_req));
        tv_wood.setText(String.valueOf(timber_req));
        tv_coconut.setText(String.valueOf(coconut_req));

        iv_buy_ship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFragment();
            }
        });
        return rootView;

    }

    private void setInventoriesCount(int item_id, int count) {
        if(item_id == 1)
            coconut_req = count;
        else if(item_id == 2)
            timber_req = count;
        else if(item_id == 3)
            banana_req = count;
        else if(item_id == 4)
            bamboo_req = count;
        else{}
    }

    private void startFragment() {
        Fragment dockyardBuyShipFragment = new Fragment1();
        Fragment dockyardBuyShipFragment2 = new Fragment2();
        Bundle bundle = new Bundle();
        bundle.putString("json", json_data.toString());
        dockyardBuyShipFragment.setArguments(bundle);
        dockyardBuyShipFragment2.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.constraintLayoutfragment1, dockyardBuyShipFragment);
        transaction.add(R.id.constraintLayoutfragment2, dockyardBuyShipFragment2);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}