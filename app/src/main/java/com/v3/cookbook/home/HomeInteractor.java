package com.v3.cookbook.home;

import com.v3.cookbook.baseview.abstracts.Interactor;


class HomeInteractor extends Interactor<ViewContract.Presenter> implements ViewContract.Interactor {
    HomeInteractor(ViewContract.Presenter presenter) {
        super(presenter);
    }
}
