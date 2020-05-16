package com.example.fireo.presenter;

import android.app.Activity;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.example.fireo.BaseApplication;
import com.example.fireo.Constants.Constants;
import com.example.fireo.custom_view.DeviceView;
import com.example.fireo.fragments.MapFragment;
import com.example.fireo.model.Building;
import com.example.fireo.model.Device;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MapPresenter {

    private MapFragment mapFragment;
    private BaseApplication application;
    private Activity context;
    private int floor;
    private Building building;
    private ArrayList<Device> devicesList = new ArrayList<>();
    private FrameLayout parentLayout;

    public MapPresenter(MapFragment fragment, FrameLayout parentLayout) {
        if (fragment != null) {
            this.parentLayout = parentLayout;
            mapFragment = fragment;
            application = (BaseApplication) fragment.getActivity();
            context = fragment.getActivity();
            mapFragment.init();
        }
    }

    public void fetchDevices(Building currentBuilding, int floor) {
        if (currentBuilding.getBuildingId() != null) {
            if (this.floor != floor || this.building != currentBuilding) {
                application.getFireStore().collection(Constants.Collections.BUILDING).document(currentBuilding.getBuildingId()).collection("floor" + floor).get().addOnCompleteListener(
                        new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            devicesList = (ArrayList<Device>) task.getResult().toObjects(Device.class);
                            refreshList();
                        }
                    }
                });
                this.floor = floor;
                this.building = currentBuilding;
            }
        }
    }

    void refreshList() {
        DeviceView deviceView;
        parentLayout.removeAllViews();
        for (Device device : devicesList) {
            deviceView = new DeviceView(context);
            deviceView.setDevice(device);
            deviceView.enableDetailDialog(context);
            deviceView.setX(device.getX());
            deviceView.setY(device.getY());
            parentLayout.addView(deviceView);
        }
        Log.d("blaaa ", "" + parentLayout.getChildCount());

    }

    void setParentView(FrameLayout parentLayout) {
        this.parentLayout = parentLayout;
    }

    FrameLayout getParentLayout() {
        return parentLayout;
    }

    public interface View {
        void init();

        void setData();
    }
}
