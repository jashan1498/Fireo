package com.example.fireo.FloatingActionButton;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.fireo.R;

import java.util.ArrayList;
import java.util.Collections;

import jp.wasabeef.blurry.Blurry;

public class FabMenu extends FrameLayout {

    private static final int DEFAULT_DURATION = 500;
    private static final float DEFAULT_SCALE_RATIO = 0.06f;
    private static final float DEFAULT_RATIO = 0.3f;
    private ArrayList<FabItem> itemList = new ArrayList<>();
    private FabItem fabItem;
    private Button actionButton;
    private boolean isVariableScale = false;
    private FabItemClickListener fabItemClickListener;
    private FrameLayout menuView;
    private ImageView blurryView;
    private boolean isAnimating = false;
    private Context context;

    public FabMenu(Context context) {
        super(context);
        init(context);
    }

    public FabMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FabMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public ImageView getBlurryView() {
        return blurryView;
    }

    public void setVariableScale(boolean isVariableScale) {
        this.isVariableScale = isVariableScale;
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.menu, this, true);
        this.context = context;

        actionButton = findViewById(R.id.action_button);
        menuView = findViewById(R.id.linearLayout);
        blurryView = findViewById(R.id.blurryView);
        menuView.setVisibility(GONE);
        blurryView.setVisibility(GONE);

        FrameLayout.LayoutParams parentParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        parentParams.setMargins(0, 0, 0, 0);
        menuView.setLayoutParams(parentParams);
        actionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isAnimating) {
                    toggle();
                }
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void toggle() {
        if (menuView.getVisibility() == GONE) {
            // expand menu
            showMenu();
        } else if (menuView.getVisibility() == VISIBLE) {
            // hide menu
            hideMenu();
        }
    }

    void hideMenu() {
        blurryView.setVisibility(GONE);
        hideMenuAnimation();
        contractActionButton();
    }

    void showMenu() {
        blurryView.setVisibility(VISIBLE);
        Blurry.with(context).capture(((ViewGroup) getParent().getParent()).getChildAt(0)).into(blurryView);
        showMenuAnimation();
        expandActionButton();
    }

    private void contractActionButton() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(actionButton, ROTATION, 0f, 180f);
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(actionButton, SCALE_X, 1f, 0.75f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(actionButton, SCALE_Y, 1f, 0.75f);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(animator, animatorX, animatorY);
        set.setDuration(DEFAULT_DURATION);
        set.start();
    }

    private void expandActionButton() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(actionButton, ROTATION, 0f, 360f);
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(actionButton, SCALE_X, 0.75f, 1f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(actionButton, SCALE_Y, 0.75f, 1f);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(animator, animatorX, animatorY);
        set.setDuration(DEFAULT_DURATION);
        set.start();
    }

    private void showMenuAnimation() {
        for (int x = 0; x < itemList.size(); x++) {
            ValueAnimator animator = ValueAnimator.ofFloat(0, 100f);
            animator.setInterpolator(new BounceInterpolator());
            animator.setDuration(Math.round(DEFAULT_DURATION * (x * DEFAULT_RATIO)));
            itemList.get(x).setVisibility(VISIBLE);
            int finalX = x;
//            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator animation) {
//                    Log.d("fffffffffffffffff",animation.getAnimatedValue()+"");
//                    float itemY = (itemList.get(finalX).getY() / (Float) animation.getAnimatedValue()) * 100;
//                    itemList.get(finalX).setTranslationY(itemY);
//                }
//            });
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    actionButton.setEnabled(false);
                    menuView.setVisibility(VISIBLE);
                    isAnimating = true;
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    isAnimating = false;
                    actionButton.setEnabled(true);
                    Log.d("endddddddddd", animator.getAnimatedValue() + "");

                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    actionButton.setEnabled(true);
                    isAnimating = false;
                    animation.cancel();
                    menuView.setVisibility(VISIBLE);

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            itemList.get(x).setAnimator(animator);
            itemList.get(x).startAnimation();
        }
    }

    private void hideMenuAnimation() {
        for (int x = 0; x < itemList.size(); x++) {
            final ValueAnimator animator = ValueAnimator.ofFloat(100f, 0f);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setDuration(Math.round(DEFAULT_DURATION * (x * DEFAULT_RATIO)));

            final int finalX = x;
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    actionButton.setEnabled(false);
                    isAnimating = true;
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    actionButton.setEnabled(true);
                    isAnimating = false;
                    if (itemList.get(itemList.size() - 1) == itemList.get(finalX) && menuView.getVisibility() == VISIBLE) {
                        menuView.setVisibility(GONE);
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    actionButton.setEnabled(true);
                    menuView.setVisibility(GONE);
                    animation.cancel();
                    isAnimating = false;
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            itemList.get(x).setAnimator(animator);
            itemList.get(x).startAnimation();
        }
    }

    public void setMenuList(ArrayList<FabMenuItem> list) {
        if (list.size() > 0) {
            createMenuItems(list);
        }
    }

    public void setFabItemClickListener(FabItemClickListener listener) {
        this.fabItemClickListener = listener;
    }

    private void createMenuItems(ArrayList<FabMenuItem> list) {
        for (FabMenuItem item : list) {
            fabItem = new FabItem(getContext(), item);
            fabItem.setItemClickListener(new FabItem.itemClicked() {
                @Override
                public void onItemClicked(FabItem item) {
                    fabItemClickListener.itemClicked(item);
                }
            });
            addItemToList(fabItem);
        }
        inflateMenu();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (menuView.getVisibility() == VISIBLE) {
            hideMenu();
        }
        return super.onInterceptTouchEvent(ev);
    }

    public void addItem(FabMenuItem item) {
        if (item != null) {
            fabItem = new FabItem(getContext(), item);
            fabItem.setItemClickListener(new FabItem.itemClicked() {
                @Override
                public void onItemClicked(FabItem item) {
                    fabItemClickListener.itemClicked(item);
                }
            });
            addItemToList(fabItem);
        }
        inflateMenu();
    }

    private void inflateMenu() {
        if (isVariableScale) {
            setScalingEffect();
        }
        menuView.removeAllViews();
        Collections.reverse(itemList);
        menuView.setPadding(0, 0, 0, actionButton.getBottom() + actionButton.getHeight() + 300);
        for (int s = 0; s < itemList.size(); s++) {
            FabItem currentItem = itemList.get(s);
            FrameLayout.LayoutParams itemParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            itemParams.gravity = (Gravity.BOTTOM | Gravity.END);
            currentItem.setLayoutParams(itemParams);
            menuView.addView(currentItem);
            currentItem.setVisibility(VISIBLE);
            currentItem.setY(getItemY(s));

            Log.d("calculations", "" + currentItem.getY());
        }

    }

    private float getItemY(int s) {
        if (s == 0) {
            return -(actionButton.getBottom() + actionButton.getHeight());
        } else {
            Log.d("calculations", "" + (-itemList.get(s - 1).getY()));
            return (-itemList.get(s - 1).getBottom() - itemList.get(s - 1).getTop() - 100);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private void addItemToList(FabItem fabItem) {
        itemList.add(fabItem);
    }

    private void setScalingEffect() {
        for (int x = 0; x < itemList.size(); x++) {
            itemList.get(x).setScaleX(1 - (x * DEFAULT_SCALE_RATIO));
            itemList.get(x).setScaleY(1 - (x * DEFAULT_SCALE_RATIO));
        }
    }

    public void setActionIcon(int res) {
        if (res != 0) {
            actionButton.setBackground(getResources().getDrawable(res, null));
        }
    }

    public interface FabItemClickListener {
        void itemClicked(FabItem item);
    }
}
