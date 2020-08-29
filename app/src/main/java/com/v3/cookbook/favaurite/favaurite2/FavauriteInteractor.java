package com.v3.cookbook.favaurite.favaurite2;

import com.v3.cookbook.baseview.abstracts.Interactor;

class FavauriteInteractor extends Interactor<ViewFavauriteContract.Presenter> implements ViewFavauriteContract.Interactor {
    FavauriteInteractor(ViewFavauriteContract.Presenter presenter) {
        super(presenter);
    }
}
