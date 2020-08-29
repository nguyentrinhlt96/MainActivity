package com.v3.cookbook.disher.detaildisher;

import com.v3.cookbook.baseview.interfaces.IInteractor;
import com.v3.cookbook.baseview.interfaces.IPresenter;
import com.v3.cookbook.baseview.interfaces.PresentView;

public interface ViewDetailDisherContract {

    interface Interactor extends IInteractor<Presenter> {
    }

    interface Presenter extends IPresenter<View, Interactor> {
        int getId();

        String getIngredients();

        String getName();

        byte[] getStep();

        String getUrlDish();

        String getFauvarite();

        int getBack();
    }

    interface View extends PresentView<Presenter> {
    }
}
