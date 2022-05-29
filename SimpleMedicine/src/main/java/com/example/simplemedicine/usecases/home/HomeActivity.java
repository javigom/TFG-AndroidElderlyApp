package com.example.simplemedicine.usecases.home;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.simplemedicine.R;
import com.example.simplemedicine.databinding.ActivityHomeBinding;
import com.example.simplemedicine.usecases.addmedication.AddMedicationRouter;
import com.example.simplemedicine.usecases.medication.MedicationRouter;
import com.example.simplemedicine.usecases.today.TodayRouter;

public class HomeActivity extends AppCompatActivity {

    // ATTRIBUTES

    private ActivityHomeBinding binding;
    private HomeViewModel viewModel;

    // Fragments
    private MedicationRouter medicationFragment;
    private TodayRouter todayFragment;

    private int selectedItem;

    public static final String CHANNEL_ID = "_main_channel";
    // METHODS

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        selectedItem = -1;
        initView();
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, getString(R.string.notification_channel_name), importance);
            channel.setDescription("main channel");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void initView() {
        binding.floatingButton.setOnClickListener(v -> new AddMedicationRouter().launch(this));
        loadTabs();
        defaultTab();
    }

    private void loadTabs() {
        if(medicationFragment != null)
            medicationFragment.remove(getSupportFragmentManager());
        if(todayFragment != null)
            todayFragment.remove(getSupportFragmentManager());

        medicationFragment = new MedicationRouter();
        todayFragment = new TodayRouter();

        binding.homeBottomNavigationView.setOnItemSelectedListener(item -> {
            if (selectedItem != item.getItemId()) {
                selectedItem = item.getItemId();

                if (item.getItemId() == R.id.home_menu_medication) {
                    todayFragment.hide(getSupportFragmentManager());
                    if (getSupportFragmentManager().findFragmentByTag(String.valueOf(R.id.home_menu_medication)) == null) {
                        medicationFragment.add(getSupportFragmentManager(), R.id.homeContainer, String.valueOf(R.id.home_menu_medication));
                    }
                    medicationFragment.show(getSupportFragmentManager());
                } else if (item.getItemId() == R.id.home_menu_today) {
                    medicationFragment.hide(getSupportFragmentManager());
                    if (getSupportFragmentManager().findFragmentByTag(String.valueOf(R.id.home_menu_today)) == null) {
                        todayFragment.add(getSupportFragmentManager(), R.id.homeContainer, String.valueOf(R.id.home_menu_today));
                    }
                    todayFragment.show(getSupportFragmentManager());
                }
                return true;
            }
            return false;
        });
    }

    private void defaultTab() {
        binding.homeBottomNavigationView.setSelectedItemId(viewModel.defaultTab());
    }

}