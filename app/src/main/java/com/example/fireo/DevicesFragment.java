package com.example.fireo;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fireo.adapters.DeviceRecyclerAdapter;
import com.example.fireo.model.Device;
import com.example.fireo.presenter.DevicePresenter;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class DevicesFragment extends Fragment implements DevicePresenter.View {
    public static final String TAG = "DEVICES_FRAGMENT";
    private RecyclerView devicesView;
    private FloatingActionMenu fabMenu;
    private MotionLayout motionLayout;
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

//    public void setUpFabMenu() {
//        fabMenu = view.findViewById(R.id.fabMenu);
//        Bitmap heatBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.thermometer);
//        Bitmap networkBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wifi);
//        Bitmap pressureBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pressure);
//        Bitmap batteryBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.battery);
//
//        FabMenuItem heatMenuItem = new FabMenuItem(getResources().getString(R.string.temprature), heatBitmap);
//        FabMenuItem networkMenuItem = new FabMenuItem(getResources().getString(R.string.network), networkBitmap);
//        FabMenuItem pressureMenuItem = new FabMenuItem(getResources().getString(R.string.pressure), pressureBitmap);
//        FabMenuItem batteryMenuItem = new FabMenuItem(getResources().getString(R.string.battery_low), batteryBitmap);
//
//        ArrayList list = new ArrayList();
//        fabMenu.addItem(heatMenuItem);
//        fabMenu.addItem(pressureMenuItem);
//        fabMenu.addItem(batteryMenuItem);
//        fabMenu.addItem(networkMenuItem);
//
////        list.add(heatMenuItem);
////        list.add(pressureMenuItem);
////        fabMenu.setMenuList(list);
//
//        fabMenu.setFabItemClickListener(new FabMenu.FabItemClickListener() {
//            @Override
//            public void itemClicked(FabItem item) {
//                Toast.makeText(context, "kkkkkkk" + item.getItemInfo().getTitle(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }


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

    public void dummy() {
//        fabMenu.setOnMenuButtonClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                presenter.toggle();
//            }
//        });
    }

    public void hideRecyclerAnimation() {
        fabMenu.showMenu(true);
        motionLayout.setProgress(0);
    }

    public void showRecyclerAnimation() {
        fabMenu.showMenu(false);
        motionLayout.setProgress(1);
    }

    public void init() {
        motionLayout = view.findViewById(R.id.motionLayout);
        fabMenu = view.findViewById(R.id.fabMenu);
        devicesView = view.findViewById(R.id.recyclerView);


    }
}
