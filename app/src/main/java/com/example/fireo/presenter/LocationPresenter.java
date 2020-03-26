package com.example.fireo.presenter;

import android.app.Activity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.fireo.BaseApplication;
import com.example.fireo.Constants.Constants;
import com.example.fireo.custom_view.DeviceView;
import com.example.fireo.custom_view.DeviceViewGroup;
import com.example.fireo.fragments.LocationFragment;
import com.example.fireo.model.Building;
import com.example.fireo.model.Device;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;

public class LocationPresenter {
    private int floor = 0;
    private Building building;
    private BaseApplication application;
    private LocationFragment locationFragment;
    private ArrayList<Device> devicesList;
    private DeviceViewGroup parentLayout;
    private Activity context;

    public LocationPresenter(LocationFragment fragment, DeviceViewGroup parentLayout) {
        if (fragment != null) {
            this.parentLayout = parentLayout;
            locationFragment = fragment;
            application = (BaseApplication) fragment.getActivity();
            context = fragment.getActivity();
            locationFragment.init();
            locationFragment.setData();
        }
    }

    public void saveLocation() {
        ArrayList<Device> devices = new ArrayList<>();
        for (int i = 0; i < parentLayout.getChildCount(); i++) {
            if (parentLayout.getChildAt(i) instanceof DeviceView) {
                DeviceView deviceView = (DeviceView) parentLayout.getChildAt(i);
                devices.add(createDevice(deviceView));
            }
        }
        updateDatabase(devices);
    }

    Device createDevice(DeviceView deviceView) {
        Device device = deviceView.getDevice();
        float x = deviceView.getX();
        float y = deviceView.getY();
        device.setX(x);
        device.setY(y);
        return device;
    }

    private void updateDatabase(ArrayList<Device> devices) {
        for (Device device : devices) {
            WriteBatch batch = application.getFireStore().batch();
            DocumentReference document = application.getFireStore().collection(Constants.Collections.BUILDING).document(BaseApplication.currentBuilding.getBuildingId()).collection("floor" + floor).document(device.getId());
            batch.update(document, "x", device.getX());
            batch.update(document, "y", device.getY());
            batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.d("blaaaaaa", "success");
                    }
                }
            });
        }
    }

    public void fetchDevices(Building currentBuilding, int floor) {
        if (currentBuilding.getBuildingId() != null) {
            if (this.floor != floor || this.building != currentBuilding) {
                application.getFireStore().collection(Constants.Collections.BUILDING).document(currentBuilding.getBuildingId()).collection("floor" + floor).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

    public void refreshList() {
        DeviceView deviceView;
        recreateParent();
        for (Device device : devicesList) {
            deviceView = new DeviceView(context);
            deviceView.setDevice(device);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.topMargin = (int) device.getY();
            params.leftMargin = (int) device.getX();
            deviceView.setLayoutParams(params);
            parentLayout.addView(deviceView);
        }
    }

    private void recreateParent() {
        if (parentLayout.getChildAt(0) instanceof ImageView) {
            ImageView bgImage = (ImageView) parentLayout.getChildAt(0);
            parentLayout.removeAllViews();
            parentLayout.addView(bgImage);
        }
    }

    public interface View {
        void init();

        void setData();
    }
}
