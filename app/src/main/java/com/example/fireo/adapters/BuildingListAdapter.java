package com.example.fireo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fireo.R;
import com.example.fireo.model.Building;
import com.example.fireo.model.ItemClickListener;

import java.util.ArrayList;

public class BuildingListAdapter extends RecyclerView.Adapter<BuildingListAdapter.RecyclerViewHolder> {
    private ArrayList<Building> buildingsList;
    private ItemClickListener itemClickListener;

    public BuildingListAdapter(ArrayList<Building> buildingsList) {
        this.buildingsList = buildingsList;
    }

    public void setItemClickListener(ItemClickListener clickListener) {
        this.itemClickListener = clickListener;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.building_view, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.buildingNameView.setText(buildingsList.get(position).getBuildingName());

        holder.buildingItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClicked(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return buildingsList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView buildingNameView;
        LinearLayout buildingItemView;

        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            buildingNameView = itemView.findViewById(R.id.building_name);
            buildingItemView = itemView.findViewById(R.id.buildingItemView);
        }
    }
}
