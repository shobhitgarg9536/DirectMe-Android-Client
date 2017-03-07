package in.silive.directme.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.silive.directme.R;

/**
 * Created by Shobhit-pc on 3/7/2017.
 */

public class ParkingUserPortViewFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.parked , container ,false);
        String port_jsonObject = getArguments().getString("port_jsonObject");
        return view;
    }
}
