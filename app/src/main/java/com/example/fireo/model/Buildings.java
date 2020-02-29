package com.example.fireo.model;

import java.util.ArrayList;


public class Buildings {

    private ArrayList<Building> buildingsList;

    public Buildings(ArrayList<Building> buildingsList) {
        this.buildingsList = buildingsList;
    }

    public Buildings() {
        buildingsList = new ArrayList<>();
    }

    public ArrayList<Building> getBuildingsList() {
        return this.buildingsList;
    }

    public void setBuildingsList(ArrayList<Building> buildingsList) {
        this.buildingsList = buildingsList;
    }

    public void addBuilding(Building building) {
        if (building != null)
            this.buildingsList.add(building);
    }
}
