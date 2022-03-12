package com.example.launcher2.data;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;

import com.example.launcher2.model.AppModel;
import com.example.launcher2.util.AppComparatorByName;
import com.example.launcher2.util.AppComparatorByShortcutAndName;
import com.example.launcher2.util.OrderTypeAppModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AppListDataSource {

    // ATTRIBUTES

    private static Context context;

    // CONSTRUCTOR

    // METHODS

    public static void updateContext(Context context) {
        AppListDataSource.context = context;
    }

    public static List<AppModel> fetchApps(OrderTypeAppModel orderType){

        List<AppModel> appList = new ArrayList<>();
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> allApps = packageManager.queryIntentActivities(intent, 0);
        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        for (ResolveInfo ri : allApps) {
            String label = ri.loadLabel(packageManager).toString();
            String packageName = ri.activityInfo.packageName;
            Drawable icon = ri.activityInfo.loadIcon(packageManager);
            Boolean isShortcut = myPreferences.getBoolean(packageName, false) && myPreferences.getBoolean(label, false);
            AppModel app = new AppModel(label, packageName, icon, isShortcut);
            appList.add(app);
        }

        if(orderType == OrderTypeAppModel.ORDER_BY_SHORTCUT_AND_NAME) {
            Collections.sort(appList, new AppComparatorByShortcutAndName());
        }

        else if(orderType == OrderTypeAppModel.ORDER_BY_NAME) {
            Collections.sort(appList, new AppComparatorByName());
        }

        return appList;
    }

    public static void addShortcutApp(String appPackage, String label) {
        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor myEditor = myPreferences.edit();
        myEditor.putBoolean(appPackage, true);
        myEditor.putBoolean(label, true);
        myEditor.commit();
    }

    public static void removeShortcutApp(String appPackage, String label) {
        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor myEditor = myPreferences.edit();
        myEditor.remove(appPackage);
        myEditor.remove(label);
        myEditor.commit();
    }

}
