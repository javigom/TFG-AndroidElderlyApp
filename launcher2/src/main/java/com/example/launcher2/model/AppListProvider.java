package com.example.launcher2.model;

import com.example.launcher2.data.AppListDataSource;

import java.util.List;

public class AppListProvider {

    // ATTRIBUTES

    // CONSTRUCTOR

    // METHODS

    public static List<AppModel> requestApps() {
        List<AppModel> appModelList = AppListDataSource.fetchApps();
        return appModelList;
    }

    public static void addShortcut(String appPackage, String label) {
        AppListDataSource.addShortcutApp(appPackage, label);
    }

    public static void removeShortcut(String appPackage, String label) {
        AppListDataSource.removeShortcutApp(appPackage, label);
    }

}
