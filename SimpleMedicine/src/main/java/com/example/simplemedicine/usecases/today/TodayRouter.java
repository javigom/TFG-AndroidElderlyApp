package com.example.simplemedicine.usecases.today;

import androidx.fragment.app.Fragment;

import com.example.simplemedicine.usecases.base.BaseFragmentRouter;

public class TodayRouter implements BaseFragmentRouter {

    private TodayFragment instance;

    public TodayRouter() {
        instance = null;
    }

    @Override
    public Fragment fragment() {
        if(instance == null) {
            instance = new TodayFragment();
        }
        return instance;
    }
}
