package com.example.fireo.model;

import com.google.firebase.firestore.PropertyName;

import java.util.ArrayList;

public class Building {

    private String buildingId;
    private String buildingName;
    private String area;
    private int floorCount;
    @PropertyName("floors")
    private ArrayList<String> floorImg;

//    public Building(String buildingId, String buildingName, String area, int floorCount, ArrayList<String> floorImg) {
//        this.buildingId = buildingId;
//        this.buildingName = buildingName;
//        this.area = area;
//        this.floorCount = floorCount;
//        this.floorImg = floorImg;
//    }

    public Building() {

    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getFloorCount() {
        return floorCount;
    }

    public void setFloorCount(int floorCount) {
        this.floorCount = floorCount;
    }

    @PropertyName("floors")
    public ArrayList<String> getFloorImg() {
        return floorImg;
    }

    @PropertyName("floors")
    public void setFloorImg(ArrayList<String> floorImg) {
        this.floorImg = floorImg;
    }
}
