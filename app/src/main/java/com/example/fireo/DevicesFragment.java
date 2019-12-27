package com.example.fireo;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fireo.adapters.DeviceRecyclerAdapter;
import com.example.fireo.model.Device;
import com.example.fireo.presenter.DevicePresenter;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;

import static com.example.fireo.Constants.DeviceFaults.TYPE_BATTERY;
import static com.example.fireo.Constants.DeviceFaults.TYPE_NETWORK;
import static com.example.fireo.Constants.DeviceFaults.TYPE_PRESSURE;
import static com.example.fireo.Constants.DeviceFaults.TYPE_TEMPERATURE;


public class DevicesFragment extends Fragment implements DevicePresenter.View, View.OnClickListener {

    static final String TAG = "DEVICES_FRAGMENT";
    private FloatingActionMenu fabMenu;
    private DevicePresenter presenter;
    private RecyclerView devicesView;
    private View view;
    private Context context;
    private DeviceRecyclerAdapter adapter;

    @Override
    public void onClick(View v) {
        fabMenu.toggle(true);
        int type = Integer.valueOf(v.getTag().toString());

        if (v instanceof FloatingActionButton) {
            presenter.filterList(type);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.devices_fragment, container, false);

        if (view.getContext() != null) {
            this.context = view.getContext();
        }

        presenter = new DevicePresenter();
        presenter.init(this);


        return view;
    }

    @Override
    public void setUpRecyclerView() {
        ArrayList<Device> list = new ArrayList<>();
        list.add(new Device("87423049385", true, TYPE_BATTERY, "123213", "Main Building"));
        list.add(new Device("45i23923523", true, TYPE_TEMPERATURE, "123213", "Main Building"));
        list.add(new Device("47283974235", true, TYPE_NETWORK, "123213", "Main Building"));
        list.add(new Device("58342902305", true, TYPE_PRESSURE, "123213", "Main Building"));
        list.add(new Device("87423049385", true, TYPE_BATTERY, "123213", "Main Building"));
        list.add(new Device("45i23923523", true, TYPE_TEMPERATURE, "123213", "Main Building"));
        list.add(new Device("47283974235", true, TYPE_NETWORK, "123213", "Main Building"));
        list.add(new Device("58342902305", true, TYPE_PRESSURE, "123213", "Main Building"));
        list.add(new Device("87423049385", true, TYPE_BATTERY, "123213", "Main Building"));
        list.add(new Device("45i23923523", true, TYPE_TEMPERATURE, "123213", "Main Building"));
        list.add(new Device("47283974235", true, TYPE_NETWORK, "123213", "Main Building"));
        list.add(new Device("58342902305", true, TYPE_PRESSURE, "123213", "Main Building"));
        list.add(new Device("87423049385", true, TYPE_BATTERY, "123213", "Main Building"));
        list.add(new Device("45i23923523", true, TYPE_TEMPERATURE, "123213", "Main Building"));
        list.add(new Device("47283974235", true, TYPE_NETWORK, "123213", "Main Building"));
        list.add(new Device("58342902305", true, TYPE_PRESSURE, "123213", "Main Building"));

        adapter = new DeviceRecyclerAdapter(list, context);
        devicesView.setLayoutManager(new LinearLayoutManager(context));
        devicesView.setAdapter(adapter);
        presenter.setRecyclerList(list);
        adapter.setOnDeviceInfoViewClickListener(device -> {
            if (device != null) {
                DeviceDetail.startActivityWithObject(device, context);
            }
        });
    }

    @Override
    public void DeviceChangeListener() {

    }

    @Override
    public void filterList(int type) {

    }


    @Override
    public void SearchDevice(String id) {

    }

    @Override
    public void updateRecyclerView(ArrayList<Device> devices) {
        adapter.setData(devices);
        adapter.notifyDataSetChanged();
    }


    public void init() {
        devicesView = view.findViewById(R.id.recyclerView);
        fabMenu = view.findViewById(R.id.fabMenu);
        fabMenu.setClickable(false);
        FloatingActionButton batteryFab = view.findViewById(R.id.fab_battery);
        FloatingActionButton networkFab = view.findViewById(R.id.fab_network);
        FloatingActionButton pressureFab = view.findViewById(R.id.fab_pressure);
        FloatingActionButton temperatureFab = view.findViewById(R.id.fab_thermometer);
        FloatingActionButton removeFilter = view.findViewById(R.id.remove_filter);

        batteryFab.setOnClickListener(this);
        networkFab.setOnClickListener(this);
        pressureFab.setOnClickListener(this);
        temperatureFab.setOnClickListener(this);
        removeFilter.setOnClickListener(this);

    }


}
