package com.v3.cookbook.moduls;

public class Categories {
    private int idCategories;
    private String nameDisher;
    private String nameUrlImageReview;

    public Categories(int idCategories2, String nameDisher2, String nameUrlImageReview2) {
        this.idCategories = idCategories2;
        this.nameDisher = nameDisher2;
        this.nameUrlImageReview = nameUrlImageReview2;
    }

    public int getIdCategories() {
        return this.idCategories;
    }

    public void setIdCategories(int idCategories2) {
        this.idCategories = idCategories2;
    }

    public String getNameDisher() {
        return this.nameDisher;
    }

    public void setNameDisher(String nameDisher2) {
        this.nameDisher = nameDisher2;
    }

    public String getNameUrlImageReview() {
        return this.nameUrlImageReview;
    }

    public void setNameUrlImageReview(String nameUrlImageReview2) {
        this.nameUrlImageReview = nameUrlImageReview2;
    }
}
