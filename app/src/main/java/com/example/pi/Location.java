package com.example.pi;

public class Location {

    String userId;
    String locationInLatLng;
    int Status;
    String timeAvailable;
    String LatLng;
    String timeOfAccess;

    public Location(){

    }

    public Location(String locationInLatLng, int status, String timeAvailable,String userId,String latLng,String timeOfAccess) {
        this.locationInLatLng = locationInLatLng;
        Status = status;
        this.timeAvailable = timeAvailable;
        this.userId=userId;
        LatLng=latLng;
        this.timeOfAccess=timeOfAccess;
    }

    public String getLocationInLatLng() {
        return locationInLatLng;
    }

    public int getStatus() {
        return Status;
    }

    public String getTimeAvailable() {
        return timeAvailable;
    }

    public String getUserId() {
        return userId;
    }

    public String getLatLng() {
        return LatLng;
    }

    public String getTimeOfAccess() {
        return timeOfAccess;
    }
}
