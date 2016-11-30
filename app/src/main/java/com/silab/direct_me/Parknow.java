package com.silab.direct_me;

/**
 * Created by Lenovo on 09-Nov-16.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Parknow extends AppCompatActivity implements View.OnClickListener {

    private ImageView island1,island2,island3,island4,pirate_Island;
    Toolbar tbPArkNow;
    LinearLayout llUserList;
    Button removeuserList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parknow);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        island1 = (ImageView) findViewById(R.id.imageViewisland1);
        island2 = (ImageView) findViewById(R.id.imageViewisland2);
        island3 = (ImageView) findViewById(R.id.imageViewisland3);
        island4 = (ImageView) findViewById(R.id.imageViewisland4);
        pirate_Island = (ImageView) findViewById(R.id.imageViewpirateisland);

        llUserList = (LinearLayout) findViewById(R.id.linearLayoutUserList);
        removeuserList = (Button) findViewById(R.id.buttonRemoveUserList);

        island1.setOnClickListener(this);
        island2.setOnClickListener(this);
        island3.setOnClickListener(this);
        island4.setOnClickListener(this);
        pirate_Island.setOnClickListener(this);
        removeuserList.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.imageViewisland1:
                //making User list visible
                llUserList.setVisibility(View.VISIBLE);
                break;
            case R.id.imageViewisland2:
                //making User list visible
                llUserList.setVisibility(View.VISIBLE);
                break;
            case R.id.imageViewisland3:
                //making User list visible
                llUserList.setVisibility(View.VISIBLE);
                break;
            case R.id.imageViewisland4:
                //making User list visible
                llUserList.setVisibility(View.VISIBLE);
                break;
            case R.id.buttonRemoveUserList:
                //making ISland view Visible
                llUserList.setVisibility(View.GONE);
                break;
            case R.id.imageViewpirateisland:
                //making ISland view Visible
                llUserList.setVisibility(View.VISIBLE);
                break;
        }
    }
}

