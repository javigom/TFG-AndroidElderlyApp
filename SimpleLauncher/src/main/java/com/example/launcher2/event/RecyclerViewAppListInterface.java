package com.example.launcher2.event;

import com.example.launcher2.model.AppModel;

public interface RecyclerViewAppListInterface {
    void onItemClick(AppModel app);
    void onButtonClick(AppModel app);
    void onUpButtonClick(AppModel app1, AppModel app2);
    void onDownButtonClick(AppModel app1, AppModel app2);
}
