package com.segula.rohitpawar.gateofishtar.model;

/**
 * Created by Rohit Pawar on 05-02-2018.
 */

public class ChampionMaster {

    private int _id;
    private String _name;
    private int health;

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

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }


    // Empty constructor
    public ChampionMaster() {

    }

    // constructor
    public ChampionMaster(String name, int health) {
        this._name = name;
        this.health = health;
    }

    // constructor
    public ChampionMaster(int id, String name, int health) {
        this._id = id;
        this._name = name;
        this.health = health;
    }

}
