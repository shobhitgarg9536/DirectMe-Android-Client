package com.silab.direct_me;


import android.content.SharedPreferences;
import java.util.Observable;


public class Controller extends Observable {

    private int bananaCount = 0;
    private int timberCount = 0;
    private int coconutCount = 0;
    private int bambooCount = 0;
    private int goldCoinCount = 0;
    SharedPreferences sharedPreferences;
    private String Commodity = "";


    public String getCommodity() {
        return Commodity;
    }

    public void setCommodity(String commodity) {
        Commodity = commodity;
        BackgroundWorker backgroundWorker = new BackgroundWorker();
        backgroundWorker.execute(Commodity , Config.COMODITY_URL);
        //whatever values comes in save it in sharedPreferences , values may come in JSON
        setChanged();
        notifyObservers();
    }
}
