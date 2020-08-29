package com.v3.cookbook.baseview.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.core.util.Preconditions;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.v3.cookbook.R;
import com.v3.cookbook.baseview.abstracts.ViewFragment;

import static com.bumptech.glide.util.Preconditions.checkNotNull;

public class ActivityUtils {
    private ActivityUtils() {
    }

    public static void addFragmentToActivity(FragmentManager fragmentManager, Fragment fragment, int frameId) {
        addFragmentToActivity(fragmentManager, fragment, frameId, false, null);
    }

    public static void addFragmentToActivity(FragmentManager fragmentManager, Fragment fragment, int frameId, boolean addToBackStack, String tag) {
        addFragmentToActivity(fragmentManager, fragment, frameId, addToBackStack, tag, false);
    }

    public static void addFragmentToActivity(FragmentManager fragmentManager, Fragment fragment, int frameId, boolean addToBackStack, String tag, boolean loadExisted) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(tag);
        }
        if (loadExisted) {
            final Fragment existingFragment = fragmentManager.findFragmentByTag(tag);
            if (existingFragment != null) {
                for (Fragment f : fragmentManager.getFragments()) {
                    transaction.hide(f);
                }
                transaction.show(existingFragment);
                if (existingFragment instanceof ViewFragment) {
                    new Handler().post(new Runnable() {
                        public void run() {
                            ((ViewFragment) existingFragment).getPresenter().onFragmentDisplay();
                        }
                    });
                }
            } else {
                transaction.add(frameId, fragment, tag);
            }
        } else {
            transaction.add(frameId, fragment, tag);
        }
        transaction.commit();
    }

    public static void ngaAddFragmentToActivity(FragmentManager fragmentManager, Fragment fragment, int frameId, boolean addToBackStack, String tag, boolean loadExisted) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(tag);
        }
        if (loadExisted) {
            final Fragment existingFragment = fragmentManager.findFragmentByTag(tag);
            if (existingFragment != null) {
                for (Fragment f : fragmentManager.getFragments()) {
                    transaction.hide(f);
                }
                transaction.show(existingFragment);
                if (existingFragment instanceof ViewFragment) {
                    new Handler().post(new Runnable() {
                        public void run() {
                            ((ViewFragment) existingFragment).getPresenter().onFragmentDisplay();
                        }
                    });
                }
            } else {
                transaction.add(frameId, fragment, tag);
            }
        } else {
            transaction.add(frameId, fragment, tag);
        }
        transaction.commitAllowingStateLoss();
    }

    public static void replaceFragmentToActivity(FragmentManager fragmentManager, Fragment fragment, int frameId, boolean addToBackStack, String tag, boolean loadExisted) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (loadExisted) {
            final Fragment existingFragment = fragmentManager.findFragmentByTag(tag);
            if (existingFragment != null) {
                for (Fragment f : fragmentManager.getFragments()) {
                    transaction.hide(f);
                }
                transaction.show(existingFragment);
                if (existingFragment instanceof ViewFragment) {
                    new Handler().post(new Runnable() {
                        public void run() {
                            ((ViewFragment) existingFragment).getPresenter().onFragmentDisplay();
                        }
                    });
                }
            } else {
                transaction.replace(frameId, fragment, tag);
            }
        } else {
            transaction.replace(frameId, fragment, tag);
        }
        transaction.commit();
    }

    public static void addChildFragment(FragmentManager fragmentManager, Fragment fragment, int frameId, boolean addToBackStack, String tag) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(tag);
        }
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    public static void addChildFragment(FragmentManager fragmentManager, Fragment fragment, int rootFrameId, boolean addToBackstack) {
        addChildFragment(fragmentManager, fragment, rootFrameId, addToBackstack, fragment.getClass().getSimpleName());
    }

    public static <T extends Activity> void startActivity(Context context, Class<T> clazz) {
        context.startActivity(new Intent(context, clazz));
    }

    public static <T extends Activity> void startActivityForResult(Activity activity, Class<T> clazz, int requestCode) {
        activity.startActivityForResult(new Intent(activity, clazz), requestCode);
    }

    public static <T extends Activity> void startActivity(Context context, Class<T> clazz, Bundle extras) {
        Intent intent = new Intent(context, clazz);
        intent.putExtras(extras);
        context.startActivity(intent);
    }

    public static <T extends Activity> void startActivityForResult(Activity activity, Class<T> clazz, Bundle extras, int requestCode) {
        Intent intent = new Intent(activity, clazz);
        intent.putExtras(extras);
        activity.startActivityForResult(intent, requestCode);
    }

    public static <T extends Activity> void startActivity(Context context, Class<T> clazz, Bundle extras, boolean withAnim) {
        Intent intent = new Intent(context, clazz);
        intent.putExtras(extras);
        context.startActivity(intent);
        if ((context instanceof Activity) && withAnim) {
            ((Activity) context).overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        }
    }
}
