package in.silive.directme.fragments;

/**
 * Created by simran on 2/24/2017.
 */


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
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
import it.sephiroth.android.library.tooltip.Tooltip;
import jp.wasabeef.picasso.transformations.BlurTransformation;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import jp.wasabeef.picasso.transformations.GrayscaleTransformation;

import static in.silive.directme.fragments.DockyardFargment.json_data;

/**
 * Created by simran on 2/23/2017.
 */

public class ParknowUsershipselectFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    Button select;
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
        Typeface type = Typeface.createFromAsset(getActivity().getAssets(),"fonts/CarnevaleeFreakshow.ttf");
        boat_name.setTypeface(type);
        select.setOnClickListener(this);
        AlphaAnimation fadeOut = new AlphaAnimation(0.0f , 1.0f ) ;
        AlphaAnimation fadeIn = new AlphaAnimation( 1.0f , 0.0f ) ;
        boat_name.startAnimation(fadeIn);
        boat_name.startAnimation(fadeOut);
        fadeIn.setDuration(500);
        fadeIn.setFillAfter(true);
        fadeOut.setDuration(1000);
        fadeOut.setFillAfter(true);
        fadeOut.setStartOffset(500+fadeIn.getStartOffset());
        try {
            json_data = new JSONObject(getArguments().getString("data", ""));
            boatName = json_data.getString("name");
            boatImageUrl = json_data.getString("ship_image");
            status = json_data.getString("ship_status");
            id=json_data.getString("ship_id");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(status.equals("Idle"))
        {
            Picasso.with(getContext())
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
            Picasso.with(getContext())
                    .load(boatImageUrl)
                    .transform(new GrayscaleTransformation())
                    .into(boat_image, new Callback() {
                        @Override
                        public void onSuccess() {
                            pbShip.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {

                        }
                    });

            select.setVisibility(View.GONE);
            boat_name.setText(boatName);
            boat_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Tooltip.make(
                            getContext(),
                            new Tooltip.Builder()
                                    .anchor(v, Tooltip.Gravity.RIGHT)
                                    .closePolicy(new Tooltip.ClosePolicy()
                                            .insidePolicy(true, false)
                                            .outsidePolicy(true, false), 3000)
                                    .text("Boat is already Docked")
                                    .withStyleId(R.style.ToolTipLayoutCustomStyleParknow)
                                    .fitToScreen(true)
                                    .activateDelay(800)
                                    .showDelay(300)
                                    .maxWidth(500)
                                    .withArrow(true)
                                    .withOverlay(false)
                                    .floatingAnimation(Tooltip.AnimationBuilder.DEFAULT)
                                    .build()
                    ).show();

                }
            });
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

