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

public class DeviceRecyclerAdapter extends RecyclerView.Adapter {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private ArrayList<Device> devicesList;
    private Context context;
    private DeviceInfoViewOnClickListener deviceInfoClickListener;

    public DeviceRecyclerAdapter(ArrayList<Device> list, Context context) {
        this.devicesList = list;
        this.context = context;
    }

    public void setData(ArrayList<Device> devices) {
        this.devicesList = devices;
        notifyDataSetChanged();
    }

    public void setOnDeviceInfoViewClickListener(DeviceInfoViewOnClickListener deviceInfoClickListener) {
        this.deviceInfoClickListener = deviceInfoClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (devicesList.get(position) == null) {
            return VIEW_TYPE_LOADING;
        } else {
            return VIEW_TYPE_ITEM;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_ITEM) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_view, parent, false);
            return new BaseViewHolder(view);

        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.load_more, parent, false);
            return new LoadMoreViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BaseViewHolder) {
            switch (devicesList.get(position).getFaultType()) {
                case TYPE_BATTERY:
                    ((BaseViewHolder) holder).faultImage.setImageDrawable(context.getResources().getDrawable(R.drawable.battery, null));
                    break;
                case TYPE_NETWORK:
                    ((BaseViewHolder) holder).faultImage.setImageDrawable(context.getResources().getDrawable(R.drawable.wifi, null));
                    break;
                case TYPE_PRESSURE:
                    ((BaseViewHolder) holder).faultImage.setImageDrawable(context.getResources().getDrawable(R.drawable.pressure, null));
                    break;
                default:
                    ((BaseViewHolder) holder).faultImage.setImageDrawable(context.getResources().getDrawable(R.drawable.thermometer, null));
            }
            ((BaseViewHolder) holder).deviceId.setText(devicesList.get(position).getId());
            ((BaseViewHolder) holder).location.setText(devicesList.get(position).getLocation());

            ((BaseViewHolder) holder).deviceView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (deviceInfoClickListener != null) {
                        deviceInfoClickListener.onDeviceInfoViewClick(devicesList.get(position));
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return devicesList.size();
    }

    public void loadMore() {
        devicesList.add(null);
        notifyDataSetChanged();
    }


    public interface DeviceInfoViewOnClickListener {
        void onDeviceInfoViewClick(Device device);
    }

    class LoadMoreViewHolder extends RecyclerView.ViewHolder {

        LoadMoreViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {
        TextView deviceId;
        TextView location;
        ImageView faultImage;
        CardView deviceView;

        BaseViewHolder(@NonNull View itemView) {
            super(itemView);
            deviceId = itemView.findViewById(R.id.deviceId);
            location = itemView.findViewById(R.id.location);
            faultImage = itemView.findViewById(R.id.faultImageType);
            deviceView = itemView.findViewById(R.id.device_view);
        }
    }
}
