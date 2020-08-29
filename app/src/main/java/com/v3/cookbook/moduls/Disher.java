package com.v3.cookbook.moduls;

public class Disher {
    private String favaurite;
    private int id;
    private int idCategory;
    private String ingredients;
    private byte[] instruction;
    private String kcal;
    private String nameDisher;
    private String serving;
    private String tagName;
    private String time;
    private String urlImageDisher;

    public Disher(int id, String nameDisher2, String urlImageDisher2, String time2, String serving2, String kcal2, String ingredients2, byte[] instruction2, int idCategory2, String tagName2, String favaurite2) {
        this.id = id;
        this.nameDisher = nameDisher2;
        this.urlImageDisher = urlImageDisher2;
        this.time = time2;
        this.serving = serving2;
        this.kcal = kcal2;
        this.ingredients = ingredients2;
        this.instruction = instruction2;
        this.idCategory = idCategory2;
        this.tagName = tagName2;
        this.favaurite = favaurite2;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameDisher() {
        return this.nameDisher;
    }

    public void setNameDisher(String nameDisher2) {
        this.nameDisher = nameDisher2;
    }

    public String getUrlImageDisher() {
        return this.urlImageDisher;
    }

    public void setUrlImageDisher(String urlImageDisher2) {
        this.urlImageDisher = urlImageDisher2;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time2) {
        this.time = time2;
    }

    public String getServing() {
        return this.serving;
    }

    public void setServing(String serving2) {
        this.serving = serving2;
    }

    public String getKcal() {
        return this.kcal;
    }

    public void setKcal(String kcal2) {
        this.kcal = kcal2;
    }

    public String getIngredients() {
        return this.ingredients;
    }

    public void setIngredients(String ingredients2) {
        this.ingredients = ingredients2;
    }

    public byte[] getInstruction() {
        return this.instruction;
    }

    public void setInstruction(byte[] instruction2) {
        this.instruction = instruction2;
    }

    public String getFavaurite() {
        return this.favaurite;
    }

    public void setFavaurite(String favaurite2) {
        this.favaurite = favaurite2;
    }

    public int getIdCategory() {
        return this.idCategory;
    }

    public void setIdCategory(int idCategory2) {
        this.idCategory = idCategory2;
    }

    public String getTagName() {
        return this.tagName;
    }

    public void setTagName(String tagName2) {
        this.tagName = tagName2;
    }
}
