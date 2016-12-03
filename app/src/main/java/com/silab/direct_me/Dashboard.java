package com.silab.direct_me;


import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.*;

public class Dashboard extends AppCompatActivity implements View.OnClickListener, java.util.Observer {

    private ImageView park,parked,parking,garage,showroom,coinimg,coinimg2,dashboard;
    private TextView bamboo,coconut,banana,timber,gold_coin,log_out;
    Controller controller = new Controller();


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);



        bamboo = (TextView) findViewById(R.id.textviewbamboo);
        coconut = (TextView) findViewById(R.id.textviewcoconut);
        banana = (TextView) findViewById(R.id.textviewbanana);
        timber = (TextView) findViewById(R.id.textviewtimber);
        gold_coin = (TextView) findViewById(R.id.textviewgoldCoin);
        log_out = (TextView) findViewById(R.id.textviewgoldCoin);
        coinimg=(ImageView)findViewById(R.id.coin);

        park = (ImageView) findViewById(R.id.imageviewpark);
        parked = (ImageView) findViewById(R.id.imageviewparked);
        parking = (ImageView) findViewById(R.id.imageviewparking);
        garage = (ImageView) findViewById(R.id.imageviewgarage);
        showroom = (ImageView) findViewById(R.id.imageviewshowroom);



        park.setOnClickListener(this);
        parked.setOnClickListener(this);
        parking.setOnClickListener(this);
        garage.setOnClickListener(this);
        showroom.setOnClickListener(this);


            controller.addObserver(Dashboard.this);
        controller.setBambooCount(100);
        controller.setBananaCount(50);
        controller.setTimberCount(40);
        controller.setCoconutCount(150);
        controller.setGoldCoinCount(10);
    }

    @Override
    public void onClick(View view) {
        Fragment fr;

        fr=new Dash();

        switch (view.getId()){
            case R.id.imageviewpark:
                Intent intent = new Intent(this , Parknow.class);
                startActivity(intent);
                break;
            case R.id.imageviewparked:
                Intent intent4=new Intent(this,Parked.class);
                startActivity(intent4);
                break;
            case R.id.imageviewparking:
                Intent intent1=new Intent(this,Parkinge.class);
                startActivity(intent1);
                break;
            case R.id.imageviewshowroom:
                Intent i = new Intent(Dashboard.this , Showroom.class);
                startActivity(i);
                break;
            case R.id.imageviewgarage:
                Intent in = new Intent(Dashboard.this , Garage.class);
                startActivity(in);
                break;

        }

    }
    @Override
    public void update(Observable observable, Object o) {
        controller = (Controller) observable;
        System.out.println(controller.getBananaCount());
        bamboo.setText(Integer.toString(controller.getBambooCount()));
        coconut.setText(Integer.toString(controller.getCoconutCount()));
        banana.setText(Integer.toString(controller.getBananaCount()));
        timber.setText(Integer.toString(controller.getTimberCount()));
        gold_coin.setText(Integer.toString(controller.getGoldCoinCount()));

    }
}
