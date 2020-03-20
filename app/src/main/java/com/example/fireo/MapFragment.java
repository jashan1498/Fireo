package com.example.fireo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.fireo.model.Building;

public class MapFragment extends Fragment implements MapPresenter.View {
    public static final String TAG = "MAP_FRAGMENT";
    private FrameLayout parentLayout;
    View view;
    private Building currentBuilding;
    private MapPresenter presenter;
    private Spinner floorSpinner;
    private TextView buildingNameView;
    private ImageView blueprint;
    private int floor = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.map_view, container, false);
        parentLayout = view.findViewById(R.id.image_parent_view);
        presenter = new MapPresenter(this,parentLayout);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        currentBuilding = BaseApplication.currentBuilding;
        setData();
        presenter.fetchDevices(currentBuilding, floor);
    }

    @Override
    public void init() {
        floorSpinner = view.findViewById(R.id.floor_spinner);
        buildingNameView = view.findViewById(R.id.building_name);
        blueprint = view.findViewById(R.id.blueprint);
    }

    @Override
    public void setData() {
        if (currentBuilding != null && currentBuilding.getBuildingId() != null) {
            Integer[] floors = new Integer[currentBuilding.getFloorCount()];
            buildingNameView.setText(currentBuilding.getBuildingName());
            for (int x = 0; x < currentBuilding.getFloorCount(); x++) {
                floors[x] = x;
            }
            ArrayAdapter spinnerAdapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_list_item_1, floors);
            floorSpinner.setAdapter(spinnerAdapter);
            floorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (view != null && view instanceof TextView) {
                        ((TextView) view).setTextColor(getActivity().getResources().getColor(R.color.creamish_white, null));
                        setImage(position);
                        floor = position;
                        presenter.fetchDevices(currentBuilding,floor);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    private void setImage(int position) {
        Glide.with(getActivity()).load(currentBuilding.getFloorImg().get(position)).into(blueprint);
    }

}
