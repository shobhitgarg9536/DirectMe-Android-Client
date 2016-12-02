package com.silab.direct_me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Lenovo on 25-Nov-16.
 */

public class Parking extends Fragment implements View.OnClickListener{
    ImageView iv,iv1,ship;
    @Nullable
    public  int i=0;
    int a[]={R.drawable.ship2,R.drawable.ship};

    public View onCreateView(LayoutInflater inflater,

                             ViewGroup container, Bundle savedInstanceState) {



        View v= inflater.inflate(

                R.layout.ship, container, false);
        this.iv=(ImageView)v.findViewById(R.id.next);
        this.iv1=(ImageView)v.findViewById(R.id.prev);
        ship=(ImageView)v.findViewById(R.id.ship);
        this.iv.setOnClickListener(Parking.this);
        this.iv1.setOnClickListener(Parking.this);
        return v;
    }


    @Override
    public void onClick(View view) {



        switch (view.getId()){

            case R.id.prev:
                if (i == 0) {
                    i = 1;
                }
                i--;
                ship.setImageResource(a[i]);
                break;
            case R.id.next:

                if(i==1)
                {
                    i=0;
                }
                i++;
                ship.setImageResource(a[i]);

                break;


        }


    }
}
