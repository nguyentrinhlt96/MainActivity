package com.v3.cookbook.disher.listdisher;


import com.v3.cookbook.baseview.abstracts.Presenter;
import com.v3.cookbook.baseview.interfaces.ContainerView;
import com.v3.cookbook.disher.detaildisher.DetailDisherPresenter;
import com.v3.cookbook.search.SearchPresenter;

public class ListDisherPresenter extends Presenter<ViewListDisherContract.View, ViewListDisherContract.Interactor> implements ViewListDisherContract.Presenter {
    private int idCategory;
    private String title;

    public ListDisherPresenter(ContainerView containerView) {
        super(containerView);
    }

    public ViewListDisherContract.View onCreateView() {
        return new ListDisherFragment();
    }

    public void start() {
    }

    public ViewListDisherContract.Interactor onCreateInteractor() {
        return new ListDisherInteractor(this);
    }

    public ListDisherPresenter setIdCategory(int idCategory2) {
        this.idCategory = idCategory2;
        return this;
    }

    public ListDisherPresenter setTitle(String title2) {
        this.title = title2;
        return this;
    }

    public void viewSeach(int back) {
        new SearchPresenter(this.mContainerView).setBack(back).pushView();
    }

    public void getDetail(int id,String urlImage, String nameDish, String ingredients, byte[] steep,String fauvarite,int back) {
        new DetailDisherPresenter(this.mContainerView)
                .setId(id).setUrlDish(urlImage)
                .setName(nameDish).setIngredients(ingredients)
                .setStep(steep).setFauvarite(fauvarite)
                .setClickBack(back)
                .pushView();
    }

    public int getIdCategory() {
        return this.idCategory;
    }

    public String getTitle() {
        return this.title;
    }
}
