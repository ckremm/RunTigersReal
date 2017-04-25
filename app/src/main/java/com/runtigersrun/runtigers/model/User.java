package com.runtigersrun.runtigers.model;

import static android.app.PendingIntent.getActivity;


/**
 * Created by cjkremm on 4/20/17.
 */

public class User {

    private String fname, lname, uname, pass, userID, friend1, friend2, friend3;

    public User(String fname, String lname, String uname, String pass, String userID, String friend1, String friend2, String friend3){
        this.setFname(fname);
        this.setLname(lname);
        this.setUname(uname);
        this.setPass(pass);
        this.setUserID(userID);
        this.setFriend1(friend1);
        this.setFriend2(friend2);
        this.setFriend3(friend3);
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

    public String getFriend1() {
        return friend1;
    }

    public String getFriend2() {
        return friend2;
    }

    public String getFriend3() {
        return friend3;
    }

    public void setFriend1(String friend1) {
        this.friend1 = friend1;
    }

    public void setFriend2(String friend2) {
        this.friend2 = friend2;
    }

    public void setFriend3(String friend3) {
        this.friend3 = friend3;
    }
}