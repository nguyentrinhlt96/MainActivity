package com.v3.cookbook.baseview.abstracts;

import android.app.Activity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.v3.cookbook.baseview.interfaces.ContainerView;
import com.v3.cookbook.baseview.interfaces.IInteractor;
import com.v3.cookbook.baseview.interfaces.IPresenter;
import com.v3.cookbook.baseview.interfaces.IView;

public abstract class Presenter<V extends IView, I extends IInteractor> implements IPresenter<V, I> {
    protected ContainerView mContainerView;
    protected I mInteractor = onCreateInteractor();
    protected V mView = onCreateView();

    public interface NoneEvent {
    }

    public Presenter(ContainerView containerView) {
        this.mContainerView = containerView;
        this.mView.setPresenter(this);
    }

    protected Presenter() {
        this.mView.setPresenter(this);
    }

    public Activity getViewContext() {
        return this.mView.getViewContext();
    }

    public V getView() {
        return this.mView;
    }

    public I getInteractor() {
        return this.mInteractor;
    }

    public Fragment getFragment() {
        if (getView() instanceof Fragment) {
            return (Fragment) getView();
        }
        return null;
    }

    public void presentView() {
        this.mContainerView.presentView(this.mView);
    }

    public void pushView() {
        this.mContainerView.pushView(this.mView);
    }

    public void replaceView() {
        this.mContainerView.replaceView(this.mView);
    }

    public void pushChildView(int frameId, FragmentManager childFragmentManager) {
        if (getFragment() != null) {
            this.mContainerView.pushChildView(this.mView, frameId, childFragmentManager);
        }
    }

    public void loadChildView(int frameId, FragmentManager childFragmentManager) {
        if (getFragment() != null) {
            this.mContainerView.loadChildView(this.mView, frameId, childFragmentManager);
        }
    }

    public void addView() {
        this.mContainerView.addView(this.mView);
    }

    public void back() {
        this.mView.getBaseActivity().hideKeyboard();
        this.mContainerView.back();
    }

    public void onFragmentDisplay() {
    }

    public boolean isViewShowing() {
        return ((ViewFragment) this.mView).isShowing();
    }
}
