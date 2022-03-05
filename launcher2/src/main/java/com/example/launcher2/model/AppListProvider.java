package com.example.launcher2.model;

import com.example.launcher2.data.AppListDataSource;

import java.util.List;

public class AppListProvider {

    // ATTRIBUTES

    // CONSTRUCTOR

    // METHODS

    public static List<AppModel> requestApps(AppListDataSource appListDataSource) {
        List<AppModel> appModelList = appListDataSource.fetchApps();

        return appModelList;
    }

}
