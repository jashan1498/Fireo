package com.example.fireo.presenter;

import com.example.fireo.DevicesFragment;

public class DevicePresenter {

    DevicesFragment fragment;
    boolean animationActive = false;

    public void init(DevicesFragment fragment) {
        this.fragment = fragment;
        fragment.init();
        fragment.setUpRecyclerView();
//        fragment.setUpFabMenu();
    }

    public void toggle() {
        if (animationActive)
            fragment.hideRecyclerAnimation();
        else
            fragment.showRecyclerAnimation();

        animationActive = !animationActive;

    }


    public interface View {
        void setUpRecyclerView();

        void DeviceChangeListener();

        void filterList(int type);

        void SearchDevice(String id);
    }
}
