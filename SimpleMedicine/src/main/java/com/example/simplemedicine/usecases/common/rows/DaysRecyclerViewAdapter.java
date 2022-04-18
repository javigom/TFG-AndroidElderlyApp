package com.example.simplemedicine.usecases.common.rows;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplemedicine.databinding.ItemDayListBinding;
import com.example.simplemedicine.model.hour.HourModel;
import com.example.simplemedicine.model.hour.HourComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DaysRecyclerViewAdapter extends RecyclerView.Adapter<DaysRecyclerViewAdapter.ViewHolder> {

    // ATTRIBUTES

    private List<HourModel> hourList;

    // CONSTRUCTOR

    public DaysRecyclerViewAdapter(boolean editMode, List<HourModel> hourList) {
        if(editMode) {
            this.hourList = hourList;
        }
        else {
            hourList = new ArrayList<>();
            hourList.add(new HourModel(7, 0));
            hourList.add(new HourModel(14, 0));
            hourList.add(new HourModel(21, 0));
        }
    }

    // METHODS

    @NonNull
    @Override
    public DaysRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemDayListBinding itemDayListBinding = ItemDayListBinding.inflate(layoutInflater, parent, false);
        return new DaysRecyclerViewAdapter.ViewHolder(itemDayListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull DaysRecyclerViewAdapter.ViewHolder holder, int position) {
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
        Collections.sort(hourList, new HourComparator());
        notifyItemInserted(hourList.indexOf(hour));
        return true;
    }

    public List<HourModel> getHourList() {
        return hourList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemDayListBinding itemDayListBinding;

        public ViewHolder(@NonNull ItemDayListBinding binding) {
            super(binding.getRoot());
            itemDayListBinding = binding;
        }

        public void bindView(HourModel hour) {
            itemDayListBinding.hourText.setText(hour.toString());
            itemDayListBinding.deleteButton.setOnClickListener(view -> {
                
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
