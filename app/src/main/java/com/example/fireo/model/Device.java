package com.example.fireo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Device implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        @Override
        public Object createFromParcel(Parcel source) {
            return new Device(source);
        }

        @Override
        public Object[] newArray(int size) {
            return new Device[size];
        }
    };


    // fault type
    //     TYPE 0 = BATTERY
    //     TYPE 1 = NETWORK
    //     TYPE 2 = PRESSURE
    //     TYPE 3 = TEMPERATURE

    private String buildingId;
    private boolean faulty;
    private String BuildingName;
    private int faultType;
    private String timeStamp;
    private String location;
    private String floorInfo;
    private String comment;
    private int floor;
    private String installTime;
    private boolean isActive;
    private int maintenanceCount;
    private int networkStatus;
    private String patrolExpire;
    private int pressureVal;
    private String prevMaintenance;
    private String serviceExpire;
    private String upTime;
    private float x;
    private float y;

    public Device(String buildingId, boolean faulty, int faultType, String timeStamp, String location) {
        this.buildingId = buildingId;
        this.faulty = faulty;
        this.faultType = faultType;
        this.timeStamp = timeStamp;
        this.location = location;
    }


    // Parcelling part
    private Device(Parcel in) {
        this.buildingId = in.readString();
        this.faulty = (Boolean) in.readValue(null);
        this.BuildingName = in.readString();
        this.faultType = in.readInt();
        this.timeStamp = in.readString();
        this.location = in.readString();
        this.floorInfo = in.readString();
        this.comment = in.readString();
        this.floor = in.readInt();
        this.installTime = in.readString();
        this.isActive = (Boolean) in.readValue(null);
        this.maintenanceCount = in.readInt();
        this.networkStatus = in.readInt();
        this.patrolExpire = in.readString();
        this.pressureVal = in.readInt();
        this.prevMaintenance = in.readString();
        this.serviceExpire = in.readString();
        this.upTime = in.readString();
        this.x = in.readFloat();
        this.y = in.readFloat();

    }

    public static Creator getCREATOR() {
        return CREATOR;
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
        return buildingId;
    }

    public void setId(String id) {
        this.buildingId = id;
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

    public String getBuildingName() {
        return BuildingName;
    }

    public void setBuildingName(String buildingName) {
        BuildingName = buildingName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getInstallTime() {
        return installTime;
    }

    public void setInstallTime(String installTime) {
        this.installTime = installTime;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getMaintenanceCount() {
        return maintenanceCount;
    }

    public void setMaintenanceCount(int maintenanceCount) {
        this.maintenanceCount = maintenanceCount;
    }

    public int getNetworkStatus() {
        return networkStatus;
    }

    public void setNetworkStatus(int networkStatus) {
        this.networkStatus = networkStatus;
    }

    public String getPatrolExpire() {
        return patrolExpire;
    }

    public void setPatrolExpire(String patrolExpire) {
        this.patrolExpire = patrolExpire;
    }

    public int getPressureVal() {
        return pressureVal;
    }

    public void setPressureVal(int pressureVal) {
        this.pressureVal = pressureVal;
    }

    public String getPrevMaintenance() {
        return prevMaintenance;
    }

    public void setPrevMaintenance(String prevMaintenance) {
        this.prevMaintenance = prevMaintenance;
    }

    public String getServiceExpire() {
        return serviceExpire;
    }

    public void setServiceExpire(String serviceExpire) {
        this.serviceExpire = serviceExpire;
    }

    public String getUpTime() {
        return upTime;
    }

    public void setUpTime(String upTime) {
        this.upTime = upTime;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.buildingId);
        dest.writeValue(this.faulty);
        dest.writeString(this.BuildingName);
        dest.writeInt(this.faultType);
        dest.writeString(this.timeStamp);
        dest.writeString(this.location);
        dest.writeString(this.floorInfo);
        dest.writeString(this.comment);
        dest.writeInt(this.floor);
        dest.writeString(this.installTime);
        dest.writeValue(this.isActive);
        dest.writeInt(this.maintenanceCount);
        dest.writeInt(this.networkStatus);
        dest.writeString(this.patrolExpire);
        dest.writeInt(this.pressureVal);
        dest.writeString(this.prevMaintenance);
        dest.writeString(this.serviceExpire);
        dest.writeString(this.upTime);
        dest.writeFloat(this.x);
        dest.writeFloat(this.y);
    }
}
