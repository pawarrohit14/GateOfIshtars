package com.segula.rohitpawar.gateofishtar.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.segula.rohitpawar.gateofishtar.model.ChampionEntry;
import com.segula.rohitpawar.gateofishtar.model.ChampionMaster;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rohit Pawar on 02-02-2018.
 */

public class BaseStore extends SQLiteOpenHelper {


    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "championsManager";

    // Contacts table name
    private static final String TABLE_CHAMPION = "champion";
    private static final String TABLE_CHAMPION_MASTER = "championMaster";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_TIMESTAMP = "timestamp";
    private static final String KEY_HEALTH = "health";

    public BaseStore(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CHAMPION_TABLE = "CREATE TABLE " + TABLE_CHAMPION + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_TIMESTAMP + " TEXT" + ")";

        String CREATE_CHAMPION_MASTER  = "CREATE TABLE " + TABLE_CHAMPION_MASTER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_HEALTH + " INTEGER" + ")";


        db.execSQL(CREATE_CHAMPION_MASTER);
        db.execSQL(CREATE_CHAMPION_TABLE);
    }




    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHAMPION);

        // Create tables again
        onCreate(db);

    }

    public void addCampionMaster(ChampionMaster championMaster) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, championMaster.get_name()); // Contact Name
        values.put(KEY_HEALTH, championMaster.getHealth()); // Contact Phone Number

        // Inserting Row
        db.insert(TABLE_CHAMPION_MASTER, null, values);
        db.close(); // Closing database connection
    }

    // Getting All ChampionEntry entry
    public ArrayList<ChampionMaster> getAllChampions() {
        ArrayList<ChampionMaster> championMasterList = new ArrayList<>();
        // Select All Query

        String selectQuery = "SELECT  * FROM " + TABLE_CHAMPION_MASTER ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ChampionMaster championMaster = new ChampionMaster();
                championMaster.set_id(Integer.parseInt(cursor.getString(0)));
                championMaster.set_name(cursor.getString(1));
                championMaster.setHealth(cursor.getInt(2));
                // Adding contact to list
                championMasterList.add(championMaster);
            } while (cursor.moveToNext());
        }

        // return contact list
        return championMasterList;
    }

    public ChampionMaster getChampionByChampionName( String champName) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CHAMPION_MASTER, new String[]{KEY_ID,
                        KEY_NAME, KEY_HEALTH}, KEY_NAME + "=?",
                new String[]{String.valueOf(champName)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        ChampionMaster championMaster = new ChampionMaster(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getInt(2));
        // return contact
        return championMaster;
    }

    public void addCampionEntry(ChampionEntry championEntry) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, championEntry.get_name()); // Contact Name
        values.put(KEY_TIMESTAMP, championEntry.get_timeStamp()); // Contact Phone Number

        // Inserting Row
        db.insert(TABLE_CHAMPION, null, values);
        db.close(); // Closing database connection
    }

    public ChampionEntry getChampionEntry(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CHAMPION, new String[]{KEY_ID,
                        KEY_NAME, KEY_TIMESTAMP}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        ChampionEntry championEntry = new ChampionEntry(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return contact
        return championEntry;
    }

    // Getting All ChampionEntry entry
    public List<ChampionEntry> getAllEntryByChampion(String champion) {
        List<ChampionEntry> contactList = new ArrayList<>();
        // Select All Query

        String selectQuery = "SELECT  * FROM " + TABLE_CHAMPION + " WHERE " + KEY_NAME + "='" + champion + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ChampionEntry contact = new ChampionEntry();
                contact.set_id(Integer.parseInt(cursor.getString(0)));
                contact.set_name(cursor.getString(1));
                contact.set_timeStamp(cursor.getString(2));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }


    // Deleting single contact
    public void deleteContact(ChampionEntry championEntry) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CHAMPION, KEY_ID + " = ?",
                new String[] { String.valueOf(championEntry.get_id()) });
        db.close();
    }
}
