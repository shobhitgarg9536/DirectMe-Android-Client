package com.silab.direct_me;

/**
 * Created by YESH AGNIHOTRI on 15-11-2016.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class Ship extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.ship_fragment, container,
                false);
        return rootView;
    }
    ImageView img;
    /*@Override
    public void onViewCreated(View view,Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        // get the button view
        img = (ImageView) getView().findViewById(R.id.buy1);
        // set a onclick listener for when the button gets clicked
        img.setOnClickListener(new View.OnClickListener() {
            // Start new list activity
            public void onClick(View v)
            {
                DFragment dFragment = new DFragment();
                // Show DialogFragment
                dFragment.show(getFragmentManager(), "Dialog Fragment");      // custom dialog

            }
        });

    }*/
}
