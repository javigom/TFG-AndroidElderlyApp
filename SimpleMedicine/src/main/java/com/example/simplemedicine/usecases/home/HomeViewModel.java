package com.example.simplemedicine.usecases.home;

import androidx.lifecycle.ViewModel;

import com.example.simplemedicine.R;

public class HomeViewModel extends ViewModel {

    public int defaultTab() {
        return R.id.home_menu_today;
    }

}
