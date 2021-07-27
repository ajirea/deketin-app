package com.stdev.deketin.models;

public class MenuModel {
    private String menuText;
    private int icon;

    public MenuModel(String menuText, int icon) {
        this.menuText = menuText;
        this.icon = icon;
    }

    public String getMenuText() {
        return menuText;
    }

    public void setMenuText(String menuText) {
        this.menuText = menuText;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
