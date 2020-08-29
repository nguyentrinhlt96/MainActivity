package com.v3.cookbook.baseview.interfaces;

public interface IInteractor<P extends IPresenter> {
    P getPresenter();
}
