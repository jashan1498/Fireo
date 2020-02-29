package com.example.fireo;

public class MapPresenter {

    MapFragment mapFragment;

    public MapPresenter(MapFragment fragment) {
        mapFragment = fragment;
        mapFragment.init();
    }

    public interface View {
        void init();
        void setData();
    }
}
