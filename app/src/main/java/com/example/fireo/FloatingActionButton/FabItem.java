package com.example.fireo.FloatingActionButton;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.fireo.R;


public class FabItem extends ConstraintLayout {

    public static final float ROTATION_RATIO = 3.6f;
    private static final int DEFAULT_ANIM_RATIO = 100;
    private ImageView imageView;
    private TextView textView;
    private FabMenuItem item;
    private itemClicked clickListener;
    private ValueAnimator animator;


    public FabItem(Context context) {
        super(context);
        init();
    }

    public FabItem(Context context, FabMenuItem item) {
        super(context);
        this.item = item;
        init();
    }

    public FabItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FabItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ImageView getImageView() {
        return imageView;
    }

    public TextView getTextView() {
        return textView;
    }

    void setItemClickListener(itemClicked clickListener) {
        this.clickListener = clickListener;
    }

    public FabMenuItem getItemInfo() {
        return item;
    }

    public void setMargins(int margin) {
        ConstraintLayout.LayoutParams params = (LayoutParams) getLayoutParams();
        params.bottomMargin = margin;
        setVisibility(GONE);
        setLayoutParams(params);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    void init() {
        LayoutInflater inflater =
                (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fab_item, this, true);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        setLayoutParams(params);
        setVisibility(VISIBLE);
        imageView = view.findViewById(R.id.imageView);
        textView = view.findViewById(R.id.textView);
        setText();
        setIcon();
        setIconClickListener();
        setClickable(true);
    }

    private void setIconClickListener() {
        FabItem.this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onItemClicked(FabItem.this);
                }
            }
        });
    }

    private void setText() {
        if (item != null && textView != null) {
            textView.setText(item.getTitle());
        }
    }

    public void setIcon() {
        if (item != null && imageView != null) {
            imageView.setImageBitmap(item.getIcon());
        }
    }

    public void startAnimation() {
        if (animator != null) {
            animator.start();

            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float animator = (float) animation.getAnimatedValue();
                    imageView.setScaleX(animator / DEFAULT_ANIM_RATIO);
                    imageView.setScaleY(animator / DEFAULT_ANIM_RATIO);
                    imageView.setRotation(animator * ROTATION_RATIO);
                    imageView.setAlpha(animator / DEFAULT_ANIM_RATIO);
                    textView.setAlpha(animator / DEFAULT_ANIM_RATIO);
                    imageView.setTranslationY(DEFAULT_ANIM_RATIO - animator);
                    textView.setTranslationY(DEFAULT_ANIM_RATIO - animator);
                }
            });
        }
    }

    public ValueAnimator getAnimator() {
        return animator;
    }

    public void setAnimator(ValueAnimator animator) {
        this.animator = animator;
    }

    public interface itemClicked {
        void onItemClicked(FabItem item);
    }

}
