package com.example.simpleLauncher.model;

import com.example.simpleLauncher.data.AppListDataSource;
import com.example.simpleLauncher.util.OrderTypeAppModel;

import java.util.ArrayList;
import java.util.List;

public class MainProvider {

    // ATTRIBUTES

    // CONSTRUCTOR

    // METHODS

    public static List<AppModel> requestShortcutApps() {
        List<AppModel> apps = AppListDataSource.fetchApps(OrderTypeAppModel.ORDER_BY_SHORTCUT_AND_NAME);
        List<AppModel> appsWithShortcut = new ArrayList<>();

        for(AppModel app: apps) {
            if(app.getPosition() != -1) {
                appsWithShortcut.add(app);
            }
        }

        return appsWithShortcut;
    }

}
