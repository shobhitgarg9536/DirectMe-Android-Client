package in.silive.directme.Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Direct_me";

    // SHIP table name
    private static final String TABLE_SHIP = "ship";

    // SHIP Table Columns names
    private static final String KEY_ID = "id";
    private static final String Api_id = "_api_id";
    private static final String Name = "_name";
    private static final String Filling_speed= "_filling_speed";
    private static final String Parking_time = "_Parking_time";
    private static final String Boat_filled = "_Boat_filled";
    private static final String Coin_earn = "_coins_earn";
    private static final String User_name = "_user_name";
    private static final String island = "_island";
    private static final String Cost_multiplier= "_Cost_multiplier";
    private static final String Table_User="user";
    private static final String Key_user="id";
    private static final String Type = "type";
    private static final String boatpark= "boatpark";
    private static final String owner = "owner";
    private static final String boatname= "boatname";
    private static final String Time="time";



    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_SHIP + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + Api_id + " TEXT,"+ Name + " TEXT,"+ Filling_speed + " TEXT,"+ Parking_time + " TEXT,"
                + Boat_filled + " TEXT,"+ Coin_earn + " TEXT,"+ User_name + " TEXT,"+ island + " TEXT,"
                + Cost_multiplier+ " TEXT" + ")";
        String CREATE_CONTACTS_Users = "CREATE TABLE " + Table_User + "("
                + Key_user + " INTEGER PRIMARY KEY," + Type + " TEXT,"+ boatpark + " TEXT,"+ owner + " TEXT,"+ boatname+ " TEXT,"
                + Time+ " TEXT" + ")";

        db.execSQL(CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_CONTACTS_Users);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHIP);
        db.execSQL("Drop table if exsist"+Table_User);
        // Create tables again
        onCreate(db);
        onCreate(db);
    }
    // Adding new ship
    public void addShip(Ships_DB_Objects ship) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Api_id, ship.get_api_id()); // Contact Name
        values.put(Name,ship.get_name());
        values.put(Filling_speed,ship.get_filling_speed());
        values.put(Parking_time,ship.get_Parking_time());
        values.put(Boat_filled,ship.get_Boat_filled());
        values.put(Coin_earn,ship.get_coins_earn());
        values.put(User_name,ship.get_user_name());
        values.put(island,ship.get_island());
        values.put(Cost_multiplier,ship.get_Cost_multiplier());

        // Inserting Row
        db.insert(TABLE_SHIP, null, values);
        db.close(); // Closing database connection
    }
    public void addPort(User_DB_Objects user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Type, user.get_Type()); // Contact Name
        values.put(boatpark,user.get_boatpark());
        values.put(owner,user.get_owner());
        values.put(boatname,user.get_boatname());

        values.put(Time,user.get_Time());


        // Inserting Row
        db.insert(Table_User, null, values);
        db.close(); // Closing database connection
    }

    // Getting single detail of ship
    public Ships_DB_Objects getShip(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SHIP, new String[] { KEY_ID, Api_id,Name,Filling_speed,
                        Parking_time,Boat_filled,Coin_earn,User_name,island,Cost_multiplier }, KEY_ID + "=?",
                new String[] { id }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();




        Ships_DB_Objects ship= new Ships_DB_Objects(cursor.getString(0),
                cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),
                cursor.getString(7),cursor.getString(8),cursor.getString(9));
        // return ship
        return ship;
    }
    public User_DB_Objects getUser(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Table_User, new String[] { Key_user, boatname,boatpark,Type,
                        owner,Time}, KEY_ID + "=?",
                new String[] { id }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();




        User_DB_Objects user= new User_DB_Objects(cursor.getString(0),
                cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4));
        // return user
        return user;
    }


    // Getting All ship
    public List<Ships_DB_Objects> getAllShip() {
        List<Ships_DB_Objects> shipList = new ArrayList<Ships_DB_Objects>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SHIP;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Ships_DB_Objects ship = new Ships_DB_Objects();
                ship.set_id(cursor.getString(0));
                ship.set_api_id(cursor.getString(1));
                ship.set_name(cursor.getString(2));
                ship.set_filling_speed(cursor.getString(3));
                ship.set_Parking_time(cursor.getString(4));
                ship.set_Boat_filled(cursor.getString(5));
                ship.set_coins_earn(cursor.getString(6));
                ship.set_user_name(cursor.getString(7));
                ship.set_island(cursor.getString(8));
                ship.set_Cost_multiplier(cursor.getString(9));

                //
                shipList.add(ship);
            } while (cursor.moveToNext());
        }

        // return value of ship list
        return shipList;
    }
    public List<User_DB_Objects> getAllUser() {
        List<User_DB_Objects> UserList = new ArrayList<User_DB_Objects>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + Table_User;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User_DB_Objects user = new User_DB_Objects();
                user.set_boatname(cursor.getString(0));
                user.set_boatpark(cursor.getString(1));
                user.set_owner(cursor.getString(2));
                user.set_Time(cursor.getString(3));
                user.set_Type(cursor.getString(4));


                //
                UserList.add(user);
            } while (cursor.moveToNext());
        }

        // return user list
        return UserList;
    }

    // Getting ships Count
    public int getshipCount() {
        String countQuery = "SELECT  * FROM " + TABLE_SHIP;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
    public int getuserCount() {
        String countQuery = "SELECT  * FROM " + Table_User;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}
