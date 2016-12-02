package com.silab.direct_me;

/**
 * Created by Lenovo on 09-Nov-16.
 */

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
    Controller controller;


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
        coinimg2=(ImageView)findViewById(R.id.coin1);
        park = (ImageView) findViewById(R.id.imageviewpark);
        parked = (ImageView) findViewById(R.id.imageviewparked);
        parking = (ImageView) findViewById(R.id.imageviewparking);
        garage = (ImageView) findViewById(R.id.imageviewgarage);
        showroom = (ImageView) findViewById(R.id.imageviewshowroom);
        dashboard = (ImageView) findViewById(R.id.imageviewdashboard);
        ObjectAnimator animation = ObjectAnimator.ofFloat(coinimg, "rotationY", 0.0f, 360f);
        animation.setDuration(3600);
        animation.setRepeatCount(ObjectAnimator.INFINITE);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.start();
        ObjectAnimator animation2 = ObjectAnimator.ofFloat(coinimg2, "rotationY", 0.0f, 360f);
        animation2.setDuration(3600);
        animation2.setRepeatCount(ObjectAnimator.INFINITE);
        animation2.setInterpolator(new AccelerateDecelerateInterpolator());
        animation2.start();

        park.setOnClickListener(this);
        parked.setOnClickListener(this);
        parking.setOnClickListener(this);
        garage.setOnClickListener(this);
        showroom.setOnClickListener(this);
        dashboard.setOnClickListener(this);
        try {
            controller.addObserver(this);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
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
            case R.id.imageviewdashboard:
                fr=new Dash();
                break;
        }
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
// Replace the contents of the container with the new fragment
        ft.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        ft.replace(R.id.fragment_place,fr);
// or ft.add(R.id.your_placeholder, new FooFragment());
// Complete the changes added above
        ft.commit();
    }
    @Override
    public void update(Observable observable, Object o) {
        bamboo.setText(controller.getBambooCount());
        coconut.setText(controller.getCoconutCount());
        banana.setText(controller.getBananaCount());
        timber.setText(controller.getTimberCount());
        gold_coin.setText(controller.getGoldCoinCount());
    }
}
