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
 * Created by Shobhit-pc on 3/15/2017.
 */

public class BuyShipGoldCoins extends Fragment {

    JSONObject json_data;
    int goldCoinCost =0;
    int user_gold_coin_count;
    SharedPreferences sharedPreferences;
    public int[] commod = new int[5];
    TextView tvGoldCoinCount, tv_ship_name;
    SeekBar sbGoldCoin;
    RadioButton rb_resources;
    ImageView iv_buy_ship, iv_ship_image;
    ProgressBar pb_ship;
    int upgradeFlag =0;
    String boatName,boatImageUrl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        try {

            json_data = new JSONObject(getArguments().getString("json", ""));
            boatName = json_data.getString("name");
            boatImageUrl = json_data.getString("image");
            goldCoinCost = json_data.getInt("buy_cost");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        View view = inflater.inflate(R.layout.buy_ship_gold_coin, container, false);
        sbGoldCoin = (SeekBar) view.findViewById(R.id.seekBarShowroomGoldCoins);
        iv_buy_ship = (ImageView) view.findViewById(R.id.imageViewShowroomBuyShipGoldCoin);
        tvGoldCoinCount = (TextView) view.findViewById(R.id.textViewShowroomBuyGoldCoins);
        rb_resources = (RadioButton) view.findViewById(R.id.radioButtonResources2);
        iv_ship_image = (ImageView) view.findViewById(R.id.imageViewBuyShipImage2);
        tv_ship_name = (TextView) view.findViewById(R.id.textViewShowroomBuyShipName2);
        pb_ship = (ProgressBar) view.findViewById(R.id.progressBarShowroomBoat2);
        tv_ship_name.setText("Buy Ship "+boatName);

        sbGoldCoin.setEnabled(false);

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

        user_gold_coin_count =commod[2];

        sbGoldCoin.setMax(goldCoinCost);

        int gold_coin_req;
        gold_coin_req = goldCoinCost - user_gold_coin_count;

        if(gold_coin_req <= 0) {
            tvGoldCoinCount.setText("0");
            sbGoldCoin.setProgress(goldCoinCost);
        }
        else {
            upgradeFlag = 1;
            tvGoldCoinCount.setText("+"+gold_coin_req);
            sbGoldCoin.setProgress(user_gold_coin_count);
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

        rb_resources.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new BuyShipResources();
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
                post_data= URLEncoder.encode("pay_type", "UTF-8") + "=" + URLEncoder.encode(String.valueOf("gold_coin"), "UTF-8");

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
