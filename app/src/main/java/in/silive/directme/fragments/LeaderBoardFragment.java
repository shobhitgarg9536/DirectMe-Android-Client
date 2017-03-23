package in.silive.directme.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.silive.directme.R;
import in.silive.directme.adapter.LeaderBoardAdapter;
import in.silive.directme.application.DirectMe;
import in.silive.directme.listeners.FetchDataListener;
import in.silive.directme.model.LeaderBoardObject;
import in.silive.directme.network.FetchData;
import in.silive.directme.utils.API_URL_LIST;
import in.silive.directme.utils.Constants;
import in.silive.directme.utils.NetworkUtils;

import static in.silive.directme.utils.Keys.island_id;

/**
 * Created by Shobhit-pc on 3/23/2017.
 */

public class LeaderBoardFragment extends Fragment {

    RecyclerView rv_user_list;
    ArrayList<LeaderBoardObject> leaderBoardUserList;
    JSONArray userList_jsonArray;
    int count;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.leaderboard, container, false);
        rv_user_list = (RecyclerView) view.findViewById(R.id.recycler_view_leader_board);
        apiCalling();
        return view;
    }



    void apiCalling() {
        SharedPreferences sharedPreferences = DirectMe.getInstance().sharedPrefs;
        String token = sharedPreferences.getString(Constants.AUTH_TOKEN, "");
        boolean network_available = NetworkUtils.isNetConnected();
        if (network_available) {
            FetchData fetchData = new FetchData(new FetchDataListener() {
                @Override
                public void processStart() {

                }

                @Override
                public void processFinish(String output) {
                    try {
                        userList_jsonArray = new JSONArray(output);
                        setJsonValuesToArray(userList_jsonArray);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            fetchData.setArgs(API_URL_LIST.LEADERBOARD_URL, token,"");
            fetchData.execute();

        }
    }


    void setJsonValuesToArray(JSONArray jsonArray) {
        ArrayList<LeaderBoardObject> leaderUserListArrayList = new ArrayList<>();
        int countuserList = jsonArray.length();
        for (int i = 0; i < countuserList; i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String user_name = jsonObject.getString("username");
                String user_icon = jsonObject.getString("gravatar");
                String user_rank = jsonObject.getString("rank");

                LeaderBoardObject leaderBoardObject = new LeaderBoardObject(user_name, user_icon, user_rank);
                leaderUserListArrayList.add(leaderBoardObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        showUserList(leaderUserListArrayList);

    }

    void showUserList(final ArrayList<LeaderBoardObject> leaderUserList) {
        LeaderBoardAdapter leaderBoardAdapter = new LeaderBoardAdapter(leaderUserList , getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rv_user_list.setLayoutManager(mLayoutManager);
        rv_user_list.setItemAnimator(new DefaultItemAnimator());
        rv_user_list.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        rv_user_list.setAdapter(leaderBoardAdapter);
    }
}
