package in.silive.directme.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

import in.silive.directme.R;

/**
 * Created by Shobhit-pc on 3/14/2017.
 */

public class DockyardBuyShipFragment extends Fragment {

    Fragment1 fragment1;
    Fragment fragment2;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        try {
            JSONObject ship_detail_json_data = new JSONObject(getArguments().getString("json", ""));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        View view = inflater.inflate(R.layout.showroom_buy_fragment, container, false);

        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_1, fragment1);
        fragmentTransaction.add(R.id.fragment_2, fragment2);
        fragmentTransaction.commit();
        return view;
    }
}