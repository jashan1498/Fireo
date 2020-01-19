package com.example.fireo.model;

import com.google.gson.annotations.SerializedName;

public class Dashboard {

    @SerializedName("battery")
    private int battery;
    @SerializedName("network")
    private int network;
    @SerializedName("pressure")
    private int pressure;
    @SerializedName("temperature")
    private int temperature;
    private int workingDevicesCount;
    private int faultyDevicesCount;
    private int totalDevicesCount;

    public Dashboard() {
    }

    public Dashboard(int battery, int network, int pressure, int temperature, int workingDevicesCount, int faultyDevicesCount, int totalDevicesCount) {
        this.battery = battery;
        this.network = network;
        this.pressure = pressure;
        this.temperature = temperature;
        this.workingDevicesCount = workingDevicesCount;
        this.faultyDevicesCount = faultyDevicesCount;
        this.totalDevicesCount = totalDevicesCount;
    }

    public Dashboard(int battery, int network, int pressure, int temperature) {
        this.battery = battery;
        this.network = network;
        this.pressure = pressure;
        this.temperature = temperature;
    }

    public String getWorkingDevicesCount() {
        return String.valueOf(workingDevicesCount);
    }

    public void setWorkingDevicesCount(int workingDevicesCount) {
        this.workingDevicesCount = workingDevicesCount;
    }

    public String getFaultyDevicesCount() {
        return String.valueOf(faultyDevicesCount);
    }

    public void setFaultyDevicesCount(int faultyDevicesCount) {
        this.faultyDevicesCount = faultyDevicesCount;
    }

    public String getTotalDevicesCount() {
        return String.valueOf(totalDevicesCount);
    }

    public void setTotalDevicesCount(int totalDevicesCount) {
        this.totalDevicesCount = totalDevicesCount;
    }

    public String getBattery() {
        return String.valueOf(battery);
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public String getNetwork() {
        return String.valueOf(network);
    }

    public void setNetwork(int network) {
        this.network = network;
    }

    public String getPressure() {
        return String.valueOf(pressure);
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public String getTemperature() {
        return String.valueOf(temperature);
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }
}

