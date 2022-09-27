package com.example.simplemedicine.usecases.base;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

public interface BaseActivityRouter {

    Intent intent(Context activity);

    default void launch(@NonNull Context activity){
        activity.startActivity(intent(activity));
    }

}
