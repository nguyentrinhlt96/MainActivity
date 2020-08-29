package com.v3.cookbook.baseview.interfaces;

import androidx.fragment.app.FragmentManager;

public interface PresentView<P extends IPresenter> extends IView<P> {
    FragmentManager getChildFragmentManager();

    FragmentManager getFragmentManager();

    boolean isShowing();

    boolean isViewHidden();

    void onNetworkError(boolean z);

    void onRequestError(String str, String str2);

    void onRequestSuccess();
}
