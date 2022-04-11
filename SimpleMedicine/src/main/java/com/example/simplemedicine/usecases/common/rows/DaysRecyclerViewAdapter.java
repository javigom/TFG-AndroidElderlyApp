package com.example.simplemedicine.usecases.common.rows;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplemedicine.databinding.ItemDayListBinding;
import com.example.simplemedicine.model.Hour;
import com.example.simplemedicine.util.HourComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DaysRecyclerViewAdapter extends RecyclerView.Adapter<DaysRecyclerViewAdapter.ViewHolder> {

    // ATTRIBUTES

    private List<Hour> hourList;

    // CONSTRUCTOR

    public DaysRecyclerViewAdapter() {
        hourList = new ArrayList<>();
        hourList.add(new Hour(7, 0));
        hourList.add(new Hour(14, 0));
        hourList.add(new Hour(21, 0));
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

    public boolean addHour(Hour hour) {
        for(Hour h : hourList) {
            if(h.equals(hour)) {
                return false;
            }
        }
        hourList.add(hour);
        Collections.sort(hourList, new HourComparator());
        notifyItemInserted(hourList.indexOf(hour));
        return true;
    }

    public List<Hour> getHourList() {
        return hourList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemDayListBinding itemDayListBinding;

        public ViewHolder(@NonNull ItemDayListBinding binding) {
            super(binding.getRoot());
            itemDayListBinding = binding;
        }

        public void bindView(Hour hour) {
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
