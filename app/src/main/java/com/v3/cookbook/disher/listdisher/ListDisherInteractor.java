package com.v3.cookbook.disher.listdisher;

import com.v3.cookbook.baseview.abstracts.Interactor;

class ListDisherInteractor extends Interactor<ViewListDisherContract.Presenter> implements ViewListDisherContract.Interactor {
    ListDisherInteractor(ViewListDisherContract.Presenter presenter) {
        super(presenter);
    }
}
