package in.silive.directme.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import in.silive.directme.R;
import in.silive.directme.activity.ParkNowActivity;
import in.silive.directme.adapter.DataUserSelectAdapter;
import in.silive.directme.application.DirectMe;
import in.silive.directme.dialog.AlertDialog;
import in.silive.directme.listeners.FetchDataListener;
import in.silive.directme.model.UserDetailsList;
import in.silive.directme.network.FetchData;
import in.silive.directme.utils.API_URL_LIST;
import in.silive.directme.utils.Constants;
import in.silive.directme.utils.NetworkUtils;


/**
 * Created by simran on 2/24/2017.
 */


public class UserDetailsFragment extends Fragment implements View.OnClickListener {
    public String user_names[];
    public String user_id[];
    RecyclerView recyclerView;
    ImageView refresh;
    SharedPreferences sharedPreferences;
    FetchData apicalling;
    RecyclerView.LayoutManager layoutManager;
    JSONArray arr;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.recycler_view_userselect, container,
                false);
        sharedPreferences = DirectMe.getInstance().sharedPrefs;
        recyclerView = (RecyclerView) v.findViewById(R.id.card_recycler_view);
        refresh = (ImageView) v.findViewById(R.id.refresh);
        refresh.setVisibility(View.INVISIBLE);
        refresh.setOnClickListener(this);
        connect();
        return v;
    }
    private void initViews() {
        refresh.setVisibility(View.VISIBLE);

        recyclerView.setHasFixedSize(true);
        if (arr.length() < 5) {
            layoutManager = new GridLayoutManager(getActivity(), arr.length());
        } else if (arr.length() >= 5) {
            layoutManager = new GridLayoutManager(getActivity(), 5);
        }
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<UserDetailsList> user_details = prepareData();
        DataUserSelectAdapter adapter = new DataUserSelectAdapter(getActivity(), user_details, (ParkNowActivity) getContext());
        recyclerView.setAdapter(adapter);
    }
    void connect() {
        boolean network_available;
        final String token = sharedPreferences.getString(Constants.AUTH_TOKEN, "");
        final String id = sharedPreferences.getString(Constants.ISLAND_ID, "");
        network_available = NetworkUtils.isNetConnected();
        if (network_available) {
            apicalling = new FetchData(new FetchDataListener() {
                @Override
                public void processStart() {

                }
                @Override
                public void processFinish(String output) {
                    try {
                        // Extract JSON array from the response
                        arr = new JSONArray(output);
                        user_names = new String[arr.length()];
                        user_id = new String[arr.length()];
                        // If no of array elements is not zero
                        if (arr.length() != 0) {
                            // Loop through each array element, get JSON object which has userid and username
                            for (int i = 0; i < arr.length(); i++) {
                                // Get JSON object
                                JSONObject obj = (JSONObject) arr.get(i);
                                user_names[i] = obj.get("name").toString();
                                user_id[i] = obj.get("user_id").toString();
                                initViews();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            String post_data = "";
            try {
                post_data = URLEncoder.encode("island_id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            apicalling.setArgs(API_URL_LIST.GET_USER_LIST, token, post_data);
            apicalling.execute();

        }else{
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.alertDialog(getContext());
        }
    }
    private ArrayList<UserDetailsList> prepareData() {
        ArrayList<UserDetailsList> user_details = new ArrayList<>();
        for (int i = 0; i < user_names.length; i++) {
            UserDetailsList userDetailsList = new UserDetailsList();
            userDetailsList.setUser_name(user_names[i]);
            userDetailsList.setUser_id(user_id[i]);
            //// TODO: 3/4/2017 add image url
            userDetailsList.setUser_image_url("simran");
            user_details.add(userDetailsList);
        }
        return user_details;
    }
    @Override
    public void onClick(View v) {
        connect();
    }
}
