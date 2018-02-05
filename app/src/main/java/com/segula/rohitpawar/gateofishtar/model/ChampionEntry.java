package com.segula.rohitpawar.gateofishtar.model;

/**
 * Created by Rohit Pawar on 05-02-2018.
 */

public class ChampionEntry {

    //private variables
    private int _id;
    private String _name;
    private String _timeStamp;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_timeStamp() {
        return _timeStamp;
    }

    public void set_timeStamp(String _timeStamp) {
        this._timeStamp = _timeStamp;
    }


    // Empty constructor
    public ChampionEntry() {

    }

    // constructor
    public ChampionEntry(String name, String _timeStamp) {
        this._name = name;
        this._timeStamp = _timeStamp;
    }

    // constructor
    public ChampionEntry(int id, String name, String _timeStamp) {
        this._id = id;
        this._name = name;
        this._timeStamp = _timeStamp;
    }
}
