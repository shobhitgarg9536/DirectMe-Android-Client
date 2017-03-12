package in.silive.directme.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.provider.SyncStateContract;

import in.silive.directme.activity.DashboardActivity;
import in.silive.directme.utils.Constants;

/**
 * Created by shobhit on 27/2/17.
 */

public class DirectMe extends Application {

    private static DirectMe singleton = null;
    public SharedPreferences sharedPrefs;
    public String token;
    int commodity[]=new int[10];
    public String coconutCount;

    public String getCoinCount() {
        return coinCount;
    }

    public String getTimberCount() {

        return timberCount;
    }

    public String getBananaCount() {

        return bananaCount;
    }

    public String getCoconutCount() {

        return coconutCount;
    }

    public String bananaCount;
    public String timberCount;
    public String coinCount;
    public String bambooCount;

    public String getBambooCount() {
        return bambooCount;
    }

    public String getToken() {
        return token;
    }



    public static DirectMe getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        singleton = this;
        super.onCreate();
        sharedPrefs = getSharedPreferences(Constants.SHARED_PREFS, Context.MODE_PRIVATE);
        for (int i = 0; i < 5; i++) {
            if (sharedPrefs.contains(DashboardActivity.co[i])) {
                this.commodity[i] = Integer.parseInt(sharedPrefs.getString(DashboardActivity.co[i], ""));
            }
        }
        this.bambooCount=Integer.toString(this.commodity[3]);
        this.bananaCount=Integer.toString(this.commodity[2]);
        this.coinCount=Integer.toString(this.commodity[4]);
        this.timberCount=Integer.toString(this.commodity[1]);
        this.coconutCount=Integer.toString(this.commodity[0]);

    }

    public void setBambooCount(String bambooCount) {
        this.bambooCount = bambooCount;

    }
}
