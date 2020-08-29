package com.v3.cookbook.disher.detaildisher;


import com.v3.cookbook.baseview.abstracts.Interactor;

class DetailDisherInteractor extends Interactor<ViewDetailDisherContract.Presenter> implements ViewDetailDisherContract.Interactor {
    DetailDisherInteractor(ViewDetailDisherContract.Presenter presenter) {
        super(presenter);
    }
}
