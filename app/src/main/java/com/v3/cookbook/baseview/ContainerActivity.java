package com.v3.cookbook.baseview;

import android.app.Activity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.v3.cookbook.R;
import com.v3.cookbook.baseview.interfaces.ContainerView;
import com.v3.cookbook.baseview.interfaces.IPresenter;
import com.v3.cookbook.baseview.interfaces.IView;

public abstract class ContainerActivity extends BaseActivity implements ContainerView {
    public int getLayoutId() {
        return R.layout.container;
    }

    public void addView(IView view) {
        if (view instanceof BaseFragment) {
            addFragment(((BaseFragment) view).setAnimIn(R.anim.slide_none).setAnimOut(R.anim.slide_none), true);
        }
    }

    public void pushView(IView view) {
        if (view instanceof BaseFragment) {
            addFragment(((BaseFragment) view).setAnimIn(R.anim.slide_right_in).setAnimOut(R.anim.slide_right_out), true);
        }
    }

    public void ngaView(IView view) {
        if (view instanceof BaseFragment) {
            ngaAddFragment(((BaseFragment) view).setAnimIn(R.anim.slide_right_in).setAnimOut(R.anim.slide_right_out), true);
        }
    }

    public void replaceView(IView view) {
        if (view instanceof BaseFragment) {
            replaceFragment(((BaseFragment) view).setAnimIn(R.anim.slide_right_in).setAnimOut(R.anim.slide_right_out), true);
        }
    }

    public void popView(IView view) {
        if (view instanceof BaseFragment) {
            FragmentManager manager = getSupportFragmentManager();
            Fragment existingFragment = manager.findFragmentByTag(view.getClass().getSimpleName());
            if (existingFragment != null) {
                manager.beginTransaction().remove(existingFragment).commit();
            }
        }
    }

    public void pushView(IView view, int frameId) {
        if (view instanceof BaseFragment) {
            addFragment(frameId, (BaseFragment) view, false);
        }
    }

    public void loadView(IView view, int frameId) {
        if (view instanceof BaseFragment) {
            addFragment(frameId, (BaseFragment) view, false, true);
        }
    }

    public void presentView(IView view) {
        if (view instanceof BaseFragment) {
            addFragment(((BaseFragment) view).setAnimIn(R.anim.slide_bottom_in).setAnimOut(R.anim.slide_bottom_out), true);
        }
    }

    public void loadChildView(IView view, int frameId, FragmentManager childFragmentManager) {
        addChildFragment(frameId, childFragmentManager, (BaseFragment) view, false, true);
    }

    public void pushChildView(IView view, int frameId, FragmentManager childFragmentManager) {
        addChildFragment(frameId, childFragmentManager, (BaseFragment) view, false, false);
    }

    public void addChildFragment(int frameId, FragmentManager childFragmentManager, BaseFragment fragment, boolean addToBackStack, boolean loadExisted) {
        addChildFragment(frameId, childFragmentManager, fragment, addToBackStack, fragment.getClass().getSimpleName(), loadExisted);
    }

    public void addFragment(BaseFragment fragment, boolean addToBackStack) {
        addFragment(fragment, addToBackStack, fragment.getClass().getSimpleName());
    }

    public void addFragment(BaseFragment fragment, boolean addToBackStack, String tag) {
        addFragment((int) R.id.container_frame, fragment, addToBackStack, tag);
    }

    public void ngaAddFragment(BaseFragment fragment, boolean addToBackStack) {
        ngaAddFragment(fragment, addToBackStack, fragment.getClass().getSimpleName());
    }

    public void ngaAddFragment(BaseFragment fragment, boolean addToBackStack, String tag) {
        ngaAddFragment(R.id.container_frame, fragment, addToBackStack, tag);
    }

    public void replaceFragment(BaseFragment fragment, boolean addToBackStack) {
        replaceFragment(fragment, addToBackStack, fragment.getClass().getSimpleName());
    }

    public void replaceFragment(BaseFragment fragment, boolean addToBackStack, String tag) {
        replaceFragment(R.id.container_frame, fragment, addToBackStack, tag);
    }

    public void initLayout() {
        addFragment(onCreateFirstFragment(), false);
    }

    public void setPresenter(IPresenter presenter) {
    }

    public IPresenter getPresenter() {
        return null;
    }

    public BaseActivity getBaseActivity() {
        return this;
    }

    public Activity getViewContext() {
        return this;
    }

    public void back() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            manager.popBackStack();
        } else {
            finish();
        }
    }

    public void onBackPressed() {
        back();
    }



}
