package com.example.simplemedicine.usecases.common.rows;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplemedicine.R;
import com.example.simplemedicine.databinding.ItemTodayListBinding;
import com.example.simplemedicine.model.HourModel;
import com.example.simplemedicine.model.NotificationModel;
import com.example.simplemedicine.provider.room.repository.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TodayRecyclerViewAdapter extends RecyclerView.Adapter<TodayRecyclerViewAdapter.ViewHolder> {

    // ATTRIBUTES
    private List<NotificationModel> notificationList;
    private final Map<HourModel, List<NotificationModel>> notificationMap;
    private final List<HourModel> hourList;
    private Context context;
    private final Repository repository;

    // CONSTRUCTOR

    public TodayRecyclerViewAdapter(Repository repository) {
        this.notificationList = new ArrayList<>();
        this.notificationMap = new HashMap<>();
        this.hourList = new ArrayList<>();
        this.repository = repository;
    }

    // METHODS

    @NonNull
    @Override
    public TodayRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemTodayListBinding itemTodayListBinding = ItemTodayListBinding.inflate(layoutInflater, parent, false);
        context = parent.getContext();
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
    public void updateMedicationList(List<NotificationModel> notificationList) {
        this.notificationList.clear();
        this.notificationMap.clear();
        this.hourList.clear();
        this.notificationList = notificationList;
        for(NotificationModel notification: this.notificationList) {
            List<NotificationModel> list = notificationMap.get(notification.getHour());
            if(list == null) {
                list = new ArrayList<>();
                this.hourList.add(notification.getHour());
            }
            list.add(notification);
            notificationMap.put(notification.getHour(), list);
        }
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
            recyclerViewAdapter.updateNotificationList(notificationMap.get(hour));
            List<NotificationModel> list = notificationMap.get(hour);
            if(list != null) {
                for(NotificationModel notificationModel: list) {
                    if(notificationModel.isCompleted()) {
                        itemTodayListBinding.checkButton.setEnabled(false);
                        statusCheckButton(true, hour);
                        break;
                    }
                }

            }
            itemTodayListBinding.checkButton.setOnClickListener(v -> {
                itemTodayListBinding.checkButton.setEnabled(false);
                statusCheckButton(true, hour);
            });
        }

        public void statusCheckButton(boolean completed, HourModel hour) {
            if(completed) {
                itemTodayListBinding.status.setText("COMPLETADO");
                itemTodayListBinding.status.setTextColor(context.getResources().getColor(R.color.primary));
                itemTodayListBinding.checkButton.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.circle_button_pressed, null));
                List<NotificationModel> updateList = notificationMap.get(hour);
                if(updateList != null) {
                    for(NotificationModel notification : updateList) {
                        notification.setCompleted(true);
                        repository.update(notification);
                    }
                }
            }
            else {
                itemTodayListBinding.status.setText("PENDIENTE");
                itemTodayListBinding.status.setTextColor(context.getResources().getColor(R.color.secondary));
                itemTodayListBinding.checkButton.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.circle_button, null));
            }
        }
    }

}
