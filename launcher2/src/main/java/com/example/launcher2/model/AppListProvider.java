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

    public static void addShortcut(AppModel app) {
        AppListDataSource.addShortcutApp(app);
    }

    public static void removeShortcut(AppModel app) {
        AppListDataSource.removeShortcutApp(app);
    }

}
