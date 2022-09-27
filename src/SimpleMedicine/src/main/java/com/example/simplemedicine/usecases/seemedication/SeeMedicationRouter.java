package com.example.simplemedicine.usecases.seemedication;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.simplemedicine.model.Medication;
import com.example.simplemedicine.usecases.base.BaseActivityRouter;

public class SeeMedicationRouter implements BaseActivityRouter {

    @Override
    public Intent intent(Context activity) {
        return new Intent(activity, SeeMedicationActivity.class);
    }

    public void launch(@NonNull Context activity, Medication medication) {
        activity.startActivity(intent(activity, medication));
    }

    public Intent intent(Context activity, Medication medication) {
        Intent intent = new Intent(activity, SeeMedicationActivity.class);
        intent.putExtra("medication", medication);
        return intent;
    }

}
