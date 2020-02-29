package com.example.fireo.model;

import java.util.ArrayList;

public class User {
    private String email;
    private String userId;
    private String type;
    private ArrayList<String> buildingList;
    private String fullName;
    private String phoneNumber;
    private ArrayList<String> simpleBuildingName;

    public User(String email, String userId, String type, String fullName, String phoneNumber) {
        this.email = email;
        this.userId = userId;
        this.type = type;
        this.buildingList = new ArrayList<>();
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }

    public User(String email, String userId, String type) {
        this.email = email;
        this.userId = userId;
        this.type = type;
        this.buildingList = new ArrayList<>();
    }

    public User(String email, String userId, String type, ArrayList<String> buildingList) {
        this.email = email;
        this.userId = userId;
        this.type = type;
        this.buildingList = buildingList;
    }

    public User() {

    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return String.valueOf(phoneNumber);
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ArrayList<String> getSimpleBuildingName() {
        return simpleBuildingName;
    }

    public void setSimpleBuildingName(ArrayList<String> simpleBuildingName) {
        this.simpleBuildingName = simpleBuildingName;
    }

    public String getType() {
        return String.valueOf(type);
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<String> getBuildingList() {
        return buildingList;
    }

    public void setBuildingList(ArrayList<String> buildingList) {
        this.buildingList = buildingList;
    }
}
