package com.example.fireo.custom_view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.customview.widget.ViewDragHelper;

import com.example.fireo.R;

public class DeviceViewGroup extends FrameLayout {
    ViewDragHelper viewDragHelper;
    private ImageView imageView;
    private int jumpValue = 5;
    private LayoutParams params;

    public DeviceViewGroup(Context context) {
        super(context);
        init(context);
    }

    public DeviceViewGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DeviceViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        viewDragHelper = ViewDragHelper.create(this, new ViewDragCallback());


    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            viewDragHelper.cancel();
            return false;
        }
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

//        deviceView = findViewById(R.id.device_view);
        imageView = findViewById(R.id.floor_img);
    }



//    public void moveLeft() {
//        params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        int newLeft = deviceView.getLeft() - jumpValue;
//        int threshold = 0;
//        if (newLeft < threshold) {
//            newLeft = threshold;
//        }
//
//        params.topMargin = deviceView.getTop();
//        params.leftMargin = newLeft;
//
//        deviceView.setLayoutParams(params);
//    }
//
//    public void moveRight() {
//        params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        int newLeft = deviceView.getLeft() + jumpValue;
//        int threshold = imageView.getWidth() - deviceView.getWidth();
//        if (newLeft > threshold) {
//            newLeft = threshold;
//        }
//        params.topMargin = deviceView.getTop();
//        params.leftMargin = newLeft;
//        deviceView.setLayoutParams(params);
//    }
//
//    public void moveTop() {
//        params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        int newBottom = deviceView.getTop() - jumpValue;
//        int threshold = 0;
//        if (newBottom < threshold) {
//            newBottom = threshold;
//        }
//        params.topMargin = newBottom;
//        params.leftMargin = deviceView.getLeft();
//        deviceView.setLayoutParams(params);
//
//    }
//
//    public void moveBottom() {
//        params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        int newBottom = deviceView.getTop() + jumpValue;
//        int threshold = imageView.getHeight() - deviceView.getHeight();
//        if (newBottom > threshold) {
//            newBottom = threshold;
//        }
//
//        params.topMargin = newBottom;
//        params.leftMargin = deviceView.getLeft();
//        deviceView.setLayoutParams(params);
//    }

    public class ViewDragCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            if (child instanceof DeviceView) {
                return true;
            }
            return false;
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            int height = imageView.getHeight() - child.getHeight();
            int paddingTop = imageView.getPaddingTop();
            int newTop = Math.max(paddingTop, Math.min(height, top));

            return newTop;
        }

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            int width = imageView.getWidth() - child.getWidth();
            int paddingLeft = imageView.getPaddingLeft();
            int newLeft = Math.max(paddingLeft, Math.min(width, left));
            return newLeft;
        }
    }
}
