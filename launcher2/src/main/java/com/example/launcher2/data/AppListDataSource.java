package com.example.launcher2.data;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;

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
        AppListDataSource.context = context.getApplicationContext();
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

            AppModel app = new AppModel(label, packageName, icon, -1);
            app.setPosition(myPreferences.getInt(app.getID(), -1));

            appList.add(app);
        }

        if(orderType == OrderTypeAppModel.ORDER_BY_SHORTCUT_AND_NAME) {
            Collections.sort(appList, new AppComparatorByShortcutAndName());
        }

        else if(orderType == OrderTypeAppModel.ORDER_BY_NAME) {
            Collections.sort(appList, new AppComparatorByName());
        }

        System.out.println("Number of shortcuts = " + myPreferences.getInt("numberOfShortcuts", 0));

        return appList;
    }

    public static void addShortcutApp(@NonNull AppModel app) {
        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        if(myPreferences.getInt(app.getID(), -1) == -1) {
            SharedPreferences.Editor myEditor = myPreferences.edit();
            int numShortcuts = myPreferences.getInt("numberOfShortcuts", 0);
            myEditor.putInt(app.getID(), numShortcuts);
            myEditor.putInt("numberOfShortcuts", numShortcuts + 1);
            //myEditor.commit();
            myEditor.apply();
        }
    }

    public static void removeShortcutApp(@NonNull AppModel app) {
        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        if(myPreferences.getInt(app.getID(), -1) != -1) {
            SharedPreferences.Editor myEditor = myPreferences.edit();
            myEditor.remove(app.getID());

            List<AppModel> list = fetchApps(OrderTypeAppModel.ORDER_BY_SHORTCUT_AND_NAME);

            int i = app.getPosition() + 1;
            while(list.get(i).getPosition() != -1) {
                myEditor.putInt(list.get(i).getID(), list.get(i).getPosition() - 1);
                i++;
            }

            int numShortcuts = myPreferences.getInt("numberOfShortcuts", 0);
            myEditor.putInt("numberOfShortcuts", numShortcuts - 1);
            //myEditor.commit();
            myEditor.apply();
        }
    }



}
