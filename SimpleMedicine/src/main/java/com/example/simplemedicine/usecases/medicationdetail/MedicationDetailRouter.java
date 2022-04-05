package com.example.simplemedicine.usecases.medicationdetail;

import android.content.Context;
import android.content.Intent;

import com.example.simplemedicine.model.Medication;
import com.example.simplemedicine.usecases.base.BaseActivityRouter;

public class MedicationDetailRouter implements BaseActivityRouter {

    @Override
    public Intent intent(Context activity) {
        return new Intent(activity, MedicationDetailActivity.class);
    }

    public void launch(Context activity, Medication medication) {
        activity.startActivity(intent(activity, medication));
    }

    public Intent intent(Context activity, Medication medication) {
        Intent intent = new Intent(activity, MedicationDetailActivity.class);
        intent.putExtra("medication", medication);
        return intent;
    }

}
