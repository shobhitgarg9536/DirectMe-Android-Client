package in.silive.directme.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.provider.SyncStateContract;

import in.silive.directme.utils.Constants;

/**
 * Created by shobhit on 27/2/17.
 */

public class DirectMe extends Application {

    private static DirectMe singleton = null;
    public SharedPreferences sharedPrefs;
    public String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static DirectMe getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        singleton = this;
        super.onCreate();
        sharedPrefs = getSharedPreferences(Constants.SHARED_PREFS, Context.MODE_PRIVATE);
    }
}
