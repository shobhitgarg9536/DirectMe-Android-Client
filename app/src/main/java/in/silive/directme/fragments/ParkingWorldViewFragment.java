package in.silive.directme.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import in.silive.directme.R;
import in.silive.directme.application.DirectMe;
import in.silive.directme.listeners.AsyncResponse;
import in.silive.directme.network.FetchData;
import in.silive.directme.utils.API_URL_LIST;
import in.silive.directme.utils.Constants;
import in.silive.directme.utils.NetworkUtils;

public class ParkingWorldViewFragment extends Fragment{

    ImageView iv_island1,iv_island2,iv_island3,iv_island4,iv_pirate;
    SharedPreferences sharedPreferences;
    boolean network_available;
    FetchData fetchData;
    JSONArray parked_ships_jsonArray;
    int count;
    private String status;
    private String island_id;
    String token;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.parknow, container , false);

        iv_island1 = (ImageView) view.findViewById(R.id.imageViewisland1marker);
        iv_island2 = (ImageView) view.findViewById(R.id.imageViewisland2marker);
        iv_island3 = (ImageView) view.findViewById(R.id.imageViewisland3marker);
        iv_island4 = (ImageView) view.findViewById(R.id.imageViewisland4marker);
        iv_pirate = (ImageView) view.findViewById(R.id.imageViewpirateislandmarker);


        apiCalling();

        return view;
    }


    void apiCalling() {
        sharedPreferences = DirectMe.getInstance().sharedPrefs;
        token = sharedPreferences.getString(Constants.AUTH_TOKEN, "");
        network_available = NetworkUtils.isNetConnected();
        if (network_available) {
            fetchData = new FetchData(new AsyncResponse() {
                @Override
                public void processStart() {

                }

                @Override
                public void processFinish(String output) {
                    try {
                        parked_ships_jsonArray = new JSONArray(output);
                        count = parked_ships_jsonArray.length();

                        for(int i=0; i<count; i++ ){
                            JSONObject jsonObject = parked_ships_jsonArray.getJSONObject(i);

                            status = jsonObject.getString("status");
                            if(status.equals("Busy")){
                                island_id = jsonObject.getString("island_id");
                                makeMarkerVisible(island_id);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            fetchData.setArgs(API_URL_LIST.PARKING_DOCKS_URL, token, "");
            fetchData.execute();

        }
    }

   void makeMarkerVisible(String island_id){
       switch (Integer.valueOf(island_id)){
           case 1:
               iv_island1.setVisibility(View.VISIBLE);
               break;
           case 2:
               iv_island2.setVisibility(View.VISIBLE);
               break;
           case 3:
               iv_island3.setVisibility(View.VISIBLE);
               break;
           case 4:
               iv_island4.setVisibility(View.VISIBLE);
               break;
           case 5:
               iv_pirate.setVisibility(View.VISIBLE);
               break;


       }
    }
}