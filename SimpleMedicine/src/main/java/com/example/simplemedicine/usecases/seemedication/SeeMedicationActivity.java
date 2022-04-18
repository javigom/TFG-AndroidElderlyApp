package com.example.simplemedicine.usecases.seemedication;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.simplemedicine.R;
import com.example.simplemedicine.databinding.ActivitySeeMedicationBinding;
import com.example.simplemedicine.model.medication.Medication;
import com.example.simplemedicine.usecases.addmedication.AddMedicationRouter;

public class SeeMedicationActivity extends AppCompatActivity {

    // ATTRIBUTES

    private ActivitySeeMedicationBinding binding;
    private SeeMedicationViewModel viewModel;
    private Medication medication;


    // METHODS

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySeeMedicationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        if(intent.getExtras() != null){
            medication = (Medication) intent.getSerializableExtra("medication");

            // View Model
            viewModel = new ViewModelProvider(this).get(SeeMedicationViewModel.class);
            getSupportActionBar().setTitle("Ver un medicamento");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            initView();
        }

        else {
            Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView() {
        binding.nameText.setText(medication.getName());
        binding.descriptionText.setText(medication.getDescription());
        binding.unitsText.setText(medication.getUnitsPerDosisString());
        binding.daysText.setText(medication.getWeekDaysString());
        binding.hoursText.setText(medication.getHoursString());
        binding.startText.setText("Desde: " + medication.getStartDateString());
        binding.endText.setText("Hasta: " + medication.getEndDateString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.see_medication_menu,  menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       switch (item.getItemId()) {
           case android.R.id.home:
               onBackPressed();
               return true;
           case R.id.edit:
               new AddMedicationRouter().launch(this, medication);
               finish();
               return true;
           case R.id.delete:
               viewModel.delete(medication);
               Toast.makeText(this, "Medicamento eliminado", Toast.LENGTH_SHORT).show();
               onBackPressed();
               return true;
        }

        return super.onOptionsItemSelected(item);
    }

}