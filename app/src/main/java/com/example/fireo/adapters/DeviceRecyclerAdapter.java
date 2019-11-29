package com.example.fireo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fireo.R;
import com.example.fireo.model.Device;

import java.util.ArrayList;

import static com.example.fireo.Constants.DeviceFaults.TYPE_BATTERY;
import static com.example.fireo.Constants.DeviceFaults.TYPE_HEAT;
import static com.example.fireo.Constants.DeviceFaults.TYPE_NETWORK;
import static com.example.fireo.Constants.DeviceFaults.TYPE_PRESSURE;

public class DeviceRecyclerAdapter extends RecyclerView.Adapter<DeviceRecyclerAdapter.BaseViewHolder> {
    ArrayList<Device> list;
    Context context;

    public DeviceRecyclerAdapter(ArrayList<Device> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_view, parent, false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {

        switch (list.get(position).getFaultType()) {
            case TYPE_HEAT:
                holder.faultImage.setImageDrawable(context.getResources().getDrawable(R.drawable.thermometer, null));
                break;
            case TYPE_BATTERY:
                holder.faultImage.setImageDrawable(context.getResources().getDrawable(R.drawable.battery, null));
                break;
            case TYPE_NETWORK:
                holder.faultImage.setImageDrawable(context.getResources().getDrawable(R.drawable.wifi, null));
                break;
            case TYPE_PRESSURE:
                holder.faultImage.setImageDrawable(context.getResources().getDrawable(R.drawable.pressure, null));
            default:
                holder.faultImage.setImageDrawable(context.getResources().getDrawable(R.drawable.thermometer, null));
        }
        holder.timeStamp.setText(list.get(position).getTimeStamp());
        holder.deviceId.setText(list.get(position).getId());
        holder.location.setText(list.get(position).getLocation());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {
        TextView deviceId;
        TextView timeStamp;
        TextView location;
        ImageView faultImage;

        BaseViewHolder(@NonNull View itemView) {
            super(itemView);
            deviceId = itemView.findViewById(R.id.deviceId);
            timeStamp = itemView.findViewById(R.id.timeStamp);
            location = itemView.findViewById(R.id.location);
            faultImage = itemView.findViewById(R.id.faultImageType);

        }
    }
}
