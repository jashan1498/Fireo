package com.example.fireo;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.fireo.Utils.SharedPrefUtils;
import com.example.fireo.model.Device;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends BaseApplication {

    public static int QR_SCAN_REQUEST_CODE = 129234;
    public BottomNavigationView bottomNavigationView;
    FragmentTransaction transaction;
    FragmentManager manager;
    Fragment active;
    HomeFragment homeFragment;
    DevicesFragment devicesFragment;
    MapFragment mapFragment;
    LocationFragment locationFragment;
    AccountFragment accountFragment;
    Toolbar actionBar;
    private ArrayList<Device> deviceList = new ArrayList<Device>();
    private int currentDeviceFragmentFloor = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPrefUtils utils = new SharedPrefUtils(this);
        applyTheme(utils.getDayNightMode());

        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        actionBar = findViewById(R.id.toolbar);
        manager = getSupportFragmentManager();
        setSupportActionBar(actionBar);
        setBottomMenuClickListener();
        initFragments();
        Toast.makeText(this, "Welcome " + firebaseUser.getEmail(), Toast.LENGTH_SHORT).show();
    }

    public ArrayList<Device> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(ArrayList<Device> deviceList) {
        this.deviceList = deviceList;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }

    private void initFragments() {
        manager = getSupportFragmentManager();

        homeFragment = new HomeFragment();
        devicesFragment = new DevicesFragment();
        mapFragment = new MapFragment();
        locationFragment = new LocationFragment();
        accountFragment = new AccountFragment();

        manager.beginTransaction().add(R.id.main_fragment, devicesFragment, DevicesFragment.TAG).hide(devicesFragment).commit();
        manager.beginTransaction().add(R.id.main_fragment, mapFragment, MapFragment.TAG).hide(mapFragment).commit();
        manager.beginTransaction().add(R.id.main_fragment, locationFragment, LocationFragment.TAG).hide(locationFragment).commit();
        manager.beginTransaction().add(R.id.main_fragment, accountFragment, AccountFragment.TAG).hide(accountFragment).commit();
        manager.beginTransaction().add(R.id.main_fragment, homeFragment, HomeFragment.TAG).commit();
        active = homeFragment;
    }

    private void setBottomMenuClickListener() {
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            changeFragment(menuItem.getItemId());
            return true;
        });
    }

    private void changeFragment(int itemId) {
        transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = active;
        switch (itemId) {
            case R.id.home:
                fragment = homeFragment;
                transaction.hide(active).show(fragment).commit();
                break;
            case R.id.devices:
                fragment = devicesFragment;
                transaction.hide(active).show(fragment).commit();
                break;
            case R.id.map1:
                fragment = mapFragment;
                transaction.hide(active).show(fragment).commit();
                break;
            case R.id.map2:
                fragment = locationFragment;
                transaction.hide(active).show(fragment).commit();
                break;
            case R.id.account:
                fragment = accountFragment;
                transaction.hide(active).show(fragment).commit();
                break;
        }
        active = fragment;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);

        // tinting icon
        Drawable drawable = menu.findItem(R.id.qr_scan).getIcon();
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, ContextCompat.getColor(this, R.color.dark_red));
        menu.findItem(R.id.qr_scan).setIcon(drawable);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.qr_scan) {
            new IntentIntegrator(this)
                    .setCaptureActivity(QrScanActivity.class)
                    .initiateScan();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                String results = result.getContents();
                if (isValidInformation(results)) {
                    DeviceDetail.startActivityWithString(results, this);
                } else {
                    Toast.makeText(this, this.getResources().getString(R.string.qr_error_string), Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private boolean isValidInformation(String intentInfo) {
        try {
            JSONObject jsonObject = new JSONObject(intentInfo);
            String buildingId = jsonObject.get("buildingId").toString();
            String deviceId = jsonObject.get("deviceId").toString();
            String floor = jsonObject.get("floor").toString();

            return !buildingId.isEmpty() && !deviceId.isEmpty() && !floor.isEmpty();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;

    }


}
