package com.example.launcher2.viewmodel;

import android.app.Activity;
import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.launcher2.data.AppListDataSource;
import com.example.launcher2.model.AppListProvider;
import com.example.launcher2.model.AppModel;

import java.util.List;

public class AppListViewModel extends ViewModel {

    // ATTRIBUTES

    private MutableLiveData<List<AppModel>> appListLiveData;

    // CONSTRUCTOR

    public AppListViewModel() {
        appListLiveData = new MutableLiveData<>();
    }

    // METHODS

    public LiveData<List<AppModel>> getAppList() {
        return appListLiveData;
    }

    public void updateAppList(AppListDataSource appListDataSource) {
        appListLiveData.setValue(AppListProvider.requestApps(appListDataSource));
    }

    public void launchApp(AppModel app, Activity activity) {

        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.setPackage(app.getPackageName());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //intent.setClassName(appModel.getPackageName(), className);
            activity.startActivity(intent);
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

}
