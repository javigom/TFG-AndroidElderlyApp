package com.example.simplemedicine.usecases.base;

import android.content.Context;
import android.content.Intent;

public interface BaseActivityRouter {

    Intent intent(Context activity);

    default void launch(Context activity){
        activity.startActivity(intent(activity));
    }

}
