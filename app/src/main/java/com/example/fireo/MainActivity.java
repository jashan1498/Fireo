package com.example.fireo;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Objects;

public class MainActivity extends BaseApplication {

    public static int QR_SCAN_REQUEST_CODE = 129234;
    BottomNavigationView bottomNavigationView;
    FragmentTransaction transaction;
    FragmentManager manager;
    Fragment active;
    HomeFragment homeFragment;
    DevicesFragment devicesFragment;
    MapFragment mapFragment;
    LocationFragment locationFragment;
    AccountFragment accountFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.white)));
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        manager = getSupportFragmentManager();
        setBottomMenuClickListener();
        initFragments();

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
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                changeFragment(menuItem.getItemId());
                return true;
            }
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
                DeviceDetail.startActivityWithString(results, this);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void fabItemClicked(View view) {
    }
}
