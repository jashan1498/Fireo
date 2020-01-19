package com.example.fireo.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.fireo.R;

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

    public void create(Boolean cancelable, String text, String title) {
        if (activity != null) {
            View view = LayoutInflater.from(activity).inflate(R.layout.dialog_template, null, false);

            dialog = new Dialog(activity);
            dialog.setContentView(view);
            dialog.setCancelable(cancelable);


            TextView contentView = view.findViewById(R.id.content);
            TextView titleView = view.findViewById(R.id.title);
            contentView.setText(text);
            titleView.setText(title);

            view.findViewById(R.id.yes_button).setOnClickListener(this);
            view.findViewById(R.id.no_button).setOnClickListener(this);

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
