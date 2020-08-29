package com.v3.cookbook.moduls;

public class IteamTab {

    private String id;
    private String titleTab;

    public IteamTab(String id, String titleTab2) {
        this.id = id;
        this.titleTab = titleTab2;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitleTab() {
        return this.titleTab;
    }

    public void setTitleTab(String titleTab2) {
        this.titleTab = titleTab2;
    }
}
