package com.example.simplemedicine.usecases.addmedication;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.simplemedicine.model.medication.Medication;
import com.example.simplemedicine.usecases.base.BaseActivityRouter;
import com.example.simplemedicine.usecases.seemedication.SeeMedicationActivity;

public class AddMedicationRouter implements BaseActivityRouter {

    @Override
    public Intent intent(Context activity) {
        return new Intent(activity, AddMedicationActivity.class);
    }

    public void launch(@NonNull Context activity, Medication medication) {
        activity.startActivity(intent(activity, medication));
    }

    public Intent intent(Context activity, Medication medication) {
        Intent intent = new Intent(activity, AddMedicationActivity.class);
        intent.putExtra("medication", medication);
        return intent;
    }
}
