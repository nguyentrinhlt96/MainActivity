package com.v3.cookbook.disher.detaildisher;


import com.v3.cookbook.baseview.abstracts.Presenter;
import com.v3.cookbook.baseview.interfaces.ContainerView;

public class DetailDisherPresenter extends Presenter<ViewDetailDisherContract.View, ViewDetailDisherContract.Interactor> implements ViewDetailDisherContract.Presenter {
    int id;
    String ingredients;
    String nameDish;
    byte[] steps;
    String fauvarite;
    String urlImageDish;
    int clickBack;
    public DetailDisherPresenter(ContainerView containerView) {
        super(containerView);
    }

    public ViewDetailDisherContract.View onCreateView() {
        new DetailDisherFragment();
        return DetailDisherFragment.getInstance();
    }

    public void start() {
    }

    public ViewDetailDisherContract.Interactor onCreateInteractor() {
        return new DetailDisherInteractor(this);
    }

    public DetailDisherPresenter setUrlDish(String urlDish) {
        this.urlImageDish = urlDish;
        return this;
    }

    public DetailDisherPresenter setIngredients(String ingredients2) {
        this.ingredients = ingredients2;
        return this;
    }

    public DetailDisherPresenter setStep(byte[] steps2) {
        this.steps = steps2;
        return this;
    }

    public DetailDisherPresenter setName(String name) {
        this.nameDish = name;
        return this;
    }

    public DetailDisherPresenter setFauvarite(String fauvarite) {
        this.fauvarite = fauvarite;
        return this;
    }

     public DetailDisherPresenter setId(int id) {
        this.id = id;
        return this;
    }

    public DetailDisherPresenter setClickBack(int clickBack) {
        this.clickBack = clickBack;
        return this;
    }


    public String getUrlDish() {
        return this.urlImageDish;
    }

    @Override
    public String getFauvarite() {
        return this.fauvarite;
    }

    @Override
    public int getBack() {
        return clickBack;
    }

    public String getName() {
        return this.nameDish;
    }

    @Override
    public int getId() {
        return this.id;
    }

    public String getIngredients() {
        return this.ingredients;
    }

    public byte[] getStep() {
        return this.steps;
    }
}
