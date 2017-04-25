package com.runtigersrun.runtigers.model;

/**
 * Created by dstieby on 4/24/2017.
 */

public class LeaderboardProperties {
    private String Time, userID;
    private int trackID;

    public LeaderboardProperties(String Time, int trackID, String userID){
        this.Time = Time;
        this.trackID = getTrackID();
        this.userID = userID;
    }

    public String getTime() {
        return Time;
    }

    public int getTrackID() {
        return trackID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setTrackID(int trackID) {
        this.trackID = trackID;
    }

    public void setTime(String time) {
        Time = time;
    }
}
