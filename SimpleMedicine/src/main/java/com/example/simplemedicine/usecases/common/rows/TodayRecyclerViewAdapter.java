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
import com.example.simplemedicine.model.Medication;
import com.example.simplemedicine.model.NotificationModel;
import com.example.simplemedicine.provider.room.repository.Repository;
import com.example.simplemedicine.util.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class    TodayRecyclerViewAdapter extends RecyclerView.Adapter<TodayRecyclerViewAdapter.ViewHolder> {

    // ATTRIBUTES
    private List<NotificationModel> notificationList;
    private final Map<HourModel, List<NotificationModel>> notificationMap;
    private Context context;
    private final Repository repository;

    // CONSTRUCTOR

    public TodayRecyclerViewAdapter(Repository repository) {
        this.notificationList = new ArrayList<>();
        this.notificationMap = new TreeMap<>();
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
        holder.bindView(new ArrayList<>(notificationMap.keySet()).get(position));
    }

    @Override
    public int getItemCount() {
        return notificationMap.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateMedicationList(List<NotificationModel> notificationList) {
        this.notificationList.clear();
        this.notificationMap.clear();
        this.notificationList = notificationList;
        for(NotificationModel notification: this.notificationList) {
            if(notification.getStartDate().compareTo(Utils.getTodayDate()) <= 0 && notification.getEndDate().compareTo(Utils.getTodayDate()) >= 0) {
                List<NotificationModel> list = notificationMap.get(notification.getHour());
                if(list == null) {
                    list = new ArrayList<>();
                }
                list.add(notification);
                notificationMap.put(notification.getHour(), list);
            }
        }
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
                for(NotificationModel notificationModel: list)
                    statusCheckButton(notificationModel.isCompleted(), hour);
            }
            itemTodayListBinding.checkButton.setOnClickListener(v -> {
                statusCheckButton(true, hour);
                updateDatabase(hour);
            });
        }

        public void statusCheckButton(boolean completed, HourModel hour) {
            if(completed) {
                // Disable button & text
                itemTodayListBinding.checkButton.setEnabled(false);
                itemTodayListBinding.status.setText(R.string.notification_completed);
                itemTodayListBinding.status.setTextColor(context.getResources().getColor(R.color.primary));
                itemTodayListBinding.checkButton.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.circle_button_pressed, null));
            }
            else {
                // Enable button & text
                itemTodayListBinding.checkButton.setEnabled(true);
                itemTodayListBinding.status.setText(R.string.notification_pending);
                itemTodayListBinding.status.setTextColor(context.getResources().getColor(R.color.secondary));
                itemTodayListBinding.checkButton.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.circle_button, null));
            }
        }

        public void updateDatabase(HourModel hour){
            List<NotificationModel> updateList = notificationMap.get(hour);
            if(updateList != null) {
                NotificationModel[] array = new NotificationModel[updateList.size()];
                repository.update(updateList.toArray(array));
            }
        }
    }

}
