package in.silive.directme.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import in.silive.directme.R;
import in.silive.directme.Activity.Showroom;
import in.silive.directme.Activity.enter_db;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by YESH AGNIHOTRI on 19-11-2016.
 */

public class dock2 extends Fragment {
    ImageView boat_image;
    int dockstatus=0;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView;
        TextView get_gold,get_banana,get_wood,get_coconut,get_bamboo,boat_speed;

        SharedPreferences pref =getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);
        String boatname=pref.getString("Garage2",null);
        if(boatname!=null&&boatname!="")
        {
            dockstatus=1;
            rootView = inflater.inflate(R.layout.dock_full, container,
                    false);
            boat_image = (ImageView)rootView.findViewById(R.id.raftimage);
            boat_speed=(TextView)rootView.findViewById(R.id.fillval);
            if (boatname.equalsIgnoreCase("raft"))
            {
                boat_image.setImageResource(R.drawable.raft);
                boat_speed.setText(pref.getString("Raft"+"Speed",null));
            }
            if (boatname.equalsIgnoreCase("ship3"))
            {
                boat_image.setImageResource(R.drawable.ship);
                boat_speed.setText(pref.getString("Ship3"+"Speed",null));
            }
            if (boatname.equalsIgnoreCase("boat"))
            {
                boat_image.setImageResource(R.drawable.raft);
                boat_speed.setText(pref.getString("Boat"+"Speed",null));
            }
            if (boatname.equalsIgnoreCase("ship2"))
            {
                boat_image.setImageResource(R.drawable.raft);
            }
            if (boatname.equalsIgnoreCase("ship1"))
            {
                boat_image.setImageResource(R.drawable.raft);
            }
        }
        else
        {
            rootView=inflater.inflate(R.layout.dock_empty, container,
                    false);
        }

        get_gold=(TextView)rootView.findViewById(R.id.gold_no);
        get_banana=(TextView)rootView.findViewById(R.id.banana_no);
        get_coconut=(TextView)rootView.findViewById(R.id.coconut_no);
        get_bamboo=(TextView)rootView.findViewById(R.id.bamboo_no);
        get_wood=(TextView)rootView.findViewById(R.id.wood_no);

        String field;
        if(pref.getString("Banana",null)!=null)
        {
            field=pref.getString("Banana",null);
            get_banana.setText(field);
        }
        if(pref.getString("Coconut",null)!=null)
        {
            field=pref.getString("Coconut",null);
            get_coconut.setText(field);
        }
        if(pref.getString("Timber",null)!=null)
        {
            field=pref.getString("Timber",null);
            get_wood.setText(field);
        }
        if(pref.getString("Bamboo",null)!=null)
        {
            field=pref.getString("Bamboo",null);
            get_bamboo.setText(field);
        }
        if(pref.getString("Gold",null)!=null)
        {
            field=pref.getString("Gold",null);
            get_gold.setText(field);
        }
        return rootView;

    }

    ImageView img,goto_db;
    ImageButton goto_boat;
    @Override
    public void onViewCreated(View view,Bundle savedInstanceState)
    {

        if (dockstatus==1)
        {
            img = (ImageView) getView().findViewById(R.id.upgrade);
            // set a onclick listener for when the button gets clicked
            img.setOnClickListener(new View.OnClickListener() {
                // Start new list activity
                public void onClick(View v)
                {
                    Intent i = new Intent(getContext(), Showroom.class);
                    startActivity(i);
                }
            });
        }

        goto_db = (ImageView) getView().findViewById(R.id.db_entry);

        goto_db.setOnClickListener(new View.OnClickListener()
        {
            // Start new list activity
            public void onClick(View v) {
                Intent i = new Intent(getContext(), enter_db.class);
                startActivity(i);
            }
        });


    }


}
