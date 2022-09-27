package com.example.simpleLauncher.event;

import com.example.simpleLauncher.model.AppModel;

public interface RecyclerViewAppListInterface {
    void onItemClick(AppModel app);
    void onButtonClick(AppModel app);
    void onUpButtonClick(AppModel app1, AppModel app2);
    void onDownButtonClick(AppModel app1, AppModel app2);
}
