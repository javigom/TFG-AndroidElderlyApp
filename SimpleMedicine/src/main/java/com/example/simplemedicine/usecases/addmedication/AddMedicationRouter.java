package com.example.simplemedicine.usecases.addmedication;

import android.content.Context;
import android.content.Intent;

import com.example.simplemedicine.usecases.base.BaseActivityRouter;

public class AddMedicationRouter implements BaseActivityRouter {
    @Override
    public Intent intent(Context activity) {
        return new Intent(activity, AddMedicationActivity.class);
    }
}
