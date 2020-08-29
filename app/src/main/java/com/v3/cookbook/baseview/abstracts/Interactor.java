package com.v3.cookbook.baseview.abstracts;

import com.v3.cookbook.baseview.interfaces.IInteractor;
import com.v3.cookbook.baseview.interfaces.IPresenter;

public abstract class Interactor<P extends IPresenter> implements IInteractor<P> {
    protected P mPresenter;

    public Interactor(P presenter) {
        this.mPresenter = presenter;
    }

    public P getPresenter() {
        return this.mPresenter;
    }
}
