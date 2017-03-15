package in.silive.directme.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.provider.SyncStateContract;

import in.silive.directme.activity.DashboardActivity;
import in.silive.directme.utils.Constants;
import in.silive.directme.utils.Keys;

/**
 * Created by shobhit on 27/2/17.
 */

public class DirectMe extends Application {

    private static DirectMe singleton = null;
    public SharedPreferences sharedPrefs;
    public String token;
    String commodity[]=new String[10];
    public String coconutCount;

    public String getCoinCount() {
        return this.coinCount;
    }

    public String getTimberCount() {

        return this.timberCount;
    }

    public String getBananaCount() {

        return this.bananaCount;
    }

    public String getCoconutCount() {

        return this.coconutCount;
    }

    public String bananaCount;
    public String timberCount;
    public String coinCount;
    public String bambooCount;

    public String getBambooCount() {
        return this.bambooCount;
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

                this.commodity[i] = sharedPrefs.getString(Keys.co[i],"");

        }
        this.bambooCount=this.commodity[1];

        this.bananaCount=this.commodity[2];
        this.coinCount=this.commodity[0];
        this.timberCount=this.commodity[3];
        this.coconutCount=this.commodity[4];

    }

    public void setBambooCount(String bambooCount) {
        this.bambooCount = bambooCount;

    }
}
