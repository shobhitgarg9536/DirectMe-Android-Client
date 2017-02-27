package in.silive.directme.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import in.silive.directme.R;

public class ParkingWorldViewFragment extends Fragment{

    ImageView iv_island1,iv_island2,iv_island3,iv_island4;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.parknow, container,
                false);

        iv_island1 = (ImageView) view.findViewById(R.id.imageViewisland1);
        iv_island2 = (ImageView) view.findViewById(R.id.imageViewisland2);
        iv_island3 = (ImageView) view.findViewById(R.id.imageViewisland3);
        iv_island4 = (ImageView) view.findViewById(R.id.imageViewisland4);

        return view;
    }
}