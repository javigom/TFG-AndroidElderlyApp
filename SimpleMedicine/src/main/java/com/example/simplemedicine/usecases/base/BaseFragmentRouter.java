package com.example.simplemedicine.usecases.base;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.simplemedicine.model.medication.Medication;

public interface BaseFragmentRouter {

    Fragment fragment();

    default void add(FragmentManager manager, int containerId, String tag) {
        manager.beginTransaction().add(containerId, fragment(), tag).commitAllowingStateLoss();
    }

    default void replace(FragmentManager manager, int containerId) {
        manager.beginTransaction().replace(containerId, fragment()).commit();
    }

    default void show(FragmentManager manager) {
        manager.beginTransaction().show(fragment()).commitAllowingStateLoss();
    }

    default void hide(FragmentManager manager) {
        manager.beginTransaction().hide(fragment()).commitAllowingStateLoss();
    }

    default void remove(FragmentManager manager) {
        manager.beginTransaction().remove(fragment()).commitAllowingStateLoss();
    }

    default boolean fillData(Medication medication) {
        return false;
    }

}
