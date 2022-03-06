package com.example.launcher2.model;

import android.graphics.drawable.Drawable;

public class AppModel {

    // ATTRIBUTES

    private String label;
    private String packageName;
    private Drawable icon;

    // CONSTRUCTOR

    public AppModel(String label, String packageName, Drawable icon) {
        this.label = label;
        this.packageName = packageName;
        this.icon = icon;
    }

    // METHODS

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "AppModel{" +
                "label='" + label + '\'' +
                ", packageName='" + packageName + '\'' +
                ", icon=" + icon +
                '}';
    }

}
