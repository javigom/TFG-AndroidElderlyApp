package com.example.simplemedicine.usecases.common.rows;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplemedicine.databinding.ItemTodayChildListBinding;
import com.example.simplemedicine.model.Medication;
import com.example.simplemedicine.model.NotificationModel;

import java.util.ArrayList;
import java.util.List;

public class TodayChildRecyclerViewAdapter extends RecyclerView.Adapter<TodayChildRecyclerViewAdapter.ViewHolder> {

    // ATTRIBUTES

    private List<NotificationModel> notificationList;

    // CONSTRUCTOR

    public TodayChildRecyclerViewAdapter() {
        this.notificationList = new ArrayList<>();
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
        holder.bindView(notificationList.get(position));
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateNotificationList(List<NotificationModel> notificationList) {
        this.notificationList.clear();
        this.notificationList = notificationList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemTodayChildListBinding itemTodayChildListBinding;

        public ViewHolder(@NonNull ItemTodayChildListBinding binding) {
            super(binding.getRoot());
            itemTodayChildListBinding = binding;
        }

        public void bindView(NotificationModel notification) {
            itemTodayChildListBinding.name.setText(notification.getMedicationName());
            itemTodayChildListBinding.constraintLayout.getBackground().setTint(notification.getMedicationColor());
        }
    }
}

