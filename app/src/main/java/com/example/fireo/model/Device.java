package com.example.fireo.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.PropertyName;

import org.json.JSONException;
import org.json.JSONObject;

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

    @PropertyName("buildingId")
    private String buildingId;
    @PropertyName("faulty")
    private boolean faulty;
    @PropertyName("buildingName")
    private String BuildingName;
    @PropertyName("faultType")
    private int faultType;
    @PropertyName("timeStamp")
    private String timeStamp;
    @PropertyName("location")
    private String location;
    @PropertyName("floorInfo")
    private String floorInfo;
    @PropertyName("comment")
    private String comment;
    @PropertyName("floor")
    private int floor;
    @PropertyName("installTime")
    private String installTime;
    @PropertyName("active")
    private boolean isActive;
    @PropertyName("maintenanceCount")
    private int maintenanceCount;
    @PropertyName("networkStatus")
    private int networkStatus;
    @PropertyName("patrolExpire")
    private String patrolExpire;
    @PropertyName("pressureVal")
    private int pressureVal;
    @PropertyName("prevMaintenance")
    private String prevMaintenance;
    @PropertyName("serviceExpire")
    private String serviceExpire;
    @PropertyName("upTime")
    private String upTime;
    @PropertyName("x")
    private float x;
    @PropertyName("y")
    private float y;
    @PropertyName("id")
    private String id;

    public Device(String buildingId, boolean faulty, int faultType, String timeStamp, String location) {
        this.buildingId = buildingId;
        this.faulty = faulty;
        this.faultType = faultType;
        this.timeStamp = timeStamp;
        this.location = location;
    }

    public Device(String buildingId, boolean faulty, String buildingName, int faultType, String timeStamp, String location, String floorInfo, String comment, int floor, String installTime, boolean isActive, int maintenanceCount, int networkStatus, String patrolExpire, int pressureVal, String prevMaintenance, String serviceExpire, String upTime, float x, float y) {
        this.buildingId = buildingId;
        this.faulty = faulty;
        BuildingName = buildingName;
        this.faultType = faultType;
        this.timeStamp = timeStamp;
        this.location = location;
        this.floorInfo = floorInfo;
        this.comment = comment;
        this.floor = floor;
        this.installTime = installTime;
        this.isActive = isActive;
        this.maintenanceCount = maintenanceCount;
        this.networkStatus = networkStatus;
        this.patrolExpire = patrolExpire;
        this.pressureVal = pressureVal;
        this.prevMaintenance = prevMaintenance;
        this.serviceExpire = serviceExpire;
        this.upTime = upTime;
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public Device() {
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
        this.id = in.readString();

    }

    public String toString() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("buildingId", buildingId);
            jsonObject.put("deviceId", id);
            jsonObject.put("floor", floor);

            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Creator getCREATOR() {
        return CREATOR;
    }

    @PropertyName("buildingId")
    public String getBuildingId() {
        return buildingId;
    }

    @PropertyName("buildingId")
    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    @PropertyName("timeStamps")
    public String getTimeStamp() {
        return timeStamp;
    }

    @PropertyName("timeStamps")
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    @PropertyName("location")
    public String getLocation() {
        return location;
    }

    @PropertyName("location")
    public void setLocation(String location) {
        this.location = location;
    }

    @PropertyName("floorInfo")
    public String getFloorInfo() {
        return floorInfo;
    }

    @PropertyName("floorInfo")
    public void setFloorInfo(String floorInfo) {
        this.floorInfo = floorInfo;
    }

    @PropertyName("id")
    public String getId() {
        return id;
    }

    @PropertyName("id")
    public void setId(String id) {
        this.id = id;
    }

    @PropertyName("faulty")
    public boolean isFaulty() {
        return faulty;
    }

    @PropertyName("faulty")
    public void setFaulty(boolean faulty) {
        this.faulty = faulty;
    }

    @PropertyName("faultType")
    public int getFaultType() {
        return faultType;
    }

    @PropertyName("faultType")
    public void setFaultType(int faultType) {
        this.faultType = faultType;
    }

    @PropertyName("buildingName")
    public String getBuildingName() {
        return BuildingName;
    }

    @PropertyName("buildingName")
    public void setBuildingName(String buildingName) {
        BuildingName = buildingName;
    }

    @PropertyName("comment")
    public String getComment() {
        return comment;
    }

    @PropertyName("comment")
    public void setComment(String comment) {
        this.comment = comment;
    }

    @PropertyName("floor")
    public int getFloor() {
        return floor;
    }

    @PropertyName("floor")
    public void setFloor(int floor) {
        this.floor = floor;
    }

    @PropertyName("installTime")
    public String getInstallTime() {
        return installTime;
    }

    @PropertyName("installTime")
    public void setInstallTime(String installTime) {
        this.installTime = installTime;
    }

    @PropertyName("active")
    public boolean isActive() {
        return isActive;
    }

    @PropertyName("active")
    public void setActive(boolean active) {
        isActive = active;
    }

    @PropertyName("maintenanceCount")
    public int getMaintenanceCount() {
        return maintenanceCount;
    }

    @PropertyName("maintenanceCount")
    public void setMaintenanceCount(int maintenanceCount) {
        this.maintenanceCount = maintenanceCount;
    }

    @PropertyName("networkStatus")
    public int getNetworkStatus() {
        return networkStatus;
    }

    @PropertyName("networkStatus")
    public void setNetworkStatus(int networkStatus) {
        this.networkStatus = networkStatus;
    }

    @PropertyName("patrolExpire")
    public String getPatrolExpire() {
        return patrolExpire;
    }

    @PropertyName("patrolExpire")
    public void setPatrolExpire(String patrolExpire) {
        this.patrolExpire = patrolExpire;
    }

    @PropertyName("pressureVal")
    public int getPressureVal() {
        return pressureVal;
    }

    @PropertyName("pressureVal")
    public void setPressureVal(int pressureVal) {
        this.pressureVal = pressureVal;
    }

    @PropertyName("prevMaintenance")
    public String getPrevMaintenance() {
        return prevMaintenance;
    }

    @PropertyName("prevMaintenance")
    public void setPrevMaintenance(String prevMaintenance) {
        this.prevMaintenance = prevMaintenance;
    }

    @PropertyName("serviceExpire")
    public String getServiceExpire() {
        return serviceExpire;
    }

    @PropertyName("serviceExpire")
    public void setServiceExpire(String serviceExpire) {
        this.serviceExpire = serviceExpire;
    }

    @PropertyName("upTime")
    public String getUpTime() {
        return upTime;
    }

    @PropertyName("upTime")
    public void setUpTime(String upTime) {
        this.upTime = upTime;
    }

    @PropertyName("x")
    public float getX() {
        return x;
    }

    @PropertyName("x")
    public void setX(float x) {
        this.x = x;
    }

    @PropertyName("y")
    public float getY() {
        return y;
    }

    @PropertyName("y")
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
        dest.writeString(this.id);
    }
}
