package com.example.launcher2.data;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;

import com.example.launcher2.model.AppModel;

import java.util.ArrayList;
import java.util.List;

public class AppListDataSource {

    // ATTRIBUTES

    private static Context context;


    // CONSTRUCTOR

    public AppListDataSource(Context context) {
        this.context = context;
    }

    // METHODS

    public static List<AppModel> fetchApps(){
        List<AppModel> appsList = new ArrayList<>();

        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> allApps = packageManager.queryIntentActivities(intent, 0);

        for (ResolveInfo ri : allApps) {
            String name = ri.loadLabel(packageManager).toString();
            String packageName = ri.activityInfo.packageName;
            Drawable icon = ri.activityInfo.loadIcon(packageManager);
            AppModel app = new AppModel(name, packageName, icon);
            appsList.add(app);
            //Log.intent(" Log package ",app.packageName.toString());
        }

        return appsList;
    }

}
