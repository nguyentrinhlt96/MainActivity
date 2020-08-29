package com.v3.cookbook.search;

import com.v3.cookbook.baseview.interfaces.IInteractor;
import com.v3.cookbook.baseview.interfaces.IPresenter;
import com.v3.cookbook.baseview.interfaces.PresentView;

public interface ViewSearchContract {

    public interface Interactor extends IInteractor<Presenter> {
    }

    public interface Presenter extends IPresenter<View, Interactor> {
        int getback();
        void getDetail(int id, String str, String str2, String str3, byte[] bArr,String fauvarite,int clickBack);
    }

    public interface View extends PresentView<Presenter> {

    }
}
