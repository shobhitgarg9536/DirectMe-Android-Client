package com.silab.direct_me;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import static android.content.Context.MODE_PRIVATE;
import static com.silab.direct_me.R.drawable.raft;
import static java.security.AccessController.getContext;

/**
 * Created by yesha on 02-12-2016.
 */

public class enter_db extends AppCompatActivity
{
    String field;
    EditText usrtext,idtxt,bananatxt,coconuttxt,timbertxt,bambootxt,goldtxt,garage1txt,garage2txt,garage3txt,garage4txt,garage5txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.db_entry);
        usrtext=(EditText)findViewById(R.id.username);
        idtxt=(EditText)findViewById(R.id.UserID);
        bananatxt=(EditText)findViewById(R.id.banana_count);
        coconuttxt=(EditText)findViewById(R.id.coconut_count);
        bambootxt=(EditText)findViewById(R.id.Bamboo_count);
        timbertxt=(EditText)findViewById(R.id.Timber_count);
        goldtxt=(EditText)findViewById(R.id.gold_count);
        garage1txt=(EditText)findViewById(R.id.garage_1);
        garage2txt=(EditText)findViewById(R.id.garage_2);
        garage3txt=(EditText)findViewById(R.id.garage_3);
        garage4txt=(EditText)findViewById(R.id.garage_4);
        garage5txt=(EditText)findViewById(R.id.garage_5);
        SharedPreferences pref =getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();
        if(pref.getString("Username",null)!=null)
        {
        field=pref.getString("Username",null);
            usrtext.setHint("Username"+field);
        }
        if(pref.getString("UserID",null)!=null)
        {
            field=pref.getString("UserID",null);
            idtxt.setHint("UserID: "+field);
        }
        if(pref.getString("Banana",null)!=null)
        {
            field=pref.getString("Banana",null);
            bananatxt.setHint("Banana: "+field);
        }
        if(pref.getString("Coconut",null)!=null)
        {
            field=pref.getString("Coconut",null);
            coconuttxt.setHint("Cococnut: "+field);
        }
        if(pref.getString("Timber",null)!=null)
        {
            field=pref.getString("Timber",null);
            timbertxt.setHint("Timber: "+field);
        }
        if(pref.getString("Bamboo",null)!=null)
        {
            field=pref.getString("Bamboo",null);
            bambootxt.setHint("Bamboo"+field);
        }
        if(pref.getString("Gold",null)!=null)
        {
            field=pref.getString("Gold",null);
            goldtxt.setHint("Gold: "+field);
        }
        if(pref.getString("Garage1",null)!=null)
        {
            field=pref.getString("Garage1",null);
            garage1txt.setHint("Garage1 :"+field);
        }
        if(pref.getString("Garage2",null)!=null)
        {
            field=pref.getString("Garage2",null);
            garage2txt.setHint("Garage2 :"+field);
        }
        if(pref.getString("Garage3",null)!=null)
        {
            field=pref.getString("Garage3",null);
            garage3txt.setHint("Garage3 :"+field);
        }
        if(pref.getString("Garage4",null)!=null)
        {
            field=pref.getString("Garage4",null);
            garage4txt.setHint("Garage4 :"+field);
        }
        if(pref.getString("Garage5",null)!=null)
        {
            field=pref.getString("Garage5",null);
            garage5txt.setHint("Garage5 :"+field);
        }

        //not saving again as editor again putting values

        ImageButton ok=(ImageButton)findViewById(R.id.okcheck);
        ok.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                editor.putString("Username",usrtext.getText().toString()).apply();
                editor.putString("UserID",idtxt.getText().toString()).apply();
                editor.putString("Banana",bananatxt.getText().toString()).apply();
                editor.putString("Coconut",coconuttxt.getText().toString()).apply();
                editor.putString("Timber",timbertxt.getText().toString()).apply();
                editor.putString("Bamboo",bambootxt.getText().toString()).apply();
                editor.putString("Gold",goldtxt.getText().toString()).apply();
                editor.putString("Garage1",garage1txt.getText().toString()).apply();
                editor.putString("Garage2",garage2txt.getText().toString()).apply();
                editor.putString("Garage3",garage3txt.getText().toString()).apply();
                editor.putString("Garage4",garage4txt.getText().toString()).apply();
                editor.putString("Garage5",garage5txt.getText().toString()).apply();
                editor.apply();
                Intent i=new Intent(getApplicationContext(),Garage.class);
                startActivity(i);
            }
        });
    }
}

