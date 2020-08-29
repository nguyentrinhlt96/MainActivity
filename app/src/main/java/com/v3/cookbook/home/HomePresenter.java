package com.v3.cookbook.home;


import com.v3.cookbook.baseview.abstracts.Presenter;
import com.v3.cookbook.baseview.interfaces.ContainerView;
import com.v3.cookbook.disher.listdisher.ListDisherPresenter;
import com.v3.cookbook.search.SearchPresenter;

public class HomePresenter extends Presenter<ViewContract.View, ViewContract.Interactor> implements ViewContract.Presenter {
    private HomeFragment homeFragment;

    public HomePresenter(ContainerView containerView) {
        super(containerView);
    }

    public void start() {
    }

    public ViewContract.Interactor onCreateInteractor() {
        return new HomeInteractor(this);
    }

    @Override
    public ViewContract.View onCreateView() {
        this.homeFragment = new HomeFragment();
        return this.homeFragment;
    }

    public void viewSeach(int back) {
        new SearchPresenter(this.mContainerView).setBack(back).pushView();
    }

    public void viewListDisher(int idCategory, String title) {
        new ListDisherPresenter(this.mContainerView).setIdCategory(idCategory + 1).setTitle(title).pushView();
    }
}
