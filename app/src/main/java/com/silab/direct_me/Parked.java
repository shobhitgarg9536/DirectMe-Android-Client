package com.silab.direct_me;

/**
 * Created by Lenovo on 09-Nov-16.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;


public class Parked extends AppCompatActivity implements View.OnClickListener {

    Toolbar tbParked;
    RelativeLayout rl;
    Button parkedShipDetial1,parkedShipDetial2,parkedShipDetial3,parkedShipDetial4,parkedShipDetial5;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parked);
        parkedShipDetial1 = (Button)findViewById(R.id.buttonParkedShip1);
        parkedShipDetial2 = (Button)findViewById(R.id.buttonParkedShip2);
        parkedShipDetial3 = (Button)findViewById(R.id.buttonParkedShip3);
        parkedShipDetial4 = (Button)findViewById(R.id.buttonParkedShip4);
        parkedShipDetial5 = (Button)findViewById(R.id.buttonParkedShip5);
        rl=(RelativeLayout)findViewById(R.id.relat);
        rl.setVisibility(View.INVISIBLE);

        parkedShipDetial1.setOnClickListener(this);
        parkedShipDetial2.setOnClickListener(this);
        parkedShipDetial3.setOnClickListener(this);
        parkedShipDetial4.setOnClickListener(this);
        parkedShipDetial5.setOnClickListener(this);
    }





    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.buttonParkedShip1:
                rl.setVisibility(View.VISIBLE);
                break;
            case R.id.buttonParkedShip2:
                rl.setVisibility(View.VISIBLE);
                break;
            case R.id.buttonParkedShip3:
                rl.setVisibility(View.VISIBLE);
                break;
            case R.id.buttonParkedShip4:
                rl.setVisibility(View.VISIBLE);
                break;
            case R.id.buttonParkedShip5:
                rl.setVisibility(View.VISIBLE);
                break;
        }

    }
}

