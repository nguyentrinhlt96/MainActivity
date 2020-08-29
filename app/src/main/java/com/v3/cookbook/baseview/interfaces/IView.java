package com.v3.cookbook.baseview.interfaces;

import android.app.Activity;
import com.v3.cookbook.baseview.BaseActivity;

public interface IView<P extends IPresenter> {
    BaseActivity getBaseActivity();

    P getPresenter();

    Activity getViewContext();

    void initLayout();

    void setPresenter(P p);
}
