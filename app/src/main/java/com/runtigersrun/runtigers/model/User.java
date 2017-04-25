package com.runtigersrun.runtigers.model;

import static android.app.PendingIntent.getActivity;


/**
 * Created by cjkremm on 4/20/17.
 */

public class User {

    private String fname, lname, uname, pass, userID;

    public User(String fname, String lname, String uname, String pass, String userID){
        this.setFname(fname);
        this.setLname(lname);
        this.setUname(uname);
        this.setPass(pass);
        this.setUserID(userID);
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
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

    public void setUname(String uname) {
        this.uname = uname;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}