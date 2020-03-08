package com.example.fireo;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fireo.presenter.LocationPresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
        presenter.fetchDevices(BaseApplication.currentBuilding, floor);
    }

    private void setClickListeners() {
        vibe = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        top.setOnClickListener(this);
        left.setOnClickListener(this);
        right.setOnClickListener(this);
        bottom.setOnClickListener(this);
        save.setOnClickListener(this);

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
        setClickListeners();

    }

    @Override
    public void setData() {

    }
}
