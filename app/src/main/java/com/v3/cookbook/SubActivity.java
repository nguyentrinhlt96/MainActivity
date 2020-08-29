package com.v3.cookbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.core.app.ActivityOptionsCompat;
import com.v3.cookbook.baseview.HomeBaseActivity;
import com.v3.cookbook.baseview.abstracts.ViewFragment;
import com.v3.cookbook.baseview.interfaces.IPresenter;

public class SubActivity extends HomeBaseActivity {
    public static final String EXTRA_FRAGMENT_ARGS = "com.v3.cookbook.EXTRA_FRAGMENT_ARGS";
    public static final String EXTRA_FRAGMENT_CLASS = "com.v3.cookbook.EXTRA_FRAGMENT_CLASS";
    public static int keyBack = 0;
    private static IPresenter<?, ?> mPresenter;

    public static Intent createIntent(Context context) {
        return new Intent(context, SubActivity.class);
    }

    public static ActivityOptionsCompat createActivityOptions(Context context) {
        return ActivityOptionsCompat.makeCustomAnimation(context, R.anim.slide_in_right, R.anim.slide_hold);
    }

    public static synchronized void startActivityLeft(Activity activity, Intent intent, IPresenter<?, ?> presenter) {
        synchronized (SubActivity.class) {
            mPresenter = presenter;
            activity.startActivity(intent);
        }
    }

    public static synchronized void startActivityForResultLeft(Activity activity, Intent intent, int requestCode, IPresenter<?, ?> presenter) {
        synchronized (SubActivity.class) {
            mPresenter = presenter;
            activity.startActivityForResult(intent, requestCode);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void finish() {
        super.finish();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public ViewFragment onCreateFirstFragment() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return null;
        }
        ViewFragment f = (ViewFragment) mPresenter.getFragment();
        Bundle args = extras.getBundle(EXTRA_FRAGMENT_ARGS);
        if (args != null) {
            f.setArguments(args);
        }
        return f;
    }

    public void onBackPressed() {
        if (keyBack == 0) {
            super.onBackPressed();
        }
    }
}
