package com.v3.cookbook.baseview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentManager.OnBackStackChangedListener;

import com.v3.cookbook.baseview.utils.ActivityUtils;

import java.util.List;

import butterknife.ButterKnife;

public abstract class BaseActivity extends BaseGlobalActivity {
    private OnBackStackChangedListener mOnBackStackChangedListener = new OnBackStackChangedListener() {
        public void onBackStackChanged() {
            BaseActivity.this.onFragmentDisplay();
        }
    };

    public abstract int getLayoutId();

    public static Fragment getTopFragment(FragmentManager manager) {
        if (manager == null) {
            return null;
        }
        if (manager.getBackStackEntryCount() > 0) {
            String fragmentName = manager.getBackStackEntryAt(manager.getBackStackEntryCount() - 1).getName();
            List<Fragment> fragments = manager.getFragments();
            if (fragments != null && !fragments.isEmpty()) {
                Fragment topFragment = null;
                for (int i = 1; i < fragments.size() && (topFragment == null || !isSameClass(topFragment, fragmentName)); i++) {
                    topFragment = (Fragment) fragments.get(fragments.size() - i);
                }
                return topFragment;
            }
        } else {
            List<Fragment> fragments2 = manager.getFragments();
            if (fragments2 != null && !fragments2.isEmpty()) {
                return (Fragment) fragments2.get(0);
            }
        }
        return null;
    }

    private static boolean isSameClass(Fragment topFragment, String fragmentName) {
        return topFragment.getClass().getSimpleName().equals(fragmentName);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind((Activity) this);
        initLayout();
        getSupportFragmentManager().addOnBackStackChangedListener(this.mOnBackStackChangedListener);
    }

    public void onResume() {
        super.onResume();
        onFragmentDisplay();
    }

    public void onDestroy() {
        super.onDestroy();
        getSupportFragmentManager().removeOnBackStackChangedListener(this.mOnBackStackChangedListener);
    }

    public void onFragmentDisplay() {
        new Handler().post(new Runnable() {
            public void run() {
                Fragment fragment = BaseActivity.getTopFragment(BaseActivity.this.getSupportFragmentManager());
                if (fragment instanceof BaseFragment) {
                    ((BaseFragment) fragment).onDisplay();
                }
            }
        });
    }

    public void initLayout() {
    }

    @SuppressLint("WrongConstant")
    public void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
            getWindow().setSoftInputMode(2);
        }
    }

    @SuppressLint("WrongConstant")
    public void hideKeyboard(View view) {
        if (view != null) {
            ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
            getWindow().setSoftInputMode(2);
        }
    }

    @SuppressLint("WrongConstant")
    public void showKeyboard(EditText editText) {
        editText.requestFocus();
        ((InputMethodManager) getSystemService("input_method")).showSoftInput(editText, 1);
        getWindow().setSoftInputMode(4);
    }

    public void replaceFragment(int containerId, BaseFragment fragment, boolean addToBackStack, String tag) {
        ActivityUtils.replaceFragmentToActivity(getSupportFragmentManager(), fragment, containerId, addToBackStack, tag, false);
    }

    public void addFragment(int containerId, BaseFragment fragment, boolean addToBackStack, String tag) {
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, containerId, addToBackStack, tag, false);
    }

    public void ngaAddFragment(int containerId, BaseFragment fragment, boolean addToBackStack, String tag) {
        ActivityUtils.ngaAddFragmentToActivity(getSupportFragmentManager(), fragment, containerId, addToBackStack, tag, false);
    }

    public void addChildFragment(int containerId, FragmentManager fragmentManager, BaseFragment fragment, boolean addToBackStack, String tag, boolean loadExisted) {
        ActivityUtils.addFragmentToActivity(fragmentManager, fragment, containerId, addToBackStack, tag, loadExisted);
    }

    public void addFragment(int containerId, BaseFragment fragment, boolean addToBackStack, boolean loadExisted) {
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, containerId, addToBackStack, fragment.getClass().getSimpleName(), loadExisted);
    }

    public void addFragment(int containerId, BaseFragment fragment, boolean addToBackStack) {
        addFragment(containerId, fragment, addToBackStack, fragment.getClass().getSimpleName());
    }

    public void replaceFragment(int containerId, BaseFragment fragment) {
        replaceFragment(containerId, fragment, false, null);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FragmentManager manager = getSupportFragmentManager();
        if (manager != null && manager.getFragments() != null && !manager.getFragments().isEmpty()) {
            for (Fragment fragment : manager.getFragments()) {
                if (fragment != null) {
                    fragment.onActivityResult(requestCode, resultCode, data);
                }
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        FragmentManager manager = getSupportFragmentManager();
        if (manager != null && manager.getFragments() != null && !manager.getFragments().isEmpty()) {
            for (Fragment fragment : manager.getFragments()) {
                if (fragment != null) {
                    fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
                }
            }
        }
    }


}
