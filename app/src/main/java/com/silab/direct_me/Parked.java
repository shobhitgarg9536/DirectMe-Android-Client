package com.silab.direct_me;

/**
 * Created by Lenovo on 09-Nov-16.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Parked extends Fragment implements View.OnClickListener {

    Toolbar tbParked;
    Button parkedShipDetial1,parkedShipDetial2,parkedShipDetial3,parkedShipDetial4,parkedShipDetial5;
    public View onCreateView(LayoutInflater inflater,

                             ViewGroup container, Bundle savedInstanceState) {



        View v= inflater.inflate(

                R.layout.parked, container, false);

        parkedShipDetial1 = (Button)v.findViewById(R.id.buttonParkedShip1);
        parkedShipDetial2 = (Button)v.findViewById(R.id.buttonParkedShip2);
        parkedShipDetial3 = (Button)v.findViewById(R.id.buttonParkedShip3);
        parkedShipDetial4 = (Button)v.findViewById(R.id.buttonParkedShip4);
        parkedShipDetial5 = (Button)v.findViewById(R.id.buttonParkedShip5);

        parkedShipDetial1.setOnClickListener(this);
        parkedShipDetial2.setOnClickListener(this);
        parkedShipDetial3.setOnClickListener(this);
        parkedShipDetial4.setOnClickListener(this);
        parkedShipDetial5.setOnClickListener(this);
        return v;
    }



    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.buttonParkedShip1:
                break;
            case R.id.buttonParkedShip2:
                break;
            case R.id.buttonParkedShip3:
                break;
            case R.id.buttonParkedShip4:
                break;
            case R.id.buttonParkedShip5:
                break;
        }

    }
}

