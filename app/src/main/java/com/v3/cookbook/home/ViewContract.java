package com.v3.cookbook.home;

import com.v3.cookbook.baseview.interfaces.IInteractor;
import com.v3.cookbook.baseview.interfaces.IPresenter;
import com.v3.cookbook.baseview.interfaces.PresentView;

public interface ViewContract {

    public interface Interactor extends IInteractor<Presenter> {
    }

    public interface Presenter extends IPresenter<View, Interactor> {
        void viewListDisher(int i, String str);

        void viewSeach(int clickback);
    }

    public interface View extends PresentView<Presenter> {
    }
}
