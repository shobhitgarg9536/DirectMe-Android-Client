package in.silive.directme;


import java.util.Observable;
import java.util.Observer;


public class Controller extends Observable {

    private int bananaCount = 0;
    private int timberCount = 0;
    private int coconutCount = 0;
    private int bambooCount = 0;
    private int goldCoinCount = 0;
    private String Commodity = "";

    public Controller() {

    }


    public String getCommodity() {
        return Commodity;
    }

    public void setCommodity(String commodity) {
        Commodity = commodity;

        //whatever values comes in save it in sharedPreferences , values may come in JSON
        setChanged();
        notifyObservers();
    }

    public int getBambooCount() {
        return bambooCount;
    }

    public void setBambooCount(int bambooCount) {
        this.bambooCount = bambooCount;
        setChanged();
        notifyObservers();
    }

    public int getBananaCount() {

        return bananaCount;
    }

    public void setBananaCount(int bananaCount) {

        this.bananaCount = bananaCount;
        setChanged();
        notifyObservers();
    }

    public int getCoconutCount() {
        return coconutCount;
    }

    public void setCoconutCount(int coconutCount) {
        this.coconutCount = coconutCount;
        setChanged();
        notifyObservers();
    }

    public int getGoldCoinCount() {
        return goldCoinCount;
    }

    public void setGoldCoinCount(int goldCoinCount) {
        this.goldCoinCount = goldCoinCount;
        setChanged();
        notifyObservers();
    }

    public int getTimberCount() {
        return timberCount;
    }

    public void setTimberCount(int timberCount) {
        this.timberCount = timberCount;
        setChanged();
        notifyObservers();
    }

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }
}
