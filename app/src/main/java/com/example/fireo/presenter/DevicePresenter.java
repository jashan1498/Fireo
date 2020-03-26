package com.example.fireo.presenter;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import com.example.fireo.BaseApplication;
import com.example.fireo.Constants.Constants;
import com.example.fireo.adapters.CustomLinearLayoutManager;
import com.example.fireo.adapters.DeviceRecyclerAdapter;
import com.example.fireo.fragments.DevicesFragment;
import com.example.fireo.model.Device;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.example.fireo.BaseApplication.currentBuilding;
import static com.example.fireo.Constants.Constants.REMOVE_FILTER;

@SuppressLint("DefaultLocale")
public class DevicePresenter {
    public boolean isOpen = false;
    public ArrayList<Device> list;
    private int floor = 0;
    private boolean isLoading = false;
    private DocumentSnapshot recentSnapshot;
    private BaseApplication application;
    private DevicesFragment fragment;
    private DeviceRecyclerAdapter adapter;
    private ArrayList<Device> searchList;

    public DevicePresenter(DevicesFragment fragment) {
        list = new ArrayList<>();
        if (fragment != null) {
            application = (BaseApplication) fragment.getActivity();
        }

    }

    public void setDeviceRecyclerAdapter(DeviceRecyclerAdapter adapter) {
        this.adapter = adapter;
    }

    private void setTime(List<DocumentSnapshot> documents, List<Device> device) {

        for (int x = 0; x < device.size(); x++) {

            Timestamp timestamp = (Timestamp) documents.get(x).get("timeStamp");
            assert timestamp != null;
            long decimal = timestamp.getSeconds();
            String timeStamp = String.format("%03d days, %02d hours , %02d min",
                    TimeUnit.SECONDS.toDays(decimal),
                    TimeUnit.SECONDS.toHours(decimal),
                    TimeUnit.SECONDS.toMinutes(decimal));
            device.get(x).setTimeStamp(timeStamp);
        }
    }

    private void fetchItems() {
        application.getFireStore().collection("building")
                .document(currentBuilding.getBuildingId())
                .collection("floor".concat(String.valueOf(floor))).orderBy("timeStamp").limit(10).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshot) {
                ArrayList<Device> device = (ArrayList<Device>) documentSnapshot.toObjects(Device.class);
                if (device.size() > 0) {
                    setTime(documentSnapshot.getDocuments(), device);
                    list = device;
                    fragment.adapter.setData(list);
                    recentSnapshot = documentSnapshot.getDocuments().get(documentSnapshot.size() - 1);
                    isLoading = false;
                }
            }
        });
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

    public void setLayoutManager(CustomLinearLayoutManager linearLayoutManager) {
        if (linearLayoutManager != null) {
            if (linearLayoutManager.findLastVisibleItemPosition() >= list.size() - 1 && !isLoading) {
                adapter.loadMore();
                loadMore();
                isLoading = true;
            }
        }
    }

    private void loadMore() {
        if (recentSnapshot != null) {
            application.getFireStore().collection("building")
                    .document(currentBuilding.getBuildingId())
                    .collection("floor".concat(String.valueOf(floor))).orderBy("timeStamp").startAfter(recentSnapshot).limit(10).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        QuerySnapshot snapshots = task.getResult();
                        inflateObjects(snapshots);
                    }
                }
            });
        }
    }

    @SuppressLint("DefaultLocale")
    private void inflateObjects(QuerySnapshot snapshots) {
        List<Device> device = snapshots.toObjects(Device.class);

        setTime(snapshots.getDocuments(), device);

        if (device.size() > 0) {
            if (list.get(list.size() - 1) == null) {
                list.remove(list.size() - 1);
            }
            list.addAll(device);
            recentSnapshot = snapshots.getDocuments().get(snapshots.size() - 1);
            adapter.setData(list);
            isLoading = false;
        } else {
            hideLoadingView();
        }
    }

    private void hideLoadingView() {
        isLoading = true;
        if (list.get(list.size() - 1) == null) {
            list.remove(list.size() - 1);
            adapter.setData(list);

        }
    }

    private void refreshList() {
        list.clear();
        adapter.setData(list);
        fetchItems();
    }

    public void spinnerItemSelected(int position) {
        floor = position;
        refreshList();
    }

    public void query(String text) {
        if (text.length() > 0) {
            application.getFireStore().collection("building").document(currentBuilding.getBuildingId()).collection(Constants.Collections.DEVICE_LIST_FULL)
                    .orderBy("id")
                    .startAt(text).limit(5).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        ArrayList<Device> snapshot = (ArrayList<Device>) task.getResult().toObjects(Device.class);
                        searchList = snapshot;
                        adapter.setData(searchList);
                    }
                }
            });
        } else {
            adapter.setData(list);
        }
    }

    public interface View {

        void init();

        void setData();

        void setUpRecyclerView();

        void DeviceChangeListener();

        void filterList(int type);

        void SearchDevice(String id);

        void updateRecyclerView(ArrayList<Device> devices);
    }
}
