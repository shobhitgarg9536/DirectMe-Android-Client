package in.silive.directme.fragments;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import in.silive.directme.R;
import in.silive.directme.application.DirectMe;
import in.silive.directme.listeners.FetchDataListener;
import in.silive.directme.network.FetchData;
import in.silive.directme.utils.API_URL_LIST;
import in.silive.directme.utils.BitmapUtils;
import in.silive.directme.utils.Constants;
import in.silive.directme.utils.NetworkUtils;

/**
 * Created by simran on 3/7/2017.
 */

public class PortDetailsFragment extends Fragment implements View.OnClickListener {
    TextView UsernameTextview,TypeTextView;
    JSONObject json_data;
    Button Catch;
    ImageView land,boat;
    private String type,id;
    SharedPreferences sharedPreferences;
    ConstraintLayout r1;
    FetchData apicalling;
    int flag=0;
    String ship_id;
    String username,ship_img_url;
    private boolean network_available;
    // frame width
    private static final int FRAME_W = 300;
    // frame height
    private static final int FRAME_H = 180;
    // number of frames
    private static final int NB_FRAMES = 20;
    // nb of frames in x
    private static final int COUNT_X = 5;
    // nb of frames in y
    private static final int COUNT_Y = 4;
    // we can slow animation by changing frame duration
    private static final int FRAME_DURATION = 150; // in ms !
    // frame duration

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.portdetails, container,
                false);


        UsernameTextview = (TextView) v.findViewById(R.id.username);

        TypeTextView = (TextView) v.findViewById(R.id.type);

        land=(ImageView)v.findViewById(R.id.land);
        boat=(ImageView)v.findViewById(R.id.boat);
        Catch=(Button) v.findViewById(R.id.catchbutton);
        r1=(ConstraintLayout)v.findViewById(R.id.background);

        Catch.setOnClickListener(this);
        sharedPreferences = DirectMe.getInstance().sharedPrefs;

        try {
            json_data= new JSONObject(getArguments().getString("data", ""));

            type = json_data.get("type").toString();
            if(type.equals("parking"))
            {
                Catch.setEnabled(false);
            }
            else
            {
                Catch.setEnabled(true);
            }
            TypeTextView.setText(type);
            id=json_data.get("id").toString();
            JSONArray logs=json_data.getJSONArray("logs");
            if(logs.length()>0)
            {
                JSONObject logdetails=logs.getJSONObject(0);
                username=logdetails.get("username").toString();
                ship_img_url=logdetails.get("ship_image").toString();
                ship_id=logdetails.get("ship").toString();
                Picasso.with(getContext())
                        .load(ship_img_url)
                        .into(boat);

                UsernameTextview.setText(username);
                Catch.setEnabled(true);
            }
            else
            {

                UsernameTextview.setText("N-A");
                TypeTextView.setText(type);

                Catch.setEnabled(false);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        startAnimation();

        return v;
    }
    private void startAnimation() {
        Bitmap waterbmp = BitmapUtils.getBitmapFromAssets("splashh.png");
        if (waterbmp != null) {

            Bitmap[] bitmaps = BitmapUtils.getBitmapsFromSprite(waterbmp, NB_FRAMES, COUNT_X, COUNT_Y, FRAME_H, FRAME_W);

            // create animation programmatically
            final AnimationDrawable animation = new AnimationDrawable();
            animation.setOneShot(false); // repeat animation
            for (int i = 0; i < NB_FRAMES; i++) {
                animation.addFrame(new BitmapDrawable(getResources(), bitmaps[i]),
                        FRAME_DURATION);
            }

            // load animation on image
            if (Build.VERSION.SDK_INT < 16) {
                r1.setBackgroundDrawable(animation);
            } else {
                r1.setBackground(animation);
            }

            // start animation on image
            r1.post(new Runnable() {

                @Override
                public void run() {
                    animation.start();
                }

            });

        }
    }


    @Override
    public void onClick(View v) {
        if(flag==0)
        {
            Catch.setEnabled(true);
            alertDialog();
        }
        else if(flag==1)
        {
            Catch.setEnabled(false);
        }


    }
    void alertDialog()
    {final String ship_img=sharedPreferences.getString(Constants.SHIP_IMAGE_URL,"");

        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage("Do you want to Catch ship");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        flag=1;
                        connect();

                        Toast.makeText(getActivity(),"You Caught ship",Toast.LENGTH_LONG).show();
                        boat.setVisibility(View.GONE);
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        flag=0;
                        dialog.cancel();
                        Toast.makeText(getActivity(),"Your ship is not docked",Toast.LENGTH_LONG).show();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
    void connect() {
        final String token = sharedPreferences.getString(Constants.AUTH_TOKEN, "");

        network_available = NetworkUtils.isNetConnected();
        if (network_available) {
            apicalling = new FetchData(new FetchDataListener() {
                @Override
                public void processStart() {

                }

                @Override
                public void processFinish(String output) {

                }
            });
            String post_data = "";

            try {
                post_data= URLEncoder.encode("ship_id", "UTF-8") + "=" + URLEncoder.encode(ship_id, "UTF-8");

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            apicalling.setArgs(API_URL_LIST.FINE_URL, token, post_data);
            apicalling.execute();

        }
    }
}
