package com.runtigersrun.runtigers.model;

/**
 * Created by cjkremm on 4/23/17.
 */

public class Estimote {

    private String UUID, Major, Minor, Callsign, ConName;

    public Estimote(String UID, String Majo, String Mino, String Callsig){
        this.UUID = UID;
        this.Major = Majo;
        this.Minor = Mino;
        this.Callsign = Callsig;
        this.ConName = UID + ":" + Majo + ":" + Mino;
    }

    public String getCallsign() {
        return Callsign;
    }

    public String getMajor() {
        return Major;
    }

    public String getConName() {
        return ConName;
    }

    public String getMinor() {
        return Minor;
    }

    public String getUUID() {
        return UUID;
    }
}
