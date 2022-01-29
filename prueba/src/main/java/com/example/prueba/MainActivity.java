package com.example.prueba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Intent intent = getPackageManager().getLaunchIntentForPackage("com.example.call");
        Intent intent = getPackageManager().getLaunchIntentForPackage("com.android.camera2");
        startActivity(intent);

        //installedApps();


        /*
        Intent intent = new Intent();
        intent.setPackage("com.example.call");

        PackageManager pm = getPackageManager();
        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, 0);
        Collections.sort(resolveInfos, new ResolveInfo.DisplayNameComparator(pm));

        if(resolveInfos.size() > 0) {
            ResolveInfo launchable = resolveInfos.get(0);
            ActivityInfo activity = launchable.activityInfo;
            ComponentName name = new ComponentName(activity.applicationInfo.packageName,
                    activity.name);
            Intent i = new Intent(Intent.ACTION_MAIN);

            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            i.setComponent(name);

            startActivity(i);
        }*/

    }

    private void installedApps() {
        List<PackageInfo> list = getPackageManager().getInstalledPackages(0);

        System.out.println("===============================");

        for (int i = 0; i<list.size(); i++){
            PackageInfo packageInfo = list.get(i);
            if((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0){
                String appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
                System.out.println(i + " " + packageInfo.packageName + "\n");
            }
        }

        System.out.println("===============================");
    }
}