package com.example.simplemedicine.usecases.today;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.simplemedicine.model.NotificationModel;
import com.example.simplemedicine.provider.room.repository.Repository;

import java.util.List;

public class TodayViewModel extends AndroidViewModel {

    // ATTRIBUTES

    private final Repository repository;
    private final LiveData<List<NotificationModel>> allNotifications;


    // CONSTRUCTOR

    public TodayViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allNotifications = repository.getAllNotification();
    }

    // METHODS

    public LiveData<List<NotificationModel>> getAllNotifications() {
        return allNotifications;
    }

    public Repository getRepository() {
        return repository;
    }
}
