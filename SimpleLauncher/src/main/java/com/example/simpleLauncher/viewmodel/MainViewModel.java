package com.example.simpleLauncher.viewmodel;

import android.app.Activity;
import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.simpleLauncher.model.AppModel;
import com.example.simpleLauncher.model.MainProvider;

import java.util.List;

public class MainViewModel extends ViewModel {

    // ATTRIBUTES

    private MutableLiveData<List<AppModel>> appListLiveData;


    // CONSTRUCTOR

    public MainViewModel() {
        appListLiveData = new MutableLiveData<>();
    }

    // METHODS

    public LiveData<List<AppModel>> getShortcutAppList() {
        return appListLiveData;
    }

    public void updateShortcutAppList() {
        appListLiveData.setValue(MainProvider.requestShortcutApps());
    }

    public void launchApp(AppModel app, Activity activity) {
        try {
//            Intent intent = new Intent(Intent.ACTION_MAIN);
//            intent.addCategory(Intent.CATEGORY_LAUNCHER);
//            intent.setPackage(app.getPackageName());
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            activity.startActivity(intent);

            Intent launchAppIntent = activity.getApplicationContext().getPackageManager().getLaunchIntentForPackage(app.getPackageName());
            if (launchAppIntent != null)
                activity.getApplicationContext().startActivity(launchAppIntent);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
