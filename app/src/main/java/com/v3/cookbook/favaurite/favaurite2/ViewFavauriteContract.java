package com.v3.cookbook.favaurite.favaurite2;

import com.v3.cookbook.baseview.interfaces.IInteractor;
import com.v3.cookbook.baseview.interfaces.IPresenter;
import com.v3.cookbook.baseview.interfaces.PresentView;

public interface ViewFavauriteContract {

    interface Interactor extends IInteractor<Presenter> {
    }

    interface Presenter extends IPresenter<View, Interactor> {
        void viewSeach(int clickback);
        void getDetail(int id, String str, String str2, String str3, byte[] bArr,String fauvarite,int back);
    }

    interface View extends PresentView<Presenter> {
    }
}
