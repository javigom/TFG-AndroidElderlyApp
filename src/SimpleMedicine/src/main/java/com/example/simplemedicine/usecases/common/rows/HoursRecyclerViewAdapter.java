package com.example.simplemedicine.usecases.common.rows;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplemedicine.databinding.ItemHourListBinding;
import com.example.simplemedicine.model.HourModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HoursRecyclerViewAdapter extends RecyclerView.Adapter<HoursRecyclerViewAdapter.ViewHolder> {

    // ATTRIBUTES

    private List<HourModel> hourList;

    // CONSTRUCTOR

    public HoursRecyclerViewAdapter(boolean editMode, List<HourModel> hourList) {
        if(editMode) {
            this.hourList = hourList;
        }
        else {
            this.hourList = new ArrayList<>();
            this.hourList.add(new HourModel(7, 0));
            this.hourList.add(new HourModel(14, 0));
            this.hourList.add(new HourModel(21, 0));
        }
    }

    // METHODS

    @NonNull
    @Override
    public HoursRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemHourListBinding itemHourListBinding = ItemHourListBinding.inflate(layoutInflater, parent, false);
        return new HoursRecyclerViewAdapter.ViewHolder(itemHourListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull HoursRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.bindView(hourList.get(position));
    }

    @Override
    public int getItemCount() {
        return hourList.size();
    }

    public boolean addHour(Context context, HourModel hour) {
        for(HourModel h : hourList) {
            if(h.equals(hour)) {
                Toast.makeText(context, "No puedes añadir dos horas iguales", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        hourList.add(hour);
        Collections.sort(hourList);
        notifyItemInserted(hourList.indexOf(hour));
        return true;
    }

    public List<HourModel> getHourList() {
        return hourList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemHourListBinding itemHourListBinding;

        public ViewHolder(@NonNull ItemHourListBinding binding) {
            super(binding.getRoot());
            itemHourListBinding = binding;
        }

        public void bindView(HourModel hour) {
            itemHourListBinding.hourText.setText(hour.toString());
            itemHourListBinding.deleteButton.setOnClickListener(view -> {
                
                if(hourList.size() != 1) {
                    int index = hourList.indexOf(hour);
                    hourList.remove(hour);
                    notifyItemRemoved(index);
                }
                
                else {
                    Toast.makeText(view.getContext(), "Debe haber al menos una hora seleccionada", Toast.LENGTH_SHORT).show();
                }
                
                
            });
        }

    }
}
