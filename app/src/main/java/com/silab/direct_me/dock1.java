package com.silab.direct_me;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by YESH AGNIHOTRI on 19-11-2016.
 */

public class dock1 extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dock1, container,
                false);

        return rootView;

    }

    ImageView img;
    @Override
    public void onViewCreated(View view,Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        // get the button view
        img = (ImageView) getView().findViewById(R.id.upgrade);
        // set a onclick listener for when the button gets clicked
        img.setOnClickListener(new View.OnClickListener() {
            // Start new list activity
            public void onClick(View v)
            {
                Intent i=new Intent(getContext(),Showroom.class);
                startActivity(i);
            }
        });

    }

}
