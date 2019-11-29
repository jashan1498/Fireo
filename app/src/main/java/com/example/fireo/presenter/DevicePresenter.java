package com.example.fireo.presenter;

import com.example.fireo.DevicesFragment;

public class DevicePresenter {

    DevicesFragment fragment;

    public void init(DevicesFragment fragment) {
        fragment.setUpRecyclerView();
    }


    public interface View {
        void setUpRecyclerView();

        void DeviceChangeListener();

        void filterList(int type);

        void SearchDevice(String id);
    }
}
