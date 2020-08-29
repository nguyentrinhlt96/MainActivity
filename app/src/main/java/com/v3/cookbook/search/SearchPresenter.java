package com.v3.cookbook.search;

import com.v3.cookbook.baseview.abstracts.Presenter;
import com.v3.cookbook.baseview.interfaces.ContainerView;
import com.v3.cookbook.disher.detaildisher.DetailDisherPresenter;
import com.v3.cookbook.disher.listdisher.ListDisherPresenter;


public class SearchPresenter extends Presenter<ViewSearchContract.View, ViewSearchContract.Interactor> implements ViewSearchContract.Presenter {
    int back;
    public SearchPresenter(ContainerView containerView) {
        super(containerView);
    }

    public ViewSearchContract.View onCreateView() {
        new SearchFragment();
        return SearchFragment.getInstance();
    }

    public void start() {
    }

    public ViewSearchContract.Interactor onCreateInteractor() {
        return new SearchInteractor(this);
    }

    public SearchPresenter setBack(int back) {
        this.back = back;
        return this;
    }

    @Override
    public int getback() {
        return back;
    }

    @Override
    public void getDetail(int id,String urlImage, String nameDish, String ingredients, byte[] steep,String fauvarite, int clickBack) {
        new DetailDisherPresenter(this.mContainerView)
                .setId(id).setUrlDish(urlImage)
                .setName(nameDish).setIngredients(ingredients)
                .setStep(steep).setFauvarite(fauvarite)
                .setClickBack(clickBack)
                .pushView();
    }
}
