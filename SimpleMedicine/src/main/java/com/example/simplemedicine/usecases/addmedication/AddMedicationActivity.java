package com.example.simplemedicine.usecases.addmedication;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.widget.ViewPager2;

import com.example.simplemedicine.databinding.ActivityAddMedicationBinding;
import com.example.simplemedicine.usecases.addmedication.page.AddMedicationPageAdapter;

public class AddMedicationActivity extends AppCompatActivity {

    // ATTRIBUTES

    private ActivityAddMedicationBinding binding;
    private AddMedicationViewModel viewModel;

    private int selection = 0;


    // METHODS

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddMedicationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = ViewModelProviders.of(this).get(AddMedicationViewModel.class);
        initView();
    }

    private void initView() {

        // Viewpager
        binding.viewPager.setAdapter(new AddMedicationPageAdapter(this, viewModel.getFragments()));
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                selection = position;
            }
        });

        // Buttons
        binding.buttonPrev.setOnClickListener(v -> {
            selection -= 1;
            binding.viewPager.setCurrentItem(selection, true);
        });

        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selection == viewModel.getPages() - 1) {
                    // ACABA
                }
                else {
                    selection += 1;
                    binding.viewPager.setCurrentItem(selection, true);
                }
            }
        });

    }
}