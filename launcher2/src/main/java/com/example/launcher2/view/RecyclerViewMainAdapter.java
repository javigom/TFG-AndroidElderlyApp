package com.example.launcher2.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.launcher2.databinding.ItemAppShortcutBinding;
import com.example.launcher2.event.RecyclerViewAppListInterface;
import com.example.launcher2.model.AppModel;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewMainAdapter extends RecyclerView.Adapter<RecyclerViewMainAdapter.ViewHolder>{

    // ATTRIBUTES

    private List<AppModel> appModelList;
    private RecyclerViewAppListInterface recyclerViewAppListInterface;

    // CONSTRUCTOR

    public RecyclerViewMainAdapter(RecyclerViewAppListInterface recyclerViewAppListInterface) {
        this.appModelList = new ArrayList<>();
        this.recyclerViewAppListInterface = recyclerViewAppListInterface;
    }

    // METHODS

    @NonNull
    @Override
    public RecyclerViewMainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemAppShortcutBinding itemAppShortcutBinding = ItemAppShortcutBinding.inflate(layoutInflater, parent, false);
        return new RecyclerViewMainAdapter.ViewHolder(itemAppShortcutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewMainAdapter.ViewHolder holder, int position) {
        holder.bindView(appModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return appModelList.size();
    }

    public void updateAppList(final List<AppModel> appModelList) {
        this.appModelList.clear();
        this.appModelList = appModelList;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemAppShortcutBinding itemAppShortcutBinding;

        public ViewHolder(@NonNull ItemAppShortcutBinding binding) {
            super(binding.getRoot());
            this.itemAppShortcutBinding = binding;
            this.itemAppShortcutBinding.linearLayout.setOnClickListener(view -> recyclerViewAppListInterface.onItemClick(appModelList.get(getAdapterPosition())));
        }

        public void bindView(AppModel app) {
            itemAppShortcutBinding.imageViewIcon.setImageDrawable(app.getIcon());
            itemAppShortcutBinding.textViewIcon.setText(app.getLabel());
        }
    }

}
