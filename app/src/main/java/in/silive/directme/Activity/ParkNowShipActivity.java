package in.silive.directme.Activity;

/**
 * Created by simran on 2/24/2017.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import in.silive.directme.Fragments.ParknowUsershipselectFragment;
import in.silive.directme.R;

/**
 * Created by simran on 2/23/2017.
 */

public class ParkNowShipActivity extends AppCompatActivity {
    ViewPager mViewPager;
    int count=1;
  ImageView left,right;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage_viewpager);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(new BoatPagerAdapter1(
                getSupportFragmentManager()));
        mViewPager.setOffscreenPageLimit(0);
        left=(ImageView)findViewById(R.id.left_navigation);
        right=(ImageView)findViewById(R.id.right_navigation);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.arrowScroll(View.FOCUS_LEFT);
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.arrowScroll(View.FOCUS_RIGHT);
            }
        });

    }
   Bundle arguments;
    String spritesheetship1="ship1spritesheet.png";
    String spritesheetship2="spritesheetship.png";
    int Frame_Width_ship1=720;
    int Frame_Width_ship2=637;
    public class BoatPagerAdapter1 extends FragmentPagerAdapter {

        public BoatPagerAdapter1(FragmentManager fm) {
            super(fm);

        }
        ParknowUsershipselectFragment ships_fragment;
        @Override
        public Fragment getItem(int position) {
            switch (position) {

                case 0: ships_fragment = new ParknowUsershipselectFragment();
                 arguments= new Bundle();
                arguments.putString("name", spritesheetship1);
                    arguments.putInt("Frame_width",Frame_Width_ship1);
                ships_fragment.setArguments(arguments);
                break;
                case 1: ships_fragment = new ParknowUsershipselectFragment();
                     arguments = new Bundle();
                    arguments.putString("name", spritesheetship2);
                    arguments.putInt("Frame_width",Frame_Width_ship2);
                    ships_fragment.setArguments(arguments);

            }
            return ships_fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }


}
