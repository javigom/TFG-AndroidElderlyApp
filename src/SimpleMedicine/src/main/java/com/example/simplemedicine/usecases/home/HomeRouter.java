package com.example.simplemedicine.usecases.home;

import android.content.Context;
import android.content.Intent;

import com.example.simplemedicine.usecases.base.BaseActivityRouter;

public class HomeRouter implements BaseActivityRouter {
    @Override
    public Intent intent(Context activity) {
        return new Intent(activity, HomeActivity.class);
    }
}
