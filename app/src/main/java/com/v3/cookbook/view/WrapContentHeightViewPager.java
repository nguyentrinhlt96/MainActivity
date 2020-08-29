package com.v3.cookbook.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import androidx.viewpager.widget.ViewPager;

public class WrapContentHeightViewPager extends ViewPager {
    private int currentPosition = 0;
    private boolean swipeLocked;

    public WrapContentHeightViewPager(Context context) {
        super(context);
    }

    public WrapContentHeightViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.currentPosition < getChildCount()) {
            View child = getChildAt(this.currentPosition);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int height = child.getMeasuredHeight();
            if (height != 0) {
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void measureViewPager(int currentPosition2) {
        this.currentPosition = currentPosition2;
        requestLayout();
    }

    public void setSwipeLocked(boolean swipeLocked2) {
        this.swipeLocked = swipeLocked2;
    }

    public boolean onTouchEvent(MotionEvent event) {
        return !this.swipeLocked && super.onTouchEvent(event);
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        return !this.swipeLocked && super.onInterceptTouchEvent(event);
    }

    public boolean canScrollHorizontally(int direction) {
        return !this.swipeLocked && super.canScrollHorizontally(direction);
    }
}
