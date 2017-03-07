package in.silive.directme.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import in.silive.directme.R;
import in.silive.directme.application.DirectMe;
import in.silive.directme.listeners.FetchDataListener;
import in.silive.directme.network.FetchData;
import in.silive.directme.utils.API_URL_LIST;
import in.silive.directme.utils.Constants;
import in.silive.directme.utils.NetworkUtils;

/**
 * Created by Shobhit-pc on 3/5/2017.
 */

public class ParkingPortFragment extends Fragment implements View.OnClickListener {

    String user_id;
    SharedPreferences sharedPreferences;
    String token;
    boolean network_available;
    FetchData fetchData;
    JSONArray user_port_jsonArray;
    int count;
    ImageView iv_port1 , iv_port2, iv_port3, iv_port4, iv_port5;
    JSONObject jsonObject_port1, jsonObject_port2,jsonObject_port3, jsonObject_port4, jsonObject_port5;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.parkonmine, container , false);

        Bundle bundle = getArguments();
        user_id =  bundle.getString("user_id");

        iv_port1 = (ImageView) view.findViewById(R.id.parkingport1);
        iv_port2 = (ImageView) view.findViewById(R.id.parkingport2);
        iv_port3 = (ImageView) view.findViewById(R.id.nonparkingport3);
        iv_port4 = (ImageView) view.findViewById(R.id.nonparkingport4);
        iv_port5 = (ImageView) view.findViewById(R.id.nonparkingport5);

        apiCalling();

        iv_port1.setOnClickListener(this);
        iv_port2.setOnClickListener(this);
        iv_port3.setOnClickListener(this);
        iv_port4.setOnClickListener(this);
        iv_port5.setOnClickListener(this);

        return view;
    }


    void apiCalling() {
        sharedPreferences = DirectMe.getInstance().sharedPrefs;
        token = sharedPreferences.getString(Constants.AUTH_TOKEN, "");
        network_available = NetworkUtils.isNetConnected();
        if (network_available) {
            fetchData = new FetchData(new FetchDataListener() {
                @Override
                public void processStart() {

                }

                @Override
                public void processFinish(String output) {
                    try {
                        user_port_jsonArray = new JSONArray(output);
                        count = user_port_jsonArray.length();

                        for(int i=0; i<count; i++ ){
                            JSONObject jsonObject = user_port_jsonArray.getJSONObject(i);
                            JSONArray jsonArray1 = jsonObject.getJSONArray("logs");
                            JSONObject jsonObject1 = jsonArray1.getJSONObject(0);
                            String ship_id = jsonObject1.getString("ship");
                            String user_name = jsonObject1.getString("username");
                            String user_id = jsonObject1.getString("user_id");
                            String ship_image = jsonObject1.getString("ship_image");

                            showPort(i+1 , ship_image , jsonObject1);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            fetchData.setArgs(API_URL_LIST.OTHER_USER_PORT_URL+user_id+"/", token, "");
            fetchData.execute();

        }
    }

    void showPort(int position , String ship_url , JSONObject jsonObject){

        switch (position){
            case 1:
                jsonObject_port1 = jsonObject;
                Picasso.with(DirectMe.getInstance())
                        .load(ship_url)
                        .into(iv_port1);

                        break;
            case 2:
                jsonObject_port2 = jsonObject;
                Picasso.with(DirectMe.getInstance())
                        .load(ship_url)
                        .into(iv_port2);
                        break;
            case 3:
                jsonObject_port3 = jsonObject;
                Picasso.with(DirectMe.getInstance())
                        .load(ship_url)
                        .into(iv_port3);
                        break;
            case 4:
                jsonObject_port4 = jsonObject;
                Picasso.with(DirectMe.getInstance())
                        .load(ship_url)
                        .into(iv_port4);
                        break;
            case 5:
                jsonObject_port5 = jsonObject;
                Picasso.with(DirectMe.getInstance())
                        .load(ship_url)
                        .into(iv_port5);
                        break;
        }
    }
    void replaceWithNewFragment(JSONObject jsonObject){
        Fragment islandFragment = new ParkingUserPortViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString("port_jsonObject" , jsonObject.toString());
        islandFragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(android.R.id.content, islandFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.parkingport1:
                replaceWithNewFragment(jsonObject_port1);
                break;
            case R.id.parkingport2:
                replaceWithNewFragment(jsonObject_port2);
                break;
            case R.id.nonparkingport3:
                replaceWithNewFragment(jsonObject_port3);
                break;
            case R.id.nonparkingport4:
                replaceWithNewFragment(jsonObject_port4);
                break;
            case R.id.nonparkingport5:
                replaceWithNewFragment(jsonObject_port5);
                break;
        }

    }
}
