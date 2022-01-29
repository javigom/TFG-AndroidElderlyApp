package com.example.launcher;

import android.graphics.drawable.Icon;

public class AppModel {
    private String appName;
    private String appPackage;
    private int appIcon;
    private int appColor;

    public AppModel(String appName, String appPackage, int appIcon, int appColor){
        this.appName = appName;
        this.appPackage = appPackage;
        this.appIcon = appIcon;
        this.appColor = appColor;
    }

    public String getAppName(){
        return appName;
    }

    public String getAppPackage(){
        return appPackage;
    }

    public int getAppIcon(){
        return appIcon;
    }

    public int getAppColor() {
        return appColor;
    }

}
