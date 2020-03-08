package com.example.fireo;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;

import androidx.annotation.Nullable;
import androidx.core.widget.ImageViewCompat;

import com.example.fireo.Utils.DialogHelper;
import com.example.fireo.model.Device;

public class DeviceView extends AppCompatImageView {

    private Device device;

    public DeviceView(Context context) {
        super(context);
        init(context);
    }

    public DeviceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public DeviceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public void enableDetailDialog(Activity activity) {
        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogHelper dialogHelper = new DialogHelper(activity);
                dialogHelper.createDeviceDetailDialog(device);
            }
        });
    }

    void init(Context context) {
        setImageDrawable(context.getResources().getDrawable(R.drawable.fire, null));
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ImageViewCompat.setImageTintList(this, ColorStateList.valueOf(context.getResources().getColor(android.R.color.white, null)));
        requestLayout();

    }
}
