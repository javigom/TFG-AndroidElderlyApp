package com.example.launcher2.viewmodel;

import android.app.Activity;
import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.launcher2.model.AppListProvider;
import com.example.launcher2.model.AppModel;
import com.example.launcher2.util.OrderTypeAppModel;

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

    public void updateAppList(OrderTypeAppModel orderType) {
        appListLiveData.setValue(AppListProvider.requestApps(orderType));
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

    public void clickShortcut(AppModel app) {
        if(app.getPosition() != - 1) AppListProvider.removeShortcut(app);
        else AppListProvider.addShortcut(app);
    }

}
