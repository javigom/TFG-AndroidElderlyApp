package com.example.simpleLauncher.model;

import com.example.simpleLauncher.data.AppListDataSource;
import com.example.simpleLauncher.util.OrderTypeAppModel;

import java.util.List;

public class AppListProvider {

    // ATTRIBUTES

    // CONSTRUCTOR

    // METHODS

    public static List<AppModel> requestApps(OrderTypeAppModel orderType) {
        List<AppModel> appModelList = AppListDataSource.fetchApps(orderType);
        return appModelList;
    }

    public static void addShortcut(AppModel app) {
        AppListDataSource.addShortcutApp(app);
    }

    public static void removeShortcut(AppModel app) {
        AppListDataSource.removeShortcutApp(app);
    }

    public static void swapApps(AppModel app1, AppModel app2) {
        AppListDataSource.swapApps(app1, app2);
    }

}