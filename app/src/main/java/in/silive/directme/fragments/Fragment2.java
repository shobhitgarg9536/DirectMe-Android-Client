package in.silive.directme.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import in.silive.directme.R;
import in.silive.directme.activity.DashboardActivity;
import in.silive.directme.application.DirectMe;
import in.silive.directme.network.FetchData;

/**
 * Created by Shobhit-pc on 3/14/2017.
 */

public class Fragment2 extends Fragment {

    String buyCost;
    int item_id, count, coconut_count =0, timber_count =0, banana_count =0, bamboo_count =0;
    int user_banana_count, user_coconut_count, user_bamboo_counnt, user_timber_count;
    SharedPreferences sharedPreferences;
    public int[] commod = new int[5];
    TextView tvshipName, tvBananaCount, tvCoconutCount, tvBambooCount, tvTimberCount;
    SeekBar sbBanana, sbCoconut, sbBamboo, sbTimber;
    ImageView iv_buy_ship;
    int upgradeFlag =0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        try {
           JSONObject json_data = new JSONObject(getArguments().getString("json", ""));
            buyCost = json_data.getString("buy_cost");
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

        View view = inflater.inflate(R.layout.fragment2, container, false);
        sbBanana = (SeekBar) view.findViewById(R.id.seekBarShowroomBanana);
        sbCoconut = (SeekBar) view.findViewById(R.id.seekBarShowroomCoconut);
        sbBamboo = (SeekBar) view.findViewById(R.id.seekBarShowroomBamboo);
        sbTimber = (SeekBar) view.findViewById(R.id.seekBarShowroomWood);
        iv_buy_ship = (ImageView) view.findViewById(R.id.imageViewShowroomBuyShip);
        tvBananaCount = (TextView) view.findViewById(R.id.textViewShowroomBuyBanana);
        tvCoconutCount = (TextView) view.findViewById(R.id.textViewShowroomBuyCoconut);
        tvBambooCount = (TextView) view.findViewById(R.id.textViewShowroomBuyBamboo);
        tvTimberCount = (TextView) view.findViewById(R.id.textViewShowroomBuyTimber);

        sharedPreferences = DirectMe.getInstance().sharedPrefs;
        //getting user inventories from shared preferences
        for (int i = 0; i < 5; i++) {
            if (sharedPreferences.contains(DashboardActivity.co[i])) {
                commod[i] = Integer.parseInt(sharedPreferences.getString(DashboardActivity.co[i], ""));
            }
        }

        user_banana_count =commod[2];
        user_timber_count = commod[0];
        user_bamboo_counnt = commod[3];
        user_coconut_count = commod[1];

        sbBanana.setMax(banana_count);
        sbBamboo.setMax(bamboo_count);
        sbTimber.setMax(timber_count);
        sbCoconut.setMax(coconut_count);

        int banana_req, coconut_req, bamboo_req, timber_req;
        coconut_req = coconut_count - user_coconut_count;
        timber_req = timber_count - user_timber_count;
        banana_req = banana_count - user_banana_count;
        bamboo_req = bamboo_count - user_bamboo_counnt;

        if(coconut_req <= 0) {
            tvCoconutCount.setText("0");
            sbCoconut.setProgress(coconut_count);
        }
        else {
            upgradeFlag = 1;
            tvCoconutCount.setText("+"+coconut_req);
            sbCoconut.setProgress(user_coconut_count);
        }
        if(timber_req <= 0) {
            tvTimberCount.setText("0");
            sbTimber.setProgress(timber_count);
        }
        else {
            upgradeFlag = 1;
            tvTimberCount.setText("+"+timber_req);
            sbTimber.setProgress(user_timber_count);
        }
        if(banana_req <= 0) {
            tvBambooCount.setText("0");
            sbBanana.setProgress(banana_count);
        }
        else {
            upgradeFlag = 1;
            tvBananaCount.setText("+"+banana_req);
            sbBanana.setProgress(user_banana_count);
        }
        if(bamboo_req <= 0) {
            tvBambooCount.setText("0");
            sbBamboo.setProgress(bamboo_count);
        }
        else {
            upgradeFlag = 1;
            tvBambooCount.setText("+"+bamboo_req);
            sbBamboo.setProgress(user_bamboo_counnt);
        }

        if(upgradeFlag == 1)
            iv_buy_ship.setImageAlpha(30);

        iv_buy_ship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }

    private void setInventoriesCount(int item_id, int count) {
        if(item_id == 1)
            coconut_count = count;
        else if(item_id == 2)
            timber_count = count;
        else if(item_id == 3)
            banana_count = count;
        else if(item_id == 4)
            bamboo_count = count;
        else{}
    }
}
