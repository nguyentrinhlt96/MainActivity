package com.v3.cookbook.baseview.interfaces;

import android.app.Activity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public interface IPresenter<V extends IView, I extends IInteractor> {
    void back();

    Fragment getFragment();

    I getInteractor();

    V getView();

    Activity getViewContext();

    boolean isViewShowing();

    void loadChildView(int i, FragmentManager fragmentManager);

    I onCreateInteractor();

    V onCreateView();

    void onFragmentDisplay();

    void presentView();

    void pushChildView(int i, FragmentManager fragmentManager);

    void pushView();

    void replaceView();

    void start();
}
