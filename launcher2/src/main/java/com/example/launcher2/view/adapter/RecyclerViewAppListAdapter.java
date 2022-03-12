package com.example.launcher2.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.launcher2.R;
import com.example.launcher2.databinding.ItemAppListBinding;
import com.example.launcher2.event.RecyclerViewAppListInterface;
import com.example.launcher2.model.AppModel;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAppListAdapter extends RecyclerView.Adapter<RecyclerViewAppListAdapter.ViewHolder> {

    // ATTRIBUTES

    private List<AppModel> appModelList;
    private final RecyclerViewAppListInterface recyclerViewAppListInterface;

    // CONSTRUCTOR

    public RecyclerViewAppListAdapter(RecyclerViewAppListInterface recyclerViewAppListInterface) {
        this.appModelList = new ArrayList<>();
        this.recyclerViewAppListInterface = recyclerViewAppListInterface;
    }

    // METHODS

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemAppListBinding itemAppListBinding = ItemAppListBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(itemAppListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAppListAdapter.ViewHolder holder, int position) {
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
        ItemAppListBinding itemAppBinding;

        public ViewHolder(@NonNull ItemAppListBinding binding) {
            super(binding.getRoot());
            this.itemAppBinding = binding;
            this.itemAppBinding.linearLayout.setOnClickListener(view -> recyclerViewAppListInterface.onItemClick(appModelList.get(getAdapterPosition())));
            this.itemAppBinding.buttonAppItem.setOnClickListener(view -> {
                AppModel app = appModelList.get(getAdapterPosition());
                recyclerViewAppListInterface.onButtonClick(appModelList.get(getAdapterPosition()));
                app.setShortcut(!app.getShortcut());
                itemAppBinding.buttonAppItem.setImageResource((app.getShortcut()) ? R.drawable.ic_check : android.R.color.transparent);
            });
        }

        public void bindView(AppModel app) {
            itemAppBinding.imageViewIcon.setImageDrawable(app.getIcon());
            itemAppBinding.textViewIcon.setText(app.getLabel());
            itemAppBinding.buttonAppItem.setImageResource((app.getShortcut()) ? R.drawable.ic_check : android.R.color.transparent);
        }
    }

}
