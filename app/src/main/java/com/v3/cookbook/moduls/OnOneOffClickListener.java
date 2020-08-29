package com.v3.cookbook.moduls;

import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;

public abstract class OnOneOffClickListener implements OnClickListener {
    private static final long MIN_CLICK_INTERVAL = 600;
    public static boolean isViewClicked = false;
    private long mLastClickTime;

    public abstract void onSingleClick(View view);

    public final void onClick(View v) {
        long currentClickTime = SystemClock.uptimeMillis();
        long elapsedTime = currentClickTime - this.mLastClickTime;
        this.mLastClickTime = currentClickTime;
        if (elapsedTime > MIN_CLICK_INTERVAL && !isViewClicked) {
            isViewClicked = true;
            startTimer();
            onSingleClick(v);
        }
    }

    private void startTimer() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                OnOneOffClickListener.isViewClicked = false;
            }
        }, MIN_CLICK_INTERVAL);
    }
}
