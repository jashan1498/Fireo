package com.example.fireo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fireo.Utils.SharedPrefUtils;
import com.example.fireo.model.ItemClickListener;

public class AuthorisedBuildingsActivity extends BaseApplication {

    private RecyclerView recyclerView;

    public static void redirect(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, AuthorisedBuildingsActivity.class);
            context.startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorised_buildings);
        recyclerView = findViewById(R.id.authorised_buildings_list);
        BuildingListAdapter adapter = new BuildingListAdapter(BaseApplication.buildingsList.getBuildingsList());
        adapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                currentBuilding = BaseApplication.buildingsList.getBuildingsList().get(position);
                saveState();
                finish();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


    }
    private void saveState() {
        SharedPrefUtils sharedPrefUtils = new SharedPrefUtils(this);
        sharedPrefUtils.save(SharedPrefUtils.CURRENT_BUILDING,currentBuilding.getBuildingId());
    }
}

