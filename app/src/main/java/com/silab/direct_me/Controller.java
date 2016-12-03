package com.silab.direct_me;


import android.content.SharedPreferences;

import java.util.*;
import java.util.Observer;


public class Controller extends Observable {

    private int bananaCount = 0;
    private int timberCount = 0;
    private int coconutCount = 0;
    private int bambooCount = 0;
    private int goldCoinCount = 0;
    SharedPreferences sharedPreferences;
    private String Commodity = "";

    public Controller(){

    }


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

    public int getBambooCount() {
        return bambooCount;
    }

    public int getBananaCount() {

        return bananaCount;
    }

    public int getCoconutCount() {
        return coconutCount;
    }

    public int getGoldCoinCount() {
        return goldCoinCount;
    }

    public int getTimberCount() {
        return timberCount;
    }

    public void setBambooCount(int bambooCount) {
        this.bambooCount = bambooCount;
        setChanged();
        notifyObservers();
    }

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }

    public void setBananaCount(int bananaCount) {

        this.bananaCount = bananaCount;
        setChanged();
        notifyObservers();
    }

    public void setCoconutCount(int coconutCount) {
        this.coconutCount = coconutCount;
        setChanged();
        notifyObservers();
    }

    public void setGoldCoinCount(int goldCoinCount) {
        this.goldCoinCount = goldCoinCount;
        setChanged();
        notifyObservers();
    }

    public void setTimberCount(int timberCount) {
        this.timberCount = timberCount;
        setChanged();
        notifyObservers();
    }
}
