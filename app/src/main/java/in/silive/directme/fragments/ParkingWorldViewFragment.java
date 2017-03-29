package in.silive.directme.fragments;


import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.silive.directme.R;
import in.silive.directme.adapter.ParkingUserListAdapter;
import in.silive.directme.application.DirectMe;
import in.silive.directme.dialog.AlertDialog;
import in.silive.directme.listeners.FetchDataListener;
import in.silive.directme.model.ParkingUserListModel;
import in.silive.directme.network.FetchData;
import in.silive.directme.utils.API_URL_LIST;
import in.silive.directme.utils.Constants;
import in.silive.directme.utils.NetworkUtils;

public class ParkingWorldViewFragment extends Fragment implements View.OnClickListener {

    ImageView iv_island1, iv_island2, iv_island3, iv_island4, iv_pirate;
    SharedPreferences sharedPreferences;
    boolean network_available;
    FetchData fetchData;
    JSONArray parked_ships_jsonArray;
    int count;
    String token;
    JSONArray jsonArrayIslanad1, jsonArrayIslanad2, jsonArrayIslanad3, jsonArrayIslanad4;
    android.support.constraint.ConstraintLayout cluserList;
    private String status;
    private String island_id;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.parknow, container, false);

        iv_island1 = (ImageView) view.findViewById(R.id.imageViewisland1marker);
        iv_island2 = (ImageView) view.findViewById(R.id.imageViewisland2marker);
        iv_island3 = (ImageView) view.findViewById(R.id.imageViewisland3marker);
        iv_island4 = (ImageView) view.findViewById(R.id.imageViewisland4marker);
        iv_pirate = (ImageView) view.findViewById(R.id.imageViewpirateislandmarker);
        cluserList = (android.support.constraint.ConstraintLayout) view.findViewById(R.id.constrainstLayoutParkNow);

        iv_island1.setOnClickListener(this);
        iv_island2.setOnClickListener(this);
        iv_island3.setOnClickListener(this);
        iv_island4.setOnClickListener(this);
        iv_pirate.setOnClickListener(this);

        jsonArrayIslanad1 = new JSONArray();
        jsonArrayIslanad2 = new JSONArray();
        jsonArrayIslanad3 = new JSONArray();
        jsonArrayIslanad4 = new JSONArray();


        apiCalling();


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
                        parked_ships_jsonArray = new JSONArray(output);
                        count = parked_ships_jsonArray.length();

                        for (int i = 0; i < count; i++) {
                            JSONObject jsonObject = parked_ships_jsonArray.getJSONObject(i);

                            status = jsonObject.getString("ship_status");
                            if (status.equals("Busy")) {
                                island_id = jsonObject.getString("island_id");
                                makeMarkerVisible(island_id, jsonObject);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, getContext());
            fetchData.setArgs(API_URL_LIST.PARKING_DOCKS_URL, token, "");
            fetchData.execute();

        }else{
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.alertDialog(getContext());
        }
    }

    void makeMarkerVisible(String island_id, JSONObject jsonObject) {
        switch (Integer.valueOf(island_id)) {
            //Coconut island
            case 1:
                iv_island2.setVisibility(View.VISIBLE);
                jsonArrayIslanad2.put(jsonObject);

                break;
            //Timber Island
            case 2:
                iv_island4.setVisibility(View.VISIBLE);
                jsonArrayIslanad4.put(jsonObject);
                break;
            //Banana Island
            case 3:
                iv_island1.setVisibility(View.VISIBLE);
                jsonArrayIslanad1.put(jsonObject);
                break;
            //Bamboo Island
            case 4:
                iv_island3.setVisibility(View.VISIBLE);
                jsonArrayIslanad3.put(jsonObject);
                break;
            case 5:
                iv_pirate.setVisibility(View.VISIBLE);
                jsonArrayIslanad1.put(jsonObject);
                break;


        }
    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.imageViewisland1marker:
                setJsonValuesToArray(jsonArrayIslanad1);
                break;
            case R.id.imageViewisland2marker:
                setJsonValuesToArray(jsonArrayIslanad2);
                break;
            case R.id.imageViewisland3marker:
                setJsonValuesToArray(jsonArrayIslanad3);
                break;
            case R.id.imageViewisland4marker:
                setJsonValuesToArray(jsonArrayIslanad4);
                break;
            case R.id.imageViewpirateislandmarker:

                break;
        }

    }

    void replaceWithNewFragment(String user_id) {
        Fragment islandFragment = new ParkingPortFragment();
        Bundle bundle = new Bundle();
        bundle.putString("user_id", user_id);
        islandFragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(android.R.id.content, islandFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    void setJsonValuesToArray(JSONArray jsonArray) {
        ArrayList<ParkingUserListModel> parkingUserListArrayList = new ArrayList<>();
        int countuserList = jsonArray.length();
        for (int i = 0; i < countuserList; i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String userName = jsonObject.getString("username");
                String user_id = jsonObject.getString("user_id");

                ParkingUserListModel parkingUserListModel = new ParkingUserListModel(userName, user_id);
                parkingUserListArrayList.add(parkingUserListModel);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        showUserList(parkingUserListArrayList);

    }

    void showUserList(final ArrayList<ParkingUserListModel> parkingUserList) {
        ParkingUserListAdapter parkingUserListAdapter = new ParkingUserListAdapter(parkingUserList);
         final Dialog dialog = new Dialog(getActivity());
          dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
           dialog.setContentView(R.layout.recycler_view_user);
           RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.card_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(parkingUserListAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                ParkingUserListModel parkingUserListModel = parkingUserList.get(position);
                String user_id = parkingUserListModel.getUser_id();
                dialog.dismiss();
                replaceWithNewFragment(user_id);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        dialog.show();
    }
}