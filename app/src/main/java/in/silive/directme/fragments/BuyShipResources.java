package in.silive.directme.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import in.silive.directme.R;
import in.silive.directme.activity.DashboardActivity;
import in.silive.directme.application.DirectMe;
import in.silive.directme.dialog.AlertDialog;
import in.silive.directme.listeners.FetchDataListener;
import in.silive.directme.network.FetchData;
import in.silive.directme.utils.API_URL_LIST;
import in.silive.directme.utils.Constants;
import in.silive.directme.utils.NetworkUtils;

/**
 * Created by Shobhit-pc on 3/14/2017.
 */

public class BuyShipResources extends Fragment {

    JSONObject json_data;
    String buyCost, boatName,boatImageUrl;
    int item_id, count, coconut_count =0, timber_count =0, banana_count =0, bamboo_count =0;
    int user_banana_count, user_coconut_count, user_bamboo_counnt, user_timber_count;
    SharedPreferences sharedPreferences;
    public int[] commod = new int[5];
    TextView tv_ship_name, tvBananaCount, tvCoconutCount, tvBambooCount, tvTimberCount;
    SeekBar sbBanana, sbCoconut, sbBamboo, sbTimber;
    ProgressBar pb_ship;
    ImageView iv_buy_ship, iv_ship_image;
    int upgradeFlag =0;
    RadioButton rb_gold_coin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        try {

            json_data = new JSONObject(getArguments().getString("json", ""));
            boatName = json_data.getString("name");
            boatImageUrl = json_data.getString("image");
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

        View view = inflater.inflate(R.layout.buy_ship_resources, container, false);
        sbBanana = (SeekBar) view.findViewById(R.id.seekBarShowroomBanana);
        sbCoconut = (SeekBar) view.findViewById(R.id.seekBarShowroomCoconut);
        sbBamboo = (SeekBar) view.findViewById(R.id.seekBarShowroomBamboo);
        sbTimber = (SeekBar) view.findViewById(R.id.seekBarShowroomWood);
        iv_buy_ship = (ImageView) view.findViewById(R.id.imageViewShowroomBuyShipResources);
        tvBananaCount = (TextView) view.findViewById(R.id.textViewShowroomBuyBanana);
        tvCoconutCount = (TextView) view.findViewById(R.id.textViewShowroomBuyCoconut);
        tvBambooCount = (TextView) view.findViewById(R.id.textViewShowroomBuyBamboo);
        tvTimberCount = (TextView) view.findViewById(R.id.textViewShowroomBuyTimber);
        rb_gold_coin = (RadioButton) view.findViewById(R.id.radioButtonGoldCoins1);

        iv_ship_image = (ImageView) view.findViewById(R.id.imageViewBuyShipImage1);
        tv_ship_name = (TextView) view.findViewById(R.id.textViewShowroomBuyShipName1);
        pb_ship = (ProgressBar) view.findViewById(R.id.progressBarShowroomBoat1);
        tv_ship_name.setText("Buy Ship "+boatName);

        sbBamboo.setEnabled(false);
        sbBanana.setEnabled(false);
        sbCoconut.setEnabled(false);
        sbTimber.setEnabled(false);

        Picasso.with(getContext())
                .load(boatImageUrl)
                .into(iv_ship_image, new Callback() {
                    @Override
                    public void onSuccess() {
                        pb_ship.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {

                    }
                });

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
                if(upgradeFlag == 0){
                    buyShip();
                }
            }
        });

        rb_gold_coin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new BuyShipGoldCoins();
                Bundle bundle = new Bundle();
                bundle.putString("json", json_data.toString());
                fragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.add(android.R.id.content, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
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

    void buyShip() {
        final String token = sharedPreferences.getString(Constants.AUTH_TOKEN, "");
        boolean network_available = NetworkUtils.isNetConnected();
        if (network_available) {
            FetchData fetchData = new FetchData(new FetchDataListener() {
                @Override
                public void processStart() {

                }

                @Override
                public void processFinish(String output) {
                    buyShipResponse();
                }
            });
            String post_data = "";

            try {
                post_data= URLEncoder.encode("pay_type", "UTF-8") + "=" + URLEncoder.encode(String.valueOf("resources"), "UTF-8");

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            fetchData.setArgs(API_URL_LIST.BUY_SHIP, token, post_data);
            fetchData.execute();
        }else{
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.alertDialog(getContext());
        }
    }

    void buyShipResponse()
    {
        final String Response=sharedPreferences.getString(Constants.RESPONSE_CODE,"");

        if (Response.equals("200")) {
            Toast.makeText(getActivity(), "Your bought ship", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "Sorry your cannot buy ship", Toast.LENGTH_LONG).show();
        }

    }
}
