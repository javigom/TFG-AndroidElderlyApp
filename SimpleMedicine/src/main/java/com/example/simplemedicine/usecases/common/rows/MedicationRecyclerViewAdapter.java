package com.example.simplemedicine.usecases.common.rows;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplemedicine.databinding.ItemMedicationListBinding;
import com.example.simplemedicine.model.Medication;
import com.example.simplemedicine.usecases.medicationdetail.MedicationDetailRouter;

import java.util.ArrayList;
import java.util.List;

public class MedicationRecyclerViewAdapter extends RecyclerView.Adapter<MedicationRecyclerViewAdapter.ViewHolder> {
    // ATTRIBUTES

    private List<Medication> medicationModelList;

    // CONSTRUCTOR

    public MedicationRecyclerViewAdapter() {
        this.medicationModelList = new ArrayList<>();
    }

    // METHODS

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemMedicationListBinding itemMedicationListBinding = ItemMedicationListBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(itemMedicationListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicationRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.bindView(medicationModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return medicationModelList.size();
    }

    public void updateMedicationList(final List<Medication> medicationModelList) {
        this.medicationModelList.clear();
        this.medicationModelList = medicationModelList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemMedicationListBinding itemMedicationListBinding;

        public ViewHolder(@NonNull ItemMedicationListBinding binding) {
            super(binding.getRoot());
            itemMedicationListBinding = binding;
            // itemMedicationListBinding.linearLayout.setOnClickListener(view -> new MedicationDetailRouter().launch(view.getContext()));

        }

        public void bindView(Medication medication) {
            itemMedicationListBinding.textViewIcon.setText(medication.getName());
            itemMedicationListBinding.linearLayout.setOnClickListener(view -> new MedicationDetailRouter().launch(view.getContext(), medication));
        }

    }

}

