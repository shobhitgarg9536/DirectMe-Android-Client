package com.silab.direct_me;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * Created by yesha on 02-12-2016.
 */

public class enter_boat extends AppCompatActivity {

    String field;
    EditText speed[]=new EditText[5];
    EditText banana[]=new EditText[5];
    EditText gold[]=new EditText[5];
    EditText bamboo[]=new EditText[5];
    EditText timber[]=new EditText[5];
    EditText coconut[]=new EditText[5];
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.boat_entries);
        speed[0]=(EditText)findViewById(R.id.rft_speed);
        speed[1]=(EditText)findViewById(R.id.boat_speed);
        speed[2]=(EditText)findViewById(R.id.ship3_speed);
        speed[3]=(EditText)findViewById(R.id.ship2_speed);
        speed[4]=(EditText)findViewById(R.id.ship1_speed);

        banana[0]=(EditText)findViewById(R.id.rft_ban_need);
        banana[1]=(EditText)findViewById(R.id.boat_ban_need);
        banana[2]=(EditText)findViewById(R.id.ship3_ban_need);
        banana[3]=(EditText)findViewById(R.id.ship2_ban_need);
        banana[4]=(EditText)findViewById(R.id.ship1_ban_need);

        bamboo[0]=(EditText)findViewById(R.id.rft_bamb_need);
        bamboo[1]=(EditText)findViewById(R.id.boat_bamb_need);
        bamboo[2]=(EditText)findViewById(R.id.ship3_bamb_need);
        bamboo[3]=(EditText)findViewById(R.id.ship2_bamb_need);
        bamboo[4]=(EditText)findViewById(R.id.ship1_bamb_need);

        gold[0]=(EditText)findViewById(R.id.rft_gold_need);
        gold[1]=(EditText)findViewById(R.id.boat_gold_need);
        gold[2]=(EditText)findViewById(R.id.ship3_gold_need);
        gold[3]=(EditText)findViewById(R.id.ship2_gold_need);
        gold[4]=(EditText)findViewById(R.id.ship1_gold_need);

        timber[0]=(EditText)findViewById(R.id.rft_wood_need);
        timber[1]=(EditText)findViewById(R.id.boat_wood_need);
        timber[2]=(EditText)findViewById(R.id.ship3_wood_need);
        timber[3]=(EditText)findViewById(R.id.ship2_wood_need);
        timber[4]=(EditText)findViewById(R.id.ship1_wood_need);

        coconut[0]=(EditText)findViewById(R.id.rft_coco_need);
        coconut[1]=(EditText)findViewById(R.id.boat_coco_need);
        coconut[2]=(EditText)findViewById(R.id.ship3_coco_need);
        coconut[3]=(EditText)findViewById(R.id.ship2_coco_need);
        coconut[4]=(EditText)findViewById(R.id.ship1_coco_need);
        SharedPreferences pref =getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();
        final String boatname[]={"Raft","Boat","Ship3","Ship2","Ship"};
       
                for(int i=0;i<5;i++)
                {
                    if(pref.getString(boatname[i]+"Speed",null)!=null)
                    {
                        field=pref.getString(boatname[i]+"Speed",null);
                        speed[i].setHint("Speed: "+field);
                    }
                    if(pref.getString(boatname[i]+"Banana",null)!=null)
                    {
                        field=pref.getString(boatname[i]+"Banana",null);
                        banana[i].setHint("Banana: "+field);
                    }
                    if(pref.getString(boatname[i]+"Bamboo",null)!=null)
                    {
                        field=pref.getString(boatname[i]+"Bamboo",null);
                        bamboo[i].setHint("Bamboo: "+field);
                    }
                    if(pref.getString(boatname[i]+"Coconut",null)!=null)
                    {
                        field=pref.getString(boatname[i]+"Coconut",null);
                        coconut[i].setHint("Coconut: "+field);
                    }
                    if(pref.getString(boatname[i]+"Timber",null)!=null)
                    {
                        field=pref.getString(boatname[i]+"Timber",null);
                        timber[i].setHint("Timber: "+field);
                    }

                    if(pref.getString(boatname[i]+"Gold",null)!=null)
                    {
                        field=pref.getString(boatname[i]+"Gold",null);
                        gold[i].setHint("Gold: "+field);
                    }
                }

        Button ok=(Button)findViewById(R.id.ok_boat);
        ok.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                for(int i=0;i<5;i++)
                {
                    if(speed[i].getText().toString()!=null)
                    editor.putString(boatname[i]+"Speed",speed[i].getText().toString());
                    if(banana[i].getText().toString()!=null)
                    editor.putString(boatname[i]+"Banana",banana[i].getText().toString());
                    if(bamboo[i].getText().toString()!=null)
                    editor.putString(boatname[i]+"Bamboo",bamboo[i].getText().toString());
                    if(timber[i].getText().toString()!=null)
                    editor.putString(boatname[i]+"Timber",timber[i].getText().toString());
                    if(coconut[i].getText().toString()!=null)
                    editor.putString(boatname[i]+"Coconut",coconut[i].getText().toString());
                    if(gold[i].getText().toString()!=null)
                    editor.putString(boatname[i]+"Gold",gold[i].getText().toString());
                }
                editor.apply();
                Intent i=new Intent(getApplicationContext(),Garage.class);
                startActivity(i);
            }
        });
    }

    }
