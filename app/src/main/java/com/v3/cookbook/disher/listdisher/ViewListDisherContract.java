package com.v3.cookbook.disher.listdisher;

import com.v3.cookbook.baseview.interfaces.IInteractor;
import com.v3.cookbook.baseview.interfaces.IPresenter;
import com.v3.cookbook.baseview.interfaces.PresentView;

public interface ViewListDisherContract {

    interface Interactor extends IInteractor<Presenter> {
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void getDetail(int id, String str, String str2, String str3, byte[] bArr,String fauvarite,int back);

        int getIdCategory();

        String getTitle();

        void viewSeach(int back);
    }

    interface View extends PresentView<Presenter> {
    }
}
