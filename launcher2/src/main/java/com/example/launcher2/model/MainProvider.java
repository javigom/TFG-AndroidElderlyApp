package com.example.launcher2.model;

import com.example.launcher2.data.AppListDataSource;

import java.util.ArrayList;
import java.util.List;

public class MainProvider {

    // ATTRIBUTES

    // CONSTRUCTOR

    // METHODS

    public static List<AppModel> requestShortcutApps() {
        List<AppModel> apps = AppListDataSource.fetchApps();
        List<AppModel> appsWithShortcut = new ArrayList<>();

        for(AppModel app: apps) {
            if(app.getShortcut()) {
                appsWithShortcut.add(app);
            }
        }

        return appsWithShortcut;
    }

}
