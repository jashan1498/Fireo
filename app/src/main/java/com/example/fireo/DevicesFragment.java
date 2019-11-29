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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DevicesFragment extends Fragment implements DevicePresenter.View {
    public static final String TAG = "DEVICES_FRAGMENT";
    @BindView(R.id.recyclerView)
    RecyclerView devicesView;
    private DevicePresenter presenter;
    private View view;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.devices_fragment, container, false);
        ButterKnife.bind(this, view);

        if (view.getContext() != null) {
            this.context = view.getContext();
        }

        presenter = new DevicePresenter();
        presenter.init(this);

        return view;
    }


    @Override
    public void setUpRecyclerView() {
        ArrayList<Device> list = new ArrayList<Device>();
        list.add(new Device("87423049385", true, Constants.DeviceFaults.TYPE_BATTERY, "123213", "Main Building"));
        list.add(new Device("45i23923523", true, Constants.DeviceFaults.TYPE_HEAT, "123213", "Main Building"));
        list.add(new Device("47283974235", true, Constants.DeviceFaults.TYPE_NETWORK, "123213", "Main Building"));
        list.add(new Device("58342902305", true, Constants.DeviceFaults.TYPE_PRESSURE, "123213", "Main Building"));
        list.add(new Device("87423049385", true, Constants.DeviceFaults.TYPE_BATTERY, "123213", "Main Building"));
        list.add(new Device("45i23923523", true, Constants.DeviceFaults.TYPE_HEAT, "123213", "Main Building"));
        list.add(new Device("47283974235", true, Constants.DeviceFaults.TYPE_NETWORK, "123213", "Main Building"));
        list.add(new Device("58342902305", true, Constants.DeviceFaults.TYPE_PRESSURE, "123213", "Main Building"));
        list.add(new Device("87423049385", true, Constants.DeviceFaults.TYPE_BATTERY, "123213", "Main Building"));
        list.add(new Device("45i23923523", true, Constants.DeviceFaults.TYPE_HEAT, "123213", "Main Building"));
        list.add(new Device("47283974235", true, Constants.DeviceFaults.TYPE_NETWORK, "123213", "Main Building"));
        list.add(new Device("58342902305", true, Constants.DeviceFaults.TYPE_PRESSURE, "123213", "Main Building"));
        list.add(new Device("87423049385", true, Constants.DeviceFaults.TYPE_BATTERY, "123213", "Main Building"));
        list.add(new Device("45i23923523", true, Constants.DeviceFaults.TYPE_HEAT, "123213", "Main Building"));
        list.add(new Device("47283974235", true, Constants.DeviceFaults.TYPE_NETWORK, "123213", "Main Building"));
        list.add(new Device("58342902305", true, Constants.DeviceFaults.TYPE_PRESSURE, "123213", "Main Building"));

        DeviceRecyclerAdapter adapter = new DeviceRecyclerAdapter(list, context);
        devicesView = view.findViewById(R.id.recyclerView);
        devicesView.setLayoutManager(new LinearLayoutManager(context));
        devicesView.setAdapter(adapter);

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
}
