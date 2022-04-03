package com.example.simplemedicine.usecases.common.rows;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplemedicine.databinding.ItemMedicationListBinding;
import com.example.simplemedicine.model.MedicationModel;

import java.util.ArrayList;
import java.util.List;

public class MedicationRecyclerViewAdapter extends RecyclerView.Adapter<MedicationRecyclerViewAdapter.ViewHolder> {
    // ATTRIBUTES

    private List<MedicationModel> medicationModelList;

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

    public void updateMedicationList(final List<MedicationModel> medicationModelList) {
        this.medicationModelList.clear();
        this.medicationModelList = medicationModelList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemMedicationListBinding itemMedicationListBinding;

        public ViewHolder(@NonNull ItemMedicationListBinding binding) {
            super(binding.getRoot());
            this.itemMedicationListBinding = binding;
            //this.itemMedicationListBinding.linearLayout.setOnClickListener(view -> recyclerViewAppListInterface.onItemClick(medicationModelList.get(getAdapterPosition())));
        }

        public void bindView(MedicationModel medication) {
            itemMedicationListBinding.textViewIcon.setText(medication.getName());
        }

    }

}

