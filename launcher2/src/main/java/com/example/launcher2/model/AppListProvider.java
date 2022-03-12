package com.example.launcher2.model;

import com.example.launcher2.data.AppListDataSource;
import com.example.launcher2.util.OrderTypeAppModel;

import java.util.List;

public class AppListProvider {

    // ATTRIBUTES

    // CONSTRUCTOR

    // METHODS

    public static List<AppModel> requestApps(OrderTypeAppModel orderType) {
        List<AppModel> appModelList = AppListDataSource.fetchApps(orderType);
        return appModelList;
    }

    public static void addShortcut(String appPackage, String label) {
        AppListDataSource.addShortcutApp(appPackage, label);
    }

    public static void removeShortcut(String appPackage, String label) {
        AppListDataSource.removeShortcutApp(appPackage, label);
    }

}
