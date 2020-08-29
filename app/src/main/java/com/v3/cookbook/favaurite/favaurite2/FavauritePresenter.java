package com.v3.cookbook.favaurite.favaurite2;


import com.v3.cookbook.baseview.abstracts.Presenter;
import com.v3.cookbook.baseview.interfaces.ContainerView;
import com.v3.cookbook.disher.detaildisher.DetailDisherPresenter;
import com.v3.cookbook.search.SearchPresenter;

public class FavauritePresenter extends Presenter<ViewFavauriteContract.View
        , ViewFavauriteContract.Interactor> implements ViewFavauriteContract.Presenter {


    public FavauritePresenter(ContainerView containerView) {
        super(containerView);
    }

    public ViewFavauriteContract.View onCreateView() {
        return new FavoriteFragment();
    }

    public void start() {
    }

    public ViewFavauriteContract.Interactor onCreateInteractor() {
        return new FavauriteInteractor(this);
    }

    @Override
    public void viewSeach(int clickback) {
        new SearchPresenter(this.mContainerView).setBack(clickback).pushView();
    }

    public void getDetail(int id,String urlImage, String nameDish, String ingredients, byte[] steep,String fauvarite,int back) {
        new DetailDisherPresenter(this.mContainerView)
                .setId(id).setUrlDish(urlImage)
                .setName(nameDish).setIngredients(ingredients)
                .setStep(steep).setFauvarite(fauvarite)
                .setClickBack(back)
                .pushView();
    }
}
