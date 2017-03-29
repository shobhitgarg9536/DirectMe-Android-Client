package in.silive.directme.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
 * Created by Shobhit-pc on 3/11/2017.
 */

public class GarageUpgradeFragment extends Fragment {

    SeekBar sbBanana, sbCoconut, sbBamboo, sbTimber;
    int user_banana_count, user_coconut_count, user_bamboo_counnt, user_timber_count;
    private String ship_id, ship_name, ship_image_url;
    int[] upgrade_count;
    ImageView btupgrade, ivshipImage;
    ProgressBar pbUpgradeShip;
    TextView tvshipName, tvBananaCount, tvCoconutCount, tvBambooCount, tvTimberCount;
    int upgradeFlag = 0;
    SharedPreferences sharedPreferences;
    FetchData fetchData;
    public int[] commod = new int[5];

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.garage_upgrade_dialog , container, false);
        sbBanana = (SeekBar) view.findViewById(R.id.seekBarGarageUpgradeBanana);
        sbCoconut = (SeekBar) view.findViewById(R.id.seekBarGarageUpgradeCoconut);
        sbBamboo = (SeekBar) view.findViewById(R.id.seekBarGarageUpgradeBamboo);
        sbTimber = (SeekBar) view.findViewById(R.id.seekBarGarageUpgradeWood);
        btupgrade = (ImageView) view.findViewById(R.id.imageViewUpgradeGarageShip);
        ivshipImage = (ImageView) view.findViewById(R.id.imageViewUpgradeShipImage);
        tvshipName = (TextView) view.findViewById(R.id.textViewGarageUpgradeShipName);
        tvBananaCount = (TextView) view.findViewById(R.id.textViewUpgradeBanana);
        tvCoconutCount = (TextView) view.findViewById(R.id.textViewUpgradeCoconut);
        tvBambooCount = (TextView) view.findViewById(R.id.textViewUpgradeBamboo);
        tvTimberCount = (TextView) view.findViewById(R.id.textViewUpgradeTimber);
        pbUpgradeShip = (ProgressBar) view.findViewById(R.id.progressBarUpgradeBoat);

        sbBamboo.setEnabled(false);
        sbBanana.setEnabled(false);
        sbCoconut.setEnabled(false);
        sbTimber.setEnabled(false);

        Bundle bundle = getArguments();
        String upgradeShipJsonArray = bundle.getString("json");
        ship_id = bundle.getString("ship_id");

        sharedPreferences = DirectMe.getInstance().sharedPrefs;

        try {
            JSONObject jsonObject = new JSONObject(upgradeShipJsonArray);
            ship_name = jsonObject.getString("name");
            ship_image_url = jsonObject.getString("image");
            JSONArray jsonArray1 = jsonObject.getJSONArray("items_required");
            upgrade_count = new int[jsonArray1.length()];
            for(int i=0; i<jsonArray1.length(); i++) {
                JSONObject jsonObject1 = jsonArray1.getJSONObject(i);
                    upgrade_count[i] = jsonObject1.getInt("count");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        tvshipName.setText("Üpgrade Ship to "+ship_name);
        Picasso.with(getContext())
                .load(ship_image_url)
                .into(ivshipImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        pbUpgradeShip.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {

                    }
                });

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

        sbCoconut.setMax(upgrade_count[0]);
        sbTimber.setMax(upgrade_count[1]);
        sbBanana.setMax(upgrade_count[2]);
        sbBamboo.setMax(upgrade_count[3]);

        int banana_req, coconut_req, bamboo_req, timber_req;
        coconut_req = upgrade_count[0] - user_coconut_count;
        timber_req = upgrade_count[1] - user_timber_count;
        banana_req = upgrade_count[2] - user_banana_count;
        bamboo_req = upgrade_count[3] - user_bamboo_counnt;

        if(coconut_req <= 0) {
            tvCoconutCount.setText("0");
            sbCoconut.setProgress(upgrade_count[0]);
        }
        else {
            upgradeFlag = 1;
            tvCoconutCount.setText("+"+coconut_req);
            sbCoconut.setProgress(user_coconut_count);
        }
        if(timber_req <= 0) {
            tvTimberCount.setText("0");
            sbTimber.setProgress(upgrade_count[1]);
        }
        else {
            upgradeFlag = 1;
            tvTimberCount.setText("+"+timber_req);
            sbTimber.setProgress(user_timber_count);
        }
        if(banana_req <= 0) {
            tvBambooCount.setText("0");
            sbBanana.setProgress(upgrade_count[2]);
        }
        else {
            upgradeFlag = 1;
            tvBananaCount.setText("+"+banana_req);
            sbBanana.setProgress(user_banana_count);
        }
        if(bamboo_req <= 0) {
            tvBambooCount.setText("0");
            sbBamboo.setProgress(upgrade_count[3]);
        }
        else {
            upgradeFlag = 1;
            tvBambooCount.setText("+"+bamboo_req);
            sbBamboo.setProgress(user_bamboo_counnt);
        }

        if(upgradeFlag == 1)
            btupgrade.setImageAlpha(30);

        btupgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(upgradeFlag == 0){
                    upgrateShip();
                }
            }
        });

        return view;
    }

    void upgrateShip() {
        final String token = sharedPreferences.getString(Constants.AUTH_TOKEN, "");
        boolean network_available = NetworkUtils.isNetConnected();
        if (network_available) {
            fetchData = new FetchData(new FetchDataListener() {
                @Override
                public void processStart() {

                }

                @Override
                public void processFinish(String output) {
                    Upgrade();
                }
            }, getContext());
            String post_data = "";

            try {
                post_data= URLEncoder.encode("ship_id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(ship_id), "UTF-8");

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            fetchData.setArgs(API_URL_LIST.UPGRADE_URL, token, post_data);
            fetchData.execute();
        }else{
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.alertDialog(getContext());
        }
    }

    void Upgrade()
    {
        final String Response=sharedPreferences.getString(Constants.RESPONSE_CODE,"");

        if (Response.equals("200")) {
            Toast.makeText(getActivity(), "Your ship is upgraded", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "Sorry your ship is not upgraded", Toast.LENGTH_LONG).show();
        }

    }

}
