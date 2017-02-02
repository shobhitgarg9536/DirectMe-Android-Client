package com.silab.direct_me;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Ships_fragment extends Fragment implements fetch_dock_id.Response
{
    JSONObject json_data;
    int slot;
    fetch_dock_id.FetchRequest object=new fetch_dock_id().new FetchRequest(this);

    /*public static Ships_fragment newInstance(JSONObject jsonObject,int value)
    {
        Ships_fragment shipd=new Ships_fragment();
        json_data=jsonObject;
        slot=value;
        return shipd;
    }*/



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
        //pref = getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);
        slot=getArguments().getInt("slot");
        try {
            json_data=new JSONObject(getArguments().getString("data",""));
        } catch (JSONException e) {
            json_data=null;
            e.printStackTrace();
        }
        View rootView = inflater.inflate(R.layout.raft_fragment, container,
                false);
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
    public void onViewCreated(final View view, Bundle savedInstanceState) {

        final FragmentTransaction ft=getChildFragmentManager().beginTransaction();
        final DFragment dFragment = new DFragment();
        // TODO Auto-generated method stub
        // get the button view
        img = (ImageView) getView().findViewById(R.id.buy1);
        // set a onclick listener for when the button gets clicked
        img.setOnClickListener(new View.OnClickListener() {
                                   // Start new list activity
                                   public void onClick(View v)
                                   {
                                       fetch_dock_id.FetchRequest object=new fetch_dock_id().new FetchRequest(Ships_fragment.this);
                                         object.execute();
                                       //onResume();


                                   }
                               });
    }

    public void processfinish(String result)
    {
        FragmentTransaction ft=getChildFragmentManager().beginTransaction();
        final DGFragment dFragment = new DGFragment();

        int res=999;
        try {
            JSONArray jsonArray=new JSONArray(result);
            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject json_data = jsonArray.getJSONObject(i);
                int get_slot=json_data.getInt("slot");
                Log.d("slot on this",""+slot);
                Log.d("get_slot",""+get_slot);
                if(get_slot==slot)
                {
                    res=json_data.getInt("ship");
                    Log.d("id json",""+json_data.getInt("ship"));
                    break;
                }
            }
          Log.d("id",""+res);

        } catch (JSONException e) {
            e.printStackTrace();
        }

             Bundle args=new Bundle();
             args.putInt("dock_id",res);
             dFragment.setTargetFragment(this,0);
             dFragment.setArguments(args);
            dFragment.show(ft, "Dialog Fragment");
    }

    public void onActivityResult(int requestcode,int resultcode,Intent intent)
    {
        String message=intent.getStringExtra("message");
        FragmentTransaction ft=this.getChildFragmentManager().beginTransaction();
        final DFragment dFragment = new DFragment();
       Log.d("post dialog msg",intent.getStringExtra("message"));
        Bundle args=new Bundle();
        args.putString("message",message);
        dFragment.setArguments(args);
        dFragment.show(ft, "Dialog Fragment");

    }
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


