package com.silab.direct_me;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by YESH AGNIHOTRI on 19-11-2016.
 */

public class Boats_equipped extends Fragment

{
    static JSONObject json_data;
    static int slot;
    public static Boats_equipped newInstance(JSONObject jsonObject, int value)
    {
        Boats_equipped boats_equipped=new Boats_equipped();
        json_data=jsonObject;
        slot=value;
        return boats_equipped;
    }


    int dockstatus=1;
    TextView banana_req, gold_req, wood_req, bamboo_req, coconut_req;
    ImageView img;
    int banana_r = 0, gold_r = 0, bamboo_r = 0, wood_r = 0, coconut_r = 0;
    ImageView boat_image;
    TextView boat_speed;

    SharedPreferences pref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        pref = getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);
        View rootView = inflater.inflate(R.layout.dockyard, container,
                false);

        try
        {

            String name=json_data.getString("name");

            TextView boatname;
            boatname=(TextView)rootView.findViewById(R.id.boatname);
            boatname.setText(name);
    }
        catch (Exception e)
        {

        }
    /*public void onResume()
    {
        super.onResume();

        banana_req.setText(pref.getString("Raft" + "Banana", null));
        gold_req.setText(pref.getString("Raft" + "Gold", null));
        bamboo_req.setText(pref.getString("Raft" + "Bamboo", null));
        wood_req.setText(pref.getString("Raft" + "Timber", null));
        coconut_req.setText(pref.getString("Raft" + "Coconut", null));

    }*/
        return rootView;
}

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {

        if (dockstatus==1)
        {
            img = (ImageView) getView().findViewById(R.id.upgrade);
            // set a onclick listener for when the button gets clicked
            img.setOnClickListener(new View.OnClickListener() {
                // Start new list activity
                public void onClick(View v)
                {
                    Intent i = new Intent(getContext(), Show_room.class);
                    i.putExtra("slot",slot);
                    startActivity(i);
                }
            });
        }
    }
}
