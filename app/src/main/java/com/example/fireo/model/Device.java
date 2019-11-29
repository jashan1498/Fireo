package com.example.fireo.model;

public class Device {
    private String id;
    private boolean faulty;
    private int faultType;
    private String timeStamp;
    private String location;
    private String floorInfo;

    public Device(String id, boolean faulty, int faultType, String timeStamp, String location) {
        this.id = id;
        this.faulty = faulty;
        this.faultType = faultType;
        this.timeStamp = timeStamp;
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        location = location;
    }

    public String getFloorInfo() {
        return floorInfo;
    }

    public void setFloorInfo(String floorInfo) {
        this.floorInfo = floorInfo;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isFaulty() {
        return faulty;
    }

    public void setFaulty(boolean faulty) {
        this.faulty = faulty;
    }

    public int getFaultType() {
        return faultType;
    }

    public void setFaultType(int faultType) {
        this.faultType = faultType;
    }
}
