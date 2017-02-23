package in.silive.directme.Database;

/**
 * Created by Lenovo on 28-Dec-16.
 */

public class ShipModel {

    //private variables
    private String _id;
    private String _api_id;
    private String _name;
    private String _Cost_multiplier, _filling_speed, _Parking_time, _Boat_filled, _coins_earn, _user_name, _island;

    public ShipModel() {

    }


    public ShipModel(String id, String name, String api_id, String cost_multiplier,
                     String filling_speed, String Parking_time, String Boat_filled, String coins_earn, String user_name, String island) {
        this._id = id;
        this._name = name;
        this._api_id = api_id;
        this._Cost_multiplier = cost_multiplier;
        this._filling_speed = filling_speed;
        this._Parking_time = Parking_time;
        this._Boat_filled = Boat_filled;
        this._coins_earn = coins_earn;
        this._user_name = user_name;
        this._island = island;
    }

    public ShipModel(String name, String api_id, String cost_multiplier,
                     String filling_speed, String Parking_time, String Boat_filled, String coins_earn, String user_name, String island) {
        this._name = name;
        this._api_id = api_id;
        this._Cost_multiplier = cost_multiplier;
        this._filling_speed = filling_speed;
        this._Parking_time = Parking_time;
        this._Boat_filled = Boat_filled;
        this._coins_earn = coins_earn;
        this._user_name = user_name;
        this._island = island;
    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_Cost_multiplier() {
        return _Cost_multiplier;
    }

    public void set_Cost_multiplier(String cost_multiplier) {
        this._Cost_multiplier = cost_multiplier;
    }

    public String get_filling_speed() {
        return _filling_speed;
    }

    public void set_filling_speed(String _filling_speed) {
        this._filling_speed = _filling_speed;
    }

    public String get_api_id() {
        return _api_id;
    }

    public void set_api_id(String _api_id) {
        this._api_id = _api_id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_Parking_time() {
        return _Parking_time;
    }

    public void set_Parking_time(String _Parking_time) {
        this._Parking_time = _Parking_time;
    }

    public String get_Boat_filled() {
        return _Boat_filled;
    }

    public void set_Boat_filled(String _Boat_filled) {
        this._Boat_filled = _Boat_filled;
    }

    public String get_coins_earn() {
        return _coins_earn;
    }

    public void set_coins_earn(String _coins_earn) {
        this._coins_earn = _coins_earn;
    }

    public String get_user_name() {
        return _user_name;
    }

    public void set_user_name(String _user_name) {
        this._user_name = _user_name;
    }

    public String get_island() {
        return _island;
    }

    public void set_island(String _island) {
        this._island = _island;
    }
}
