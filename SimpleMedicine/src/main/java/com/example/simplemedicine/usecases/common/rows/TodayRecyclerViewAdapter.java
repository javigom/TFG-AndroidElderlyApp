package com.example.simplemedicine.usecases.common.rows;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplemedicine.databinding.ItemTodayListBinding;
import com.example.simplemedicine.model.date.DateModel;
import com.example.simplemedicine.model.hour.HourModel;
import com.example.simplemedicine.model.medication.Medication;
import com.example.simplemedicine.model.medication.WeekDaysEnum;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class TodayRecyclerViewAdapter extends RecyclerView.Adapter<TodayRecyclerViewAdapter.ViewHolder> {

    // ATTRIBUTES

    private HashMap<HourModel, List<Medication>> medicatonHashMap;
    private List<HourModel> hourList;

    // CONSTRUCTOR

    public TodayRecyclerViewAdapter() {
        this.medicatonHashMap = new HashMap<>();
        this.hourList = new ArrayList<>();
    }

    // METHODS

    @NonNull
    @Override
    public TodayRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemTodayListBinding itemTodayListBinding = ItemTodayListBinding.inflate(layoutInflater, parent, false);
        return new TodayRecyclerViewAdapter.ViewHolder(itemTodayListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TodayRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.bindView(hourList.get(position));
    }

    @Override
    public int getItemCount() {
        return hourList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateMedicationList(List<Medication> medicationList) {
        this.medicatonHashMap.clear();
        this.hourList.clear();
        this.medicatonHashMap = new HashMap<>();
        this.hourList = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        DateModel today = new DateModel(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        int weekday = (calendar.get(Calendar.DAY_OF_WEEK) - 2) % 7;
        for(Medication medication: medicationList) {
            if(medication.getStartDate().compareTo(today) <= 0  && medication.getEndDate().compareTo(today) >= 0 && medication.getWeekDays().get(WeekDaysEnum.getWeekDay(weekday))) {
                for(HourModel hour: medication.getHours()) {
                    List<Medication> list = medicatonHashMap.get(hour);
                    if(list == null) {
                        list = new ArrayList<>();
                    }
                    list.add(medication);
                    medicatonHashMap.put(hour, list);
                }
            }
        }
        this.hourList.addAll(this.medicatonHashMap.keySet());
        Collections.sort(this.hourList);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemTodayListBinding itemTodayListBinding;

        public ViewHolder(@NonNull ItemTodayListBinding binding) {
            super(binding.getRoot());
            itemTodayListBinding = binding;
        }

        public void bindView(HourModel hour) {
            itemTodayListBinding.hour.setText(hour.toString());
            TodayChildRecyclerViewAdapter recyclerViewAdapter = new TodayChildRecyclerViewAdapter();
            itemTodayListBinding.recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            itemTodayListBinding.recyclerView.setAdapter(recyclerViewAdapter);
            recyclerViewAdapter.updateMedicationList(medicatonHashMap.get(hour));
        }
    }

}
