package com.silab.direct_me;


import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

public class Ships_fragment extends Fragment
{
    static JSONObject json_data;
    public static Ships_fragment newInstance(JSONObject jsonObject)
    {
        Ships_fragment shipd=new Ships_fragment();
        json_data=jsonObject;
        return shipd;
    }



    TextView banana_req, gold_req, wood_req, bamboo_req, coconut_req;
    ImageView img;
    int banana_r = 0, gold_r = 0, bamboo_r = 0, wood_r = 0, coconut_r = 0;
    ImageView boat_image;
    TextView boat_speed;

    SharedPreferences pref;
    int id=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        pref = getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);
        View rootView = inflater.inflate(R.layout.raft_fragment, container,
                false);
        rootView.setId(id);
        id++;
        try {
            int id=json_data.getInt("id");
            String name=json_data.getString("name");
            int cost_multiplier=json_data.getInt("cost_multiplier");
            int experience_gain=json_data.getInt("experience_gain");
            String image=json_data.getString("image");
            int buy_cost=json_data.getInt("buy_cost");

            TextView boatname;
            boatname=(TextView)rootView.findViewById(R.id.boatname);
            boatname.setText(name);
            if(json_data.getJSONArray("items_required").length()!=0) {
                JSONArray jsonArray = json_data.getJSONArray("items_required");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (jsonObject.getString("name").equalsIgnoreCase("Gold")) {
                        gold_r = jsonObject.getInt("count");
                    }
                    if (jsonObject.getString("name").equalsIgnoreCase("Coconut"))
                        coconut_r = jsonObject.getInt("count");
                    if (jsonObject.getString("name").equalsIgnoreCase("Timber"))
                        wood_r = jsonObject.getInt("count");
                    if (jsonObject.getString("name").equalsIgnoreCase("Bamboo"))
                        bamboo_r = jsonObject.getInt("count");
                    if (jsonObject.getString("name").equalsIgnoreCase("Banana"))
                        banana_r = jsonObject.getInt("count");
                }
            }

        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        banana_req = (TextView) rootView.findViewById(R.id.getbanana);
        bamboo_req = (TextView) rootView.findViewById(R.id.getbamboo);
        gold_req = (TextView) rootView.findViewById(R.id.getgold);
        wood_req = (TextView) rootView.findViewById(R.id.getwood);
        coconut_req = (TextView) rootView.findViewById(R.id.getcoconut);
        banana_req.setText(Integer.toString(banana_r));
        bamboo_req.setText(Integer.toString(bamboo_r));
        gold_req.setText(Integer.toString(gold_r));
        wood_req.setText(Integer.toString(wood_r));
        coconut_req.setText(Integer.toString(coconut_r));



        ImageView boat_image;
        TextView boat_speed;
        //boat_image = (ImageView)rootView.findViewById(R.id.raftimage);
        boat_speed=(TextView)rootView.findViewById(R.id.fillval);
        //boat_image.setImageResource(R.drawable.raft);
        //boat_speed.setText(pref.getString("Raft"+"Speed",null));
        return rootView;

    }

    public static final int DFRAGMENT = 1;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        FragmentTransaction ft=getChildFragmentManager().beginTransaction();
        final DFragment dFragment = new DFragment();
        // TODO Auto-generated method stub
        // get the button view
        img = (ImageView) getView().findViewById(R.id.buy1);
        // set a onclick listener for when the button gets clicked
        img.setOnClickListener(new View.OnClickListener() {
            // Start new list activity
            public void onClick(View v) {

                onResume();
                checkPurchase cp = new checkPurchase(getContext());

                boolean result = cp.forpurchase(banana_r, bamboo_r, gold_r, coconut_r, wood_r);
                String sendres;

                if (result == true)
                    sendres = "true";
                else
                    sendres = "false";


                Bundle args = new Bundle();
                args.putString("check", sendres + " " + Integer.toString(banana_r) + " " + Integer.toString(gold_r) + " " + Integer.toString(gold_r) + " " + Integer.toString(bamboo_r) + " " + Integer.toString(coconut_r) + " ");
                dFragment.setArguments(args);
                // Show DialogFragment
                dFragment.show(getFragmentManager(), "Dialog Fragment");      // custom dialog
                com.silab.direct_me.Ships_fragment obj = new com.silab.direct_me.Ships_fragment();
            }
        });

        ft.commit();
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



}