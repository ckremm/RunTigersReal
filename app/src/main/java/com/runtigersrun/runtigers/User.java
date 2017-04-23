package com.runtigersrun.runtigers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.app.PendingIntent.getActivity;
import static android.content.Context.MODE_PRIVATE;
import static android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE;


/**
 * Created by cjkremm on 4/20/17.
 */

public class User {

    private String fname, lname, uname, pass;

    public User(String fname, String lname, String uname, String pass){
        this.setName(fname);
        this.setStart(lname);
        this.setUname(uname);
        this.setPass(pass);
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getUname() {
        return uname;
    }

    public String getPass() {
        return pass;
    }

    public void setName(String name) {
        this.fname = name;
    }

    public void setStart(String name) {
        this.lname = name;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}