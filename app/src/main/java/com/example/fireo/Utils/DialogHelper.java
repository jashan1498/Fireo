package com.example.fireo.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.fireo.R;
import com.example.fireo.model.Device;

public class DialogHelper implements View.OnClickListener {


    private DialogListener dialogListener;
    private Activity activity;
    private Dialog dialog;

    public DialogHelper(Activity activity) {
        if (activity != null) this.activity = activity;
    }

    public void setDialogListener(DialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    public void createYesNoDialog(Boolean cancelable, String text, String title) {
        if (activity != null) {
            View view = LayoutInflater.from(activity).inflate(R.layout.dialog_template, null, false);

            dialog = new Dialog(activity);
            dialog.setContentView(view);
            dialog.setCancelable(cancelable);


            TextView contentView = view.findViewById(R.id.content);
            TextView titleView = view.findViewById(R.id.device_id_text);
            contentView.setText(text);
            titleView.setText(title);

            view.findViewById(R.id.yes_button).setOnClickListener(this);
            view.findViewById(R.id.no_button).setOnClickListener(this);

            dialog.show();
            Window window = dialog.getWindow();
            window.setLayout(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        }

    }

    public void createDeviceDetailDialog(Device device) {
        if (activity != null) {
            View view = LayoutInflater.from(activity).inflate(R.layout.device_detail_dialog_template, null, false);

            dialog = new Dialog(activity);
            dialog.setContentView(view);
            dialog.setCancelable(false);

            TextView deviceId = view.findViewById(R.id.device_id_text);
            TextView faultType = view.findViewById(R.id.fault_type_text);
            TextView activeText = view.findViewById(R.id.active_text);
            TextView cancelButton = view.findViewById(R.id.ok_text);


            deviceId.setText(String.valueOf(device.getId()));
            faultType.setText(String.valueOf(device.getFaultType()));
            activeText.setText(device.isActive() ? "true" : "false");

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });

            dialog.show();
            Window window = dialog.getWindow();
            window.setLayout(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public void onClick(View v) {
        if (dialogListener != null) {
            switch (v.getId()) {
                case R.id.yes_button:
                    dialogListener.onClick(true);
                case R.id.no_button:
                    dialogListener.onClick(false);
            }
        }
    }

    public void dismissDialog() {
        dialog.dismiss();

    }

    public interface DialogListener {
        void onClick(boolean accepted);
    }
}
