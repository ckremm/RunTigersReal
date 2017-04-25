package com.runtigersrun.runtigers.model;

/**
 * Created by dstieby on 4/24/2017.
 */

public class LeaderboardProperties {
    private String Time, username, trackID;

    public LeaderboardProperties(String Time, String trackID, String username){
        this.Time = Time;
        this.trackID = trackID;
        this.username = username;
    }

    public String getTime() {
        return Time;
    }

    public String getTrackID() {
        return trackID;
    }

    public String getUserID() {
        return username;
    }

    public void setUserID(String username) {
        this.username = username;
    }

    public void setTrackID(String trackID) {
        this.trackID = trackID;
    }

    public void setTime(String time) {
        Time = time;
    }
}
