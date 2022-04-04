package com.example.simplemedicine.usecases.medicationdetail;

import android.content.Context;
import android.content.Intent;

import com.example.simplemedicine.model.MedicationModel;
import com.example.simplemedicine.usecases.base.BaseActivityRouter;

public class MedicationDetailRouter implements BaseActivityRouter {

    @Override
    public Intent intent(Context activity) {
        return new Intent(activity, MedicationDetailActivity.class);
    }

    public void launch(Context activity, MedicationModel medication) {
        activity.startActivity(intent(activity, medication));
    }

    public Intent intent(Context activity, MedicationModel medication) {
        Intent intent = new Intent(activity, MedicationDetailActivity.class);
        intent.putExtra("medication", medication);
        return intent;
    }

}
