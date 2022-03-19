package com.example.launcher2.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
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
    boolean editOption, orderOption;

    // CONSTRUCTOR

    public RecyclerViewAppListAdapter(RecyclerViewAppListInterface recyclerViewAppListInterface) {
        this.appModelList = new ArrayList<>();
        this.recyclerViewAppListInterface = recyclerViewAppListInterface;
        this.editOption = false;
        this.orderOption = false;
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

    public boolean updateEditButtonVisibility() {
        this.editOption = !this.editOption;
        if(editOption && orderOption) orderOption = false;
        return this.editOption;
    }

    public boolean updateOrderButtonVisibility() {
        this.orderOption = !this.orderOption;
        if(orderOption && editOption) editOption = false;
        return this.orderOption;
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
                if(app.getPosition() != -1) app.setPosition(-1);
                else app.setPosition(0);
                itemAppBinding.buttonAppItem.setImageResource((app.getPosition() != - 1) ? R.drawable.ic_check : android.R.color.transparent);
            });

            this.itemAppBinding.upButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int index = getAdapterPosition();

                    if(index > 0) {
                        recyclerViewAppListInterface.onUpButtonClick(appModelList.get(index), appModelList.get(index - 1));
                    }

                    else {
                        recyclerViewAppListInterface.onUpButtonClick(appModelList.get(index), null);
                    }

                }
            });

            this.itemAppBinding.downButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = getAdapterPosition();

                    if((index < appModelList.size() - 1) && (appModelList.get(index + 1).getPosition() != -1)) {
                        recyclerViewAppListInterface.onDownButtonClick(appModelList.get(index), appModelList.get(index + 1));
                    }

                    else {
                        recyclerViewAppListInterface.onDownButtonClick(appModelList.get(index), null);
                    }
                }
            });

        }

        public void bindView(AppModel app) {
            itemAppBinding.imageViewIcon.setImageDrawable(app.getIcon());
            itemAppBinding.textViewIcon.setText(app.getLabel());
            itemAppBinding.buttonAppItem.setVisibility((editOption)? View.VISIBLE: View.GONE);
            itemAppBinding.buttonAppItem.setImageResource((app.getPosition() != - 1) ? R.drawable.ic_check : android.R.color.transparent);
            itemAppBinding.upButton.setVisibility((orderOption)? View.VISIBLE: View.GONE);
            itemAppBinding.downButton.setVisibility((orderOption)? View.VISIBLE: View.GONE);
        }
    }

}
