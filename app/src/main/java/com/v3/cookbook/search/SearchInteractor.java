package com.v3.cookbook.search;

import com.v3.cookbook.baseview.abstracts.Interactor;

class SearchInteractor extends Interactor<ViewSearchContract.Presenter> implements ViewSearchContract.Interactor {
    SearchInteractor(ViewSearchContract.Presenter presenter) {
        super(presenter);
    }
}
