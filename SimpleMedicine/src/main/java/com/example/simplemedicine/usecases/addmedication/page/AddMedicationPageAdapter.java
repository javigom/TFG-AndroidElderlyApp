package com.example.simplemedicine.usecases.addmedication.page;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.simplemedicine.model.medication.Medication;
import com.example.simplemedicine.usecases.base.BaseFragmentRouter;

import java.util.List;

public class AddMedicationPageAdapter extends FragmentStateAdapter {

    // ATTRIBUTES

    private List<BaseFragmentRouter> pages;


    // CONSTRUCTOR

    public AddMedicationPageAdapter(@NonNull AppCompatActivity context, List<BaseFragmentRouter> pages) {
        super(context);
        this.pages = pages;
    }


    // METHODS

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return pages.get(position).fragment();
    }

    @Override
    public int getItemCount() {
        return pages.size();
    }

    public boolean fillData(int selection, Medication medication) {
        return pages.get(selection).fillData(medication);
    }

}
