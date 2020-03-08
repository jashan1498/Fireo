package com.example.fireo;

import android.animation.Animator;
import android.animation.ObjectAnimator;
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

import java.util.ArrayList;

import static com.example.fireo.BaseApplication.currentBuilding;

public class DevicesFragment extends Fragment implements DevicePresenter.View, View.OnClickListener {

    static final String TAG = "DEVICES_FRAGMENT";
    public DeviceRecyclerAdapter adapter;
    private FloatingActionMenu fabMenu;
    private DevicePresenter presenter;
    private RecyclerView devicesView;
    private View view;
    private Context context;
    private Spinner floorSpinner;
    private TextView buildingNameView;
    private Building localBuilding = new Building();
    private ImageView searchViewToggle;
    private LinearLayout searchViewParent;

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

        presenter = new DevicePresenter(this);
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

    @Override
    public void setUpRecyclerView() {
        setData();
        presenter.list = new ArrayList<>();

        adapter = new DeviceRecyclerAdapter(presenter.list, context);
        devicesView.setLayoutManager(new CustomLinearLayoutManager(context));
        presenter.setDeviceRecyclerAdapter(adapter);
        devicesView.setAdapter(adapter);
        presenter.setRecyclerList(presenter.list);
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

                presenter.setLayoutManager(linearLayoutManager);
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

    @Override
    public void init() {
        BaseApplication application = (BaseApplication) getActivity();

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
        SearchView searchView = view.findViewById(R.id.search_view);

        ObjectAnimator translateAnimator = ObjectAnimator.ofFloat(searchViewParent, "translationY", 0f, 130f);
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(searchViewParent, "alpha", 0f, 1f);

        translateAnimator.setDuration(500);
        alphaAnimator.setDuration(500);
        searchViewToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (presenter.isOpen) {
                    hideSearch(translateAnimator, alphaAnimator);
                } else {
                    openSearch(translateAnimator, alphaAnimator);
                }
                presenter.isOpen = !presenter.isOpen;

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.query(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                presenter.query(newText);
                return false;
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

    @Override
    public void setData() {
        if (currentBuilding != null && BaseApplication.currentBuilding.getBuildingId() != null) {
            Integer[] floors = new Integer[BaseApplication.currentBuilding.getFloorCount()];
            buildingNameView.setText(BaseApplication.currentBuilding.getBuildingName());
            for (int x = 0; x < BaseApplication.currentBuilding.getFloorCount(); x++) {
                floors[x] = x;
            }
            ArrayAdapter spinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, floors);
            floorSpinner.setAdapter(spinnerAdapter);
            floorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (view instanceof TextView) {
                        ((TextView) view).setTextColor(getActivity().getResources().getColor(R.color.creamish_white, null));
                        presenter.spinnerItemSelected(position);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }
}
