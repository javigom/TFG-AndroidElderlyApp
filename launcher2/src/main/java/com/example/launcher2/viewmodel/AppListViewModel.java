package com.example.launcher2.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.launcher2.data.AppListDataSource;
import com.example.launcher2.model.AppListProvider;
import com.example.launcher2.model.AppModel;

import java.util.ArrayList;
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

}
