package com.example.fireo;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fireo.adapters.DeviceRecyclerAdapter;
import com.example.fireo.model.Building;
import com.example.fireo.model.Device;
import com.example.fireo.presenter.DevicePresenter;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
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

@SuppressLint("DefaultLocale")
public class DevicesFragment extends Fragment implements DevicePresenter.View, View.OnClickListener {

    static final String TAG = "DEVICES_FRAGMENT";
    private DocumentSnapshot recentSnapshot;
    private BaseApplication application;
    private FloatingActionMenu fabMenu;
    private DevicePresenter presenter;
    private RecyclerView devicesView;
    private View view;
    private Context context;
    private Spinner floorSpinner;
    private TextView buildingNameView;
    private DeviceRecyclerAdapter adapter;
    private boolean isLoading = false;
    private ArrayList<Device> list;
    private Building localBuilding = new Building();
    private int floor = 0;
    private ImageView searchViewToggle;
    private boolean isOpen = false;
    private LinearLayout searchViewParent;
    private SearchView searchView;

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
    public void onResume() {
        super.onResume();
        if (currentBuilding.getBuildingId() != null && localBuilding != currentBuilding) {
            setData();
            localBuilding = currentBuilding;
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
                    adapter.setData(list);
                    recentSnapshot = documentSnapshot.getDocuments().get(documentSnapshot.size() - 1);
                    isLoading = false;
                }
            }
        });
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

    @Override
    public void setUpRecyclerView() {
        setData();
        list = new ArrayList<>();

        adapter = new DeviceRecyclerAdapter(list, context);
        devicesView.setLayoutManager(new CustomLinearLayoutManager(context));
        devicesView.setAdapter(adapter);
        presenter.setRecyclerList(list);
        adapter.setOnDeviceInfoViewClickListener(device -> {
            if (device != null) {
                DeviceDetail.startActivityWithObject(device, context);
            }
        });
        devicesView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                CustomLinearLayoutManager linearLayoutManager = (CustomLinearLayoutManager) recyclerView.getLayoutManager();

                if (linearLayoutManager != null) {
                    if (linearLayoutManager.findLastVisibleItemPosition() >= list.size() - 1 && !isLoading) {
                        adapter.loadMore();
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });
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
        application = (BaseApplication) getActivity();

        devicesView = view.findViewById(R.id.recyclerView);
        fabMenu = view.findViewById(R.id.fabMenu);
        fabMenu.setClickable(false);
        FloatingActionButton batteryFab = view.findViewById(R.id.fab_battery);
        FloatingActionButton networkFab = view.findViewById(R.id.fab_network);
        FloatingActionButton pressureFab = view.findViewById(R.id.fab_pressure);
        FloatingActionButton temperatureFab = view.findViewById(R.id.fab_thermometer);
        FloatingActionButton removeFilter = view.findViewById(R.id.remove_filter);
        searchViewToggle = view.findViewById(R.id.search_view_toggle);
        floorSpinner = view.findViewById(R.id.floor_spinner);
        buildingNameView = view.findViewById(R.id.building_name);
        searchViewParent = view.findViewById(R.id.search_view_parent);
        searchView = view.findViewById(R.id.search_view);

        ObjectAnimator translateAnimator = ObjectAnimator.ofFloat(searchViewParent, "translationY", 0f, 130f);
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(searchViewParent, "alpha", 0f, 1f);

        translateAnimator.setDuration(500);
        alphaAnimator.setDuration(500);
        searchViewToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen) {
                    hideSearch(translateAnimator, alphaAnimator);
                } else {
                    openSearch(translateAnimator, alphaAnimator);
                }
                isOpen = !isOpen;

            }
        });


        batteryFab.setOnClickListener(this);
        networkFab.setOnClickListener(this);
        pressureFab.setOnClickListener(this);
        temperatureFab.setOnClickListener(this);
        removeFilter.setOnClickListener(this);

    }

    private void openSearch(ObjectAnimator translateAnimator, ObjectAnimator alphaAnimator) {
        searchViewToggle.setImageDrawable(getResources().getDrawable(R.drawable.ic_close, null));
        translateAnimator.start();
        alphaAnimator.start();
        translateAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                searchViewParent.getChildAt(0).requestFocus();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void hideSearch(ObjectAnimator translateAnimator, ObjectAnimator alphaAnimator) {
        searchViewToggle.setImageDrawable(getResources().getDrawable(R.drawable.ic_search));
        translateAnimator.reverse();
        alphaAnimator.reverse();
    }

    private void setData() {
        if (currentBuilding != null && BaseApplication.currentBuilding.getBuildingId() != null) {
            Integer[] floors = new Integer[BaseApplication.currentBuilding.getFloorCount()];
            buildingNameView.setText(BaseApplication.currentBuilding.getBuildingName());
            for (int x = 0; x < BaseApplication.currentBuilding.getFloorCount(); x++) {
                floors[x] = x;
            }
            ArrayAdapter spinnerAdapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_list_item_1, floors);
            floorSpinner.setAdapter(spinnerAdapter);
            floorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (view != null && view instanceof TextView) {
                        ((TextView) view).setTextColor(getActivity().getResources().getColor(R.color.creamish_white, null));
                        floor = position;
                        refreshList();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    private void refreshList() {
        list.clear();
        adapter.setData(list);
        fetchItems();
//        isLoading = false;
    }
}
