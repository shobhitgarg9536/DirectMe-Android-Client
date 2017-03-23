package in.silive.directme.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import in.silive.directme.R;
import in.silive.directme.activity.DashboardActivity;
import in.silive.directme.activity.ParkNowActivity;
import in.silive.directme.application.DirectMe;
import in.silive.directme.listeners.FetchDataListener;
import in.silive.directme.network.FetchData;
import in.silive.directme.utils.API_URL_LIST;
import in.silive.directme.utils.BitmapUtils;
import in.silive.directme.utils.Constants;
import in.silive.directme.utils.NetworkUtils;


/**
 * Created by simran on 3/5/2017.
 */

public class ParkingDetailsFragment extends Fragment implements View.OnClickListener {
    TextView UsernameTextview,TypeTextView;
    JSONObject json_data;
    Button Catch;
    ImageView land,boat;
    private String type,id;
    SharedPreferences sharedPreferences;
    ConstraintLayout r1;
    FetchData apicalling;
    Button Dock;
    int flag=0;
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
    View v;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.portdetails, container,
                false);


        UsernameTextview = (TextView) v.findViewById(R.id.username);

        TypeTextView = (TextView) v.findViewById(R.id.type);

        land=(ImageView)v.findViewById(R.id.land);
        boat=(ImageView)v.findViewById(R.id.boat);

        r1=(ConstraintLayout)v.findViewById(R.id.background);
        Dock=(Button)v.findViewById(R.id.catchbutton);
        Dock.setOnClickListener(this);
        sharedPreferences = DirectMe.getInstance().sharedPrefs;

        try {
            json_data= new JSONObject(getArguments().getString("data", ""));
            type = json_data.get("type").toString();
            TypeTextView.setText(type);
            id=json_data.get("id").toString();
            JSONArray logs=json_data.getJSONArray("logs");
            if(logs.length()>0)
            {
                JSONObject logdetails=logs.getJSONObject(0);
                username=logdetails.get("username").toString();
                ship_img_url=logdetails.get("ship_image").toString();
                Picasso.with(getContext())
                        .load(ship_img_url)
                        .into(boat);

                UsernameTextview.setText(username);
                Dock.setEnabled(false);
            }
            else
            {

                UsernameTextview.setText("N-A");
                TypeTextView.setText(type);
                Dock.setEnabled(true);


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        startAnimation();





        return v;
    }

    @Override
    public void onClick(View v) {
        if(flag==0)
        {
            Dock.setEnabled(true);
            alertDialog();
        }
        else if(flag==1)
        {
            Dock.setEnabled(false);
        }


    }
    String  ship_image;
    void alertDialog()
    {final String ship_img=sharedPreferences.getString(Constants.SHIP_IMAGE_URL,"");
        ship_image=ship_img;
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage("Do you want to Dock your ship");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        flag=1;
                        connect();

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
    void dock()
    {
        Picasso.with(getContext())
                .load(ship_image)
                .into(boat);
        initiatePopupWindow("Congratulation your ship has been docked succesfully",1);
    }

    void connect() {
        final String token = sharedPreferences.getString(Constants.AUTH_TOKEN, "");
        final String ship_id=sharedPreferences.getString(Constants.SHIP_ID,"");
        network_available = NetworkUtils.isNetConnected();
        if (network_available) {
            apicalling = new FetchData(new FetchDataListener() {
                @Override
                public void processStart() {

                }

                @Override
                public void processFinish(String output) {
                    final String responsecode=sharedPreferences.getString(Constants.RESPONSE_CODE,"");
                    if (Integer.parseInt(responsecode)==201)
                    {
                        dock();



                    }
                    else
                    {
                        initiatePopupWindow("Sorry your ship is not docked...",0);
                    }

                }
            });
            String post_data = "";

            try {
                post_data= URLEncoder.encode("ship_id", "UTF-8") + "=" + URLEncoder.encode(ship_id, "UTF-8");
                post_data+="&"+URLEncoder.encode("port_id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            apicalling.setArgs(API_URL_LIST.Dock_Url, token, post_data);
            apicalling.execute();

        }
    }
    private PopupWindow pwindo;
    Button ok;
    int dock_status;
    private void initiatePopupWindow(String message_status,int status) {
        try {
// We need to get the instance of the LayoutInflater
            dock_status=status;
            LayoutInflater layoutInflater = (LayoutInflater)getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = layoutInflater.inflate(R.layout.screenpopup,
                    (ViewGroup)v.findViewById(R.id.popup_element));

            pwindo = new PopupWindow(layout, 580, 400, true);

            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);

            ok = (Button) layout.findViewById(R.id.ok);

            ok.setOnClickListener(cancel_button_click_listener);
            TextView message=(TextView) layout.findViewById(R.id.dock_status);
            message.setText(message_status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener cancel_button_click_listener = new View.OnClickListener() {
        public void onClick(View v) {
            if(dock_status==1) {
                Intent i = new Intent(getActivity(), DashboardActivity.class);
                startActivity(i);
                pwindo.dismiss();
            }
            else
            {
                pwindo.dismiss();
            }

        }
    };


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
}
