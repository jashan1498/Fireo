package com.example.fireo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fireo.R;
import com.example.fireo.model.Device;

import java.util.ArrayList;

import static com.example.fireo.Constants.Constants.DeviceFaults.TYPE_BATTERY;
import static com.example.fireo.Constants.Constants.DeviceFaults.TYPE_NETWORK;
import static com.example.fireo.Constants.Constants.DeviceFaults.TYPE_PRESSURE;

public class DeviceRecyclerAdapter extends RecyclerView.Adapter<DeviceRecyclerAdapter.BaseViewHolder> {
    private ArrayList<Device> devicesList;
    private Context context;
    private DeviceInfoViewOnClickListener deviceInfoClickListener;

    public DeviceRecyclerAdapter(ArrayList<Device> list, Context context) {
        this.devicesList = list;
        this.context = context;
    }

    public void setData(ArrayList<Device> devices) {
        this.devicesList = devices;

    }

    public void setOnDeviceInfoViewClickListener(DeviceInfoViewOnClickListener deviceInfoClickListener) {
        this.deviceInfoClickListener = deviceInfoClickListener;

    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_view, parent, false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {

        switch (devicesList.get(position).getFaultType()) {
            case TYPE_BATTERY:
                holder.faultImage.setImageDrawable(context.getResources().getDrawable(R.drawable.battery, null));
                break;
            case TYPE_NETWORK:
                holder.faultImage.setImageDrawable(context.getResources().getDrawable(R.drawable.wifi, null));
                break;
            case TYPE_PRESSURE:
                holder.faultImage.setImageDrawable(context.getResources().getDrawable(R.drawable.pressure, null));
                break;
            default:
                holder.faultImage.setImageDrawable(context.getResources().getDrawable(R.drawable.thermometer, null));
        }
        holder.timeStamp.setText(devicesList.get(position).getTimeStamp());
        holder.deviceId.setText(devicesList.get(position).getId());
        holder.location.setText(devicesList.get(position).getLocation());

        holder.deviceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deviceInfoClickListener != null) {
                    deviceInfoClickListener.onDeviceInfoViewClick(devicesList.get(position));
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return devicesList.size();
    }

    public interface DeviceInfoViewOnClickListener {
        void onDeviceInfoViewClick(Device device);
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {
        TextView deviceId;
        TextView timeStamp;
        TextView location;
        ImageView faultImage;
        CardView deviceView;

        BaseViewHolder(@NonNull View itemView) {
            super(itemView);
            deviceId = itemView.findViewById(R.id.deviceId);
            timeStamp = itemView.findViewById(R.id.timeStamp);
            location = itemView.findViewById(R.id.location);
            faultImage = itemView.findViewById(R.id.faultImageType);
            deviceView = itemView.findViewById(R.id.device_view);

        }
    }
}
