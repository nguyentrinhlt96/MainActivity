package com.v3.cookbook.baseview;

import android.app.Activity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.v3.cookbook.R;
import com.v3.cookbook.baseview.abstracts.ViewFragment;
import com.v3.cookbook.baseview.interfaces.ContainerView;
import com.v3.cookbook.baseview.interfaces.IPresenter;
import com.v3.cookbook.baseview.interfaces.IView;

public abstract class ContainerFragment extends BaseFragment implements ContainerView {
    public int getLayoutId() {
        return R.layout.container;
    }

    public void initLayout() {
    }

    public void addChildFragment(Fragment fragment) {
        addChildFragment(fragment, true);
    }

    public void addChildFragment(Fragment fragment, boolean addToBackStack) {
        addChildFragment(fragment, addToBackStack, fragment.getClass().getSimpleName());
    }

    public void addChildFragment(Fragment fragment, boolean addToBackStack, String tag) {
        addChildFragment(fragment, R.layout.container, addToBackStack, tag);
    }

    public void startPresent() {
        ViewFragment viewFragment = onCreateFirstFragment();
        if (viewFragment != null) {
            addChildFragment(viewFragment, false);
        }
        this.mIsStarted = true;
    }

    public void addView(IView view) {
        if (view instanceof BaseFragment) {
            addChildFragment((BaseFragment) view, true);
        }
    }

    public void pushView(IView view) {
        if (view instanceof BaseFragment) {
            addChildFragment(((BaseFragment) view).setAnimIn(R.anim.slide_right_in).setAnimOut(R.anim.slide_right_out), true);
        }
    }

    public void presentView(IView view) {
        if (view instanceof BaseFragment) {
            addChildFragment(((BaseFragment) view).setAnimIn(R.anim.slide_bottom_in).setAnimOut(R.anim.slide_bottom_out), true);
        }
    }

    public void setPresenter(IPresenter presenter) {
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!this.mStartOnAnimationEnded && !this.mIsStarted) {
            startPresent();
        }
    }

    public boolean needTranslationAnimation() {
        return true;
    }

    public BaseActivity getBaseActivity() {
        if (getActivity() instanceof BaseActivity) {
            return (BaseActivity) getActivity();
        }
        return null;
    }

    public Activity getViewContext() {
        return getActivity();
    }

    public void back() {
        FragmentManager manager = getFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            manager.popBackStack();
        } else {
            getActivity().finish();
        }
    }

    public void loadView(IView view, int frameId) {
        if (view instanceof BaseFragment) {
            addChildFragment((BaseFragment) view, frameId, false, view.getClass().getSimpleName());
        }
    }

    public void pushView(IView view, int frameId) {
        if (view instanceof BaseFragment) {
            addChildFragment((BaseFragment) view, frameId, false, view.getClass().getSimpleName());
        }
    }

    public void popView(IView view) {
        if (view instanceof BaseFragment) {
            FragmentManager manager = getChildFragmentManager();
            Fragment existingFragment = manager.findFragmentByTag(view.getClass().getSimpleName());
            if (existingFragment != null) {
                manager.beginTransaction().remove(existingFragment).commit();
            }
        }
    }

    public IPresenter getPresenter() {
        return null;
    }
}
