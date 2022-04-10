package com.example.simplemedicine.usecases.addmedication;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.widget.ViewPager2;

import com.example.simplemedicine.databinding.ActivityAddMedicationBinding;
import com.example.simplemedicine.usecases.addmedication.page.AddMedicationPageAdapter;
import com.example.simplemedicine.usecases.home.HomeRouter;

public class AddMedicationActivity extends AppCompatActivity {

    // ATTRIBUTES

    private ActivityAddMedicationBinding binding;
    private AddMedicationViewModel viewModel;

    private AddMedicationPageAdapter pageAdapter;
    private int selection = 0;


    // METHODS

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddMedicationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = ViewModelProviders.of(this).get(AddMedicationViewModel.class);
        getSupportActionBar().setTitle("Añadir un medicacamento");
        initView();
    }

    @Override
    public void onBackPressed() {
        binding.buttonPrev.callOnClick();
    }

    private void initView() {

        // Viewpager

        pageAdapter = new AddMedicationPageAdapter(this, viewModel.getFragments());
        binding.viewPager.setAdapter(pageAdapter);
        binding.viewPager.setUserInputEnabled(false);
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                selection = position;

                binding.buttonPrev.setText((position == 0)? "Cancelar": "Anterior");
                binding.buttonNext.setText((position == viewModel.getPages() - 1)? "Guardar": "Siguiente");
            }
        });

        // Buttons

        binding.buttonPrev.setOnClickListener(v -> {
            if(selection == 0) {
                super.onBackPressed();
            }
            else {
                selection -= 1;
                binding.viewPager.setCurrentItem(selection, true);
            }
        });

        binding.buttonNext.setOnClickListener(v -> {

            if(pageAdapter.saveData(selection, viewModel.getMedication())) {
                if(selection == viewModel.getPages() - 1) {
                    System.out.println(viewModel.getMedication());
                    Toast.makeText(this, "Medicamento añadido con éxito", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    selection += 1;
                    binding.viewPager.setCurrentItem(selection, true);
                }
            }
            else {
                Toast.makeText(this, "Debes rellenar los campos obligatorios", Toast.LENGTH_SHORT).show();
            }


        });

    }
}