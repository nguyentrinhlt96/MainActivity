package com.v3.cookbook.baseview.abstracts;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.v3.cookbook.baseview.BaseActivity;
import com.v3.cookbook.baseview.BaseFragment;
import com.v3.cookbook.baseview.interfaces.IPresenter;
import com.v3.cookbook.baseview.interfaces.PresentView;

public abstract class ViewFragment<P extends IPresenter> extends BaseFragment implements PresentView<P> {
    protected boolean mIsInitialized = false;
    protected P mPresenter;
    private boolean mViewHidden;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (!this.mIsInitialized) {
            this.mRootView = super.onCreateView(inflater, container, savedInstanceState);
            if (getArguments() != null) {
                parseArgs(getArguments());
            }
            initLayout();
            this.mIsInitialized = true;
        }
        return this.mRootView;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!this.mStartOnAnimationEnded && !this.mIsStarted) {
            startPresent();
        }
    }

    public void startPresent() {
        P p = this.mPresenter;
        if (p != null) {
            p.start();
        }
        this.mIsStarted = true;
    }

    public void initLayout() {
    }

    public BaseActivity getBaseActivity() {
        if (getActivity() instanceof BaseActivity) {
            return (BaseActivity) getActivity();
        }
        return null;
    }

    public void onRequestError(String errorCode, String errorMessage) {
    }

    public void onNetworkError(boolean shouldShowPopup) {
    }

    public void onRequestSuccess() {
    }

    public Activity getViewContext() {
        return getActivity();
    }

    public void setPresenter(P presenter) {
        this.mPresenter = presenter;
    }

    public P getPresenter() {
        return this.mPresenter;
    }

    public void parseArgs(Bundle args) {
    }

    public boolean needTranslationAnimation() {
        return true;
    }

    public void onDisplay() {
        super.onDisplay();
        P p = this.mPresenter;
        if (p != null) {
            p.onFragmentDisplay();
        }
    }

    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        this.mViewHidden = hidden;
    }

    public boolean isViewHidden() {
        return this.mViewHidden;
    }

    public boolean isShowing() {
        return isResumed() && this == BaseActivity.getTopFragment(getFragmentManager());
    }

    public void onDestroyView() {
        super.onDestroyView();
    }

    public void onDetach() {
        super.onDetach();
    }


}
