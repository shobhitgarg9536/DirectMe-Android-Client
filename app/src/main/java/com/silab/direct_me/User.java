package com.silab.direct_me;

/**
 * Created by Lenovo on 02-Jan-17.
 */

public class User {
    private String _id;
    private  String _Type;
    private String _boatpark;
    private  String _owner ;
    private  String _boatname;
    private  String _Time;
    public User()
    {

    }
    public User(String Type, String boatpark, String owner,String boatname,
                String Time){
        this._Type = Type;
        this._boatpark = boatpark;
        this._owner = owner;
        this._boatname=boatname;
        this._Time=Time;

    }
    public User(String id,String Type, String boatpark, String owner,String boatname,
                String Time){
        this._id=id;
        this._Type = Type;
        this._boatpark = boatpark;
        this._owner = owner;
        this._boatname=boatname;
        this._Time=Time;

    }

    public String get_Type() {
        return _Type;
    }

    public void set_Type(String _Type) {
        this._Type = _Type;
    }

    public String get_boatpark() {
        return _boatpark;
    }

    public void set_boatpark(String _boatpark) {
        this._boatpark = _boatpark;
    }

    public String get_owner() {
        return _owner;
    }

    public void set_owner(String _owner) {
        this._owner = _owner;
    }

    public String get_boatname() {
        return _boatname;
    }

    public void set_boatname(String _boatname) {
        this._boatname = _boatname;
    }

    public String get_Time() {
        return _Time;
    }

    public void set_Time(String _Time) {
        this._Time = _Time;
    }
}
