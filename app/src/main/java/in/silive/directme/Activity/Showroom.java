package in.silive.directme.Activity;

        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentManager;
        import android.support.v4.app.FragmentPagerAdapter;
        import android.support.v4.view.ViewPager;
        import android.support.v7.app.AppCompatActivity;

        import in.silive.directme.Fragments.Raft;
        import in.silive.directme.Fragments.Ship;
        import in.silive.directme.Fragments.Ship2;
        import in.silive.directme.R;

public class Showroom extends AppCompatActivity
{

    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showroom);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(new BoatPagerAdapter(
                getSupportFragmentManager()));
    }

    public class BoatPagerAdapter extends FragmentPagerAdapter
    {

        public BoatPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new Raft();
            }
            if (position == 1) {
                return new Ship2();
            } else
            {
                return new Ship();
            }
        }

        @Override
        public int getCount()
        {
            return 3;
        }
    }
}
