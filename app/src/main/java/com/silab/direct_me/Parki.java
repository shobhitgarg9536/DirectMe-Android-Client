package com.silab.direct_me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Lenovo on 05-Nov-16.
 */
public class Parki extends AppCompatActivity {
    Fragment fr,fr1;



    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parking);

        fr=new Parking();
        fr1=new Island();
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
// Replace the contents of the container with the new fragment
        ft.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        ft.replace(R.id.one,fr);
        ft.replace(R.id.two,fr1);
// or ft.add(R.id.your_placeholder, new FooFragment());
// Complete the changes added above
        ft.commit();


    }
}








