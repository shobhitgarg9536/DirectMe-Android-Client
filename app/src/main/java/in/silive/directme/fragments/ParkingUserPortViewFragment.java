package in.silive.directme.fragments;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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
import in.silive.directme.utils.Keys;
import in.silive.directme.utils.NetworkUtils;

/**
 * Created by Shobhit-pc on 3/7/2017.
 */

public class ParkingUserPortViewFragment extends Fragment {

    ConstraintLayout clBackground;
    Button btDock;

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
    String port_jsonObject , parkingType, userName, user_id, token, ship_id;
    TextView parkingTypeTextView, userNameTextview;
    SharedPreferences sharedPreferences;
    Boolean network_available;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.portdetails , container ,false);
        clBackground=(ConstraintLayout) view.findViewById(R.id.background);
        btDock=(Button) view.findViewById(R.id.catchbutton);
        port_jsonObject = getArguments().getString("port_jsonObject");
        parkingType = getArguments().getString("parkingType");
        userNameTextview = (TextView) view.findViewById(R.id.username);
        parkingTypeTextView = (TextView) view.findViewById(R.id.type);
        startAnimation();

        parkingTypeTextView.setText(parkingType);

        try {
            JSONObject jsonObject = new JSONObject(port_jsonObject);
            userName = jsonObject.getString("username");
            user_id = jsonObject.getString("user_id");
            ship_id = jsonObject.getString("ship");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        userNameTextview.setText(userName);

        sharedPreferences = DirectMe.getInstance().sharedPrefs;

        btDock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user_id.equals(sharedPreferences.getString(Keys.user_id , ""))){
                    network_available = NetworkUtils.isNetConnected();
                    token = sharedPreferences.getString(Constants.AUTH_TOKEN, "");
                    if (network_available) {
                      FetchData  apicalling = new FetchData(new FetchDataListener() {
                            @Override
                            public void processStart() {

                            }

                            @Override
                            public void processFinish(String output) {
                                alertDialog("You have unsuccessfully undock your ship");
                            }
                        });
                        String post_data = "";

                        try {
                            post_data= URLEncoder.encode("ship_id", "UTF-8") + "=" + URLEncoder.encode(ship_id, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        apicalling.setArgs(API_URL_LIST.UNDOCK_URL, token, post_data);
                        apicalling.execute();

                    }
                }
            }
        });
        return view;
    }

    void alertDialog(String message)
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage(message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
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
                clBackground.setBackgroundDrawable(animation);
            } else {
                clBackground.setBackground(animation);
            }

            // start animation on image
            clBackground.post(new Runnable() {

                @Override
                public void run() {
                    animation.start();
                }

            });

        }
    }
}
