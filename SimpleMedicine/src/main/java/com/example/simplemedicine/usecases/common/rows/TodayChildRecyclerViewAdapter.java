package com.example.simplemedicine.usecases.common.rows;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplemedicine.databinding.ItemTodayChildListBinding;
import com.example.simplemedicine.model.medication.Medication;

import java.util.ArrayList;
import java.util.List;

public class TodayChildRecyclerViewAdapter extends RecyclerView.Adapter<TodayChildRecyclerViewAdapter.ViewHolder> {

    // ATTRIBUTES

    private List<Medication> medicationList;

    // CONSTRUCTOR

    public TodayChildRecyclerViewAdapter() {
        this.medicationList = new ArrayList<>();
    }

    // METHODS

    @NonNull
    @Override
    public TodayChildRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemTodayChildListBinding itemTodayChildListBinding = ItemTodayChildListBinding.inflate(layoutInflater, parent, false);
        return new TodayChildRecyclerViewAdapter.ViewHolder(itemTodayChildListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TodayChildRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.bindView(medicationList.get(position));
    }

    @Override
    public int getItemCount() {
        return medicationList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateMedicationList(List<Medication> medicationList) {
        this.medicationList.clear();
        this.medicationList = medicationList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemTodayChildListBinding itemTodayChildListBinding;

        public ViewHolder(@NonNull ItemTodayChildListBinding binding) {
            super(binding.getRoot());
            itemTodayChildListBinding = binding;
        }

        public void bindView(Medication medication) {
            itemTodayChildListBinding.name.setText(medication.getName());
            itemTodayChildListBinding.constraintLayout.getBackground().setTint(medication.getColor());
        }
    }
}

