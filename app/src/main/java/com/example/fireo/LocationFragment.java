package com.example.fireo;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fireo.presenter.LocationPresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.example.fireo.BaseApplication.currentBuilding;

public class LocationFragment extends Fragment implements View.OnClickListener, LocationPresenter.View {
    static final String TAG = "LOCATION_FRAGMENT";
    View view;
    private DeviceViewGroup deviceViewGroup;
    private ImageButton top, bottom, left, right;
    private FloatingActionButton save, fab;
    private Vibrator vibe;
    private Context context;
    private LocationPresenter presenter;
    private int floor = 0;
    private Spinner floorSpinner;
    private TextView buildingNameView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.location_view, container, false);
        if (container != null) {
            context = container.getContext();
        }
        this.view = view;
        deviceViewGroup = view.findViewById(R.id.deviceViewGroup);
        presenter = new LocationPresenter(this, deviceViewGroup);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.fetchDevices(currentBuilding, floor);
        setData();
    }

    private void setClickListeners() {
        vibe = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

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
                        presenter.fetchDevices(currentBuilding, floor);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

    }

    private void setImage(int position) {

    }

    private void addNewDeviceToScreen() {

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
//            case R.id.top:
//                deviceViewGroup.moveTop();
//                vibrate();
//                break;
//            case R.id.bottom:
//                deviceViewGroup.moveBottom();
//                vibrate();
//                break;
//            case R.id.left:
//                deviceViewGroup.moveLeft();
//                vibrate();
//                break;
//            case R.id.right:
//                deviceViewGroup.moveRight();
//                vibrate();
//                break;
            case R.id.save_device:
                presenter.saveLocation();
        }

    }

    private void vibrate() {
        vibe.vibrate(10);
    }


    @Override
    public void init() {

        fab = view.findViewById(R.id.fab_add);
        top = view.findViewById(R.id.top);
        bottom = view.findViewById(R.id.bottom);
        left = view.findViewById(R.id.left);
        right = view.findViewById(R.id.right);
        save = view.findViewById(R.id.save_device);
        floorSpinner = view.findViewById(R.id.floor_spinner);
        buildingNameView = view.findViewById(R.id.building_name);
        setClickListeners();

    }
}
