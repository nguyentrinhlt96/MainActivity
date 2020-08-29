package com.v3.cookbook.moduls;

public class LoadInfoDetailDisher {
    private int id;
    private String url;
    private String nameDish;
    private String Ingredients;
    private byte[] step;
    private String favaurite;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNameDish() {
        return nameDish;
    }

    public void setNameDish(String nameDish) {
        this.nameDish = nameDish;
    }

    public String getIngredients() {
        return Ingredients;
    }

    public void setIngredients(String ingredients) {
        Ingredients = ingredients;
    }

    public byte[] getStep() {
        return step;
    }

    public void setStep(byte[] step) {
        this.step = step;
    }

    public String getFavaurite() {
        return favaurite;
    }

    public void setFavaurite(String favaurite) {
        this.favaurite = favaurite;
    }
}
