package in.silive.directme.utils;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by Shobhit-pc on 3/4/2017.
 */

public class ViewPagerAnimation implements ViewPager.PageTransformer {
    public void transformPage(View view, float position) {
        if (position <= -1.0F || position >= 1.0F) {
            view.setTranslationX(view.getWidth() * position);
            view.setVisibility(View.GONE);
            view.setAlpha(0.0F);
        } else if (position == 0.0F) {
            view.setTranslationX(view.getWidth() * position);
            view.setAlpha(1.0F);
            view.setVisibility(View.VISIBLE);
        } else {
            // position is between -1.0F & 0.0F OR 0.0F & 1.0F
            view.setTranslationX(view.getWidth() * -position);
            view.setAlpha(1.0F - Math.abs(position));
        }
    }
}