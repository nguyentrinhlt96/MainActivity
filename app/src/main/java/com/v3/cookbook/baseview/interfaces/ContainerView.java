package com.v3.cookbook.baseview.interfaces;

import androidx.fragment.app.FragmentManager;
import com.v3.cookbook.baseview.abstracts.ViewFragment;

public interface ContainerView extends IView {
    void addView(IView iView);

    void back();

    void loadChildView(IView iView, int i, FragmentManager fragmentManager);

    void loadView(IView iView, int i);

    void ngaView(IView iView);

    ViewFragment onCreateFirstFragment();

    void popView(IView iView);

    void presentView(IView iView);

    void pushChildView(IView iView, int i, FragmentManager fragmentManager);

    void pushView(IView iView);

    void pushView(IView iView, int i);

    void replaceView(IView iView);
}
