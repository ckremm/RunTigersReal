package com.runtigersrun.runtigers.model;

/**
 * Created by cjkremm on 4/22/17.
 */

public class TrackProperties {

    private String name, start, checkpoint, finish, trackID;

    public TrackProperties(String name, String start, String checkpoint, String finish, String ID){
        this.setName(name);
        this.setStart(start);
        this.setCheckpoint(checkpoint);
        this.setFinish(finish);
        this.setTrackID(ID);
    }

    public String getName() {
        return name;
    }

    public String getStart() {
        return start;
    }

    public String getCheckpoint() {
        return checkpoint;
    }

    public String getFinish() {
        return finish;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setCheckpoint(String checkpoint) {
        this.checkpoint = checkpoint;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public void setTrackID(String trackID) {
        this.trackID = trackID;
    }

    public String getTrackID() {
        return trackID;
    }
}
