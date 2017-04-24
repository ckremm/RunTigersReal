package com.runtigersrun.runtigers.model;

/**
 * Created by dstieby on 4/24/2017.
 */

public class LeaderboardProperties {
    private String Time, trackID, userID;

    public LeaderboardProperties(String Time, String trackID, String userID){
        this.Time = Time;
        this.trackID = trackID;
        this.userID = userID;
    }

    public String getTime() {
        return Time;
    }

    public String getTrackID() {
        return trackID;
    }

    public String getUserID() {
        return userID;
    }



}
