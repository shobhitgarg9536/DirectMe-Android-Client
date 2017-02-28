package in.silive.directme.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by shobhit on 27/2/17.
 */

public class DirectMe extends Application {

    private static DirectMe singleton = null;
    public Context mContext;

    public static DirectMe getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        singleton = this;
        super.onCreate();
    }
}
