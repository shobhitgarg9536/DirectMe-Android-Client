package in.silive.directme.Fragments;


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

import org.json.JSONException;
import org.json.JSONObject;

import in.silive.directme.R;

public class Ships_fragment extends Fragment
{
    JSONObject json_data;
    int slot;


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
        final Dialog_Fragment dialogFragment = new Dialog_Fragment();
        // TODO Auto-generated method stub
        // get the button view

    }


    public void onActivityResult(int requestcode,int resultcode,Intent intent)
    {
        String message=intent.getStringExtra("message");
        FragmentTransaction ft=this.getChildFragmentManager().beginTransaction();
        final Dialog_Fragment dialogFragment = new Dialog_Fragment();
        Log.d("post dialog msg",intent.getStringExtra("message"));
        Bundle args=new Bundle();
        args.putString("message",message);
        dialogFragment.setArguments(args);
        dialogFragment.show(ft, "Dialog Fragment");

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


