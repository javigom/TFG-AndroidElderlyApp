package com.example.simplemedicine.usecases.home;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.simplemedicine.R;
import com.example.simplemedicine.model.Medication;
import com.example.simplemedicine.model.NotificationModel;
import com.example.simplemedicine.provider.room.repository.Repository;
import com.example.simplemedicine.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private Context context;

    public HomeViewModel(Application application) {
        super(application);
        context = application;
    }

    public int defaultTab() {
        return R.id.home_menu_today;
    }

    public void updateAlarms(List<NotificationModel> list) {
        Utils.createAlarm(list, context);
    }

}
