package com.example.simplemedicine.usecases.addmedication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.simplemedicine.databinding.ActivityAddMedicationBinding;
import com.example.simplemedicine.model.Medication;
import com.example.simplemedicine.usecases.addmedication.page.AddMedicationPageAdapter;
import com.example.simplemedicine.usecases.seemedication.SeeMedicationRouter;

public class AddMedicationActivity extends AppCompatActivity {

    // ATTRIBUTES

    private ActivityAddMedicationBinding binding;
    private AddMedicationViewModel viewModel;

    private AddMedicationPageAdapter pageAdapter;
    private int selection = 0;
    private boolean editMode;
    private Medication oldMedication;


    // METHODS

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddMedicationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(AddMedicationViewModel.class);

        Intent intent = getIntent();
        if(intent.getExtras() != null) {
            oldMedication = (Medication) intent.getSerializableExtra("medication");
            viewModel.setMedication((Medication) intent.getSerializableExtra("medication"));
            getSupportActionBar().setTitle("Editar un medicamento");
            editMode = true;
        }
        else {
            getSupportActionBar().setTitle("Añadir un medicamento");
            editMode = false;
        }

        viewModel.initPages();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
    }

    @Override
    public void onBackPressed() {
        binding.buttonPrev.callOnClick();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
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
                if(editMode)
                    new SeeMedicationRouter().launch(this, oldMedication);
                super.onBackPressed();
            }
            else {
                selection -= 1;
                binding.viewPager.setCurrentItem(selection, true);
            }
        });

        binding.buttonNext.setOnClickListener(v -> {
            if(pageAdapter.fillData(selection, viewModel.getMedication())) {
                if(selection == viewModel.getPages() - 1) {
                    if(editMode) {
                        viewModel.update();
                        Toast.makeText(this, "Medicamento editado con éxito", Toast.LENGTH_SHORT).show();
                        new SeeMedicationRouter().launch(this, viewModel.getMedication());
                        super.onBackPressed();
                    }
                    else {
                        viewModel.insert();
                        //Utils.createAlarmNotification(viewModel.getMedication(), getApplicationContext());
                        Toast.makeText(this, "Medicamento añadido con éxito", Toast.LENGTH_SHORT).show();
                    }

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

//    private void createAlarmNotification(){
//        Medication medication = viewModel.getMedication();
//        if(medication.isNotifications()){
//            for(HourModel hour: medication.getHours()) {
//                for(int i = 0; i < 7; i ++) {
//                    Boolean isDayWeek = medication.getWeekDays().get(WeekDayEnum.getWeekDay(i));
//                    if(isDayWeek != null && isDayWeek) {
//                        Intent intent = new Intent(AddMedicationActivity.this, AlarmReceiver.class);
//                        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//                        PendingIntent pendingIntent;
//                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M)
//                            pendingIntent = PendingIntent.getBroadcast(AddMedicationActivity.this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
//                        else
//                            pendingIntent = PendingIntent.getBroadcast(AddMedicationActivity.this, 0, intent, 0);
//                        Calendar calendar = Calendar.getInstance();
//                        calendar.set(Calendar.SECOND, 0);
//                        calendar.set(Calendar.MINUTE, hour.getMinutes());
//                        calendar.set(Calendar.HOUR_OF_DAY, hour.getHours());
//                        calendar.add(Calendar.DAY_OF_WEEK, i - 1);
//                        System.out.println(calendar.getTime());
//                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//                    }
//                }
//            }
//        }
//    }
}