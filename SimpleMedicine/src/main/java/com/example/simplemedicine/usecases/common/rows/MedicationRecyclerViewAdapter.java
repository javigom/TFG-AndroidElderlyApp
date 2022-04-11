package com.example.simplemedicine.usecases.common.rows;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplemedicine.databinding.ItemMedicationListBinding;
import com.example.simplemedicine.model.Medication;
import com.example.simplemedicine.usecases.seemedication.SeeMedicationRouter;

import java.util.ArrayList;
import java.util.List;

public class MedicationRecyclerViewAdapter extends RecyclerView.Adapter<MedicationRecyclerViewAdapter.ViewHolder> {

    // ATTRIBUTES

    private List<Medication> medicationList;

    // CONSTRUCTOR

    public MedicationRecyclerViewAdapter() {
        this.medicationList = new ArrayList<>();
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
        holder.bindView(medicationList.get(position));
    }

    @Override
    public int getItemCount() {
        return medicationList.size();
    }

    public void updateMedicationList(final List<Medication> medicationModelList) {
        this.medicationList.clear();
        this.medicationList = medicationModelList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemMedicationListBinding itemMedicationListBinding;

        public ViewHolder(@NonNull ItemMedicationListBinding binding) {
            super(binding.getRoot());
            itemMedicationListBinding = binding;
        }

        public void bindView(Medication medication) {
            itemMedicationListBinding.textViewIcon.setText(medication.getName());
            itemMedicationListBinding.linearLayout.setOnClickListener(view -> new SeeMedicationRouter().launch(view.getContext(), medication));
        }

    }

}

