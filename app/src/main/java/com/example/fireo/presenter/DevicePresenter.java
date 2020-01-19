package com.example.fireo.presenter;

import com.example.fireo.DevicesFragment;
import com.example.fireo.model.Device;

import java.util.ArrayList;

import static com.example.fireo.Constants.Constants.REMOVE_FILTER;

public class DevicePresenter {


    private DevicesFragment fragment;
    private ArrayList<Device> list;
    public DevicePresenter() {
        list = new ArrayList<>();

    }

    public void init(DevicesFragment fragment) {
        this.fragment = fragment;
        fragment.init();
        fragment.setUpRecyclerView();
    }


    public void setRecyclerList(ArrayList<Device> list) {
        this.list = list;
    }

    public void filterList(int type) {
        ArrayList<Device> newList = new ArrayList<>();
        if (type != REMOVE_FILTER) {
            for (Device device : list) {
                if (device.getFaultType() == type) {
                    newList.add(device);
                }
            }
            fragment.updateRecyclerView(newList);
        } else {
            fragment.updateRecyclerView(list);
        }
    }


    public interface View {
        void setUpRecyclerView();

        void DeviceChangeListener();

        void filterList(int type);

        void SearchDevice(String id);

        void updateRecyclerView(ArrayList<Device> devices);
    }
}
