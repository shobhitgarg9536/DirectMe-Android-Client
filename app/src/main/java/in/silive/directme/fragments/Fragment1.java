package in.silive.directme.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import in.silive.directme.R;

/**
 * Created by Shobhit-pc on 3/14/2017.
 */

public class Fragment1 extends Fragment {

    ImageView iv_ship_image;
    TextView tv_ship_name;
    ProgressBar pb_ship;
    String boatName,boatImageUrl;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        try {
            JSONObject json_data = new JSONObject(getArguments().getString("json",""));
            boatName = json_data.getString("name");
            boatImageUrl = json_data.getString("image");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        View view = inflater.inflate(R.layout.fragment1, container, false);

        iv_ship_image = (ImageView) view.findViewById(R.id.imageViewBuyShipImage);
        tv_ship_name = (TextView) view.findViewById(R.id.textViewShowroomBuyShipName);
        pb_ship = (ProgressBar) view.findViewById(R.id.progressBarShowroomShip);
        tv_ship_name.setText("Buy Ship "+boatName);

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

        return view;
    }
}
