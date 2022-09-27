package com.example.simpleLauncher.view.adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simpleLauncher.R;
import com.example.simpleLauncher.databinding.ItemAppListBinding;
import com.example.simpleLauncher.event.RecyclerViewAppListInterface;
import com.example.simpleLauncher.model.AppModel;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAppListAdapter extends RecyclerView.Adapter<RecyclerViewAppListAdapter.ViewHolder> {

    // ATTRIBUTES

    private List<AppModel> appModelList;
    private final RecyclerViewAppListInterface recyclerViewAppListInterface;
    private boolean editOption, orderOption;
    private Context context;

    // CONSTRUCTOR

    public RecyclerViewAppListAdapter(RecyclerViewAppListInterface recyclerViewAppListInterface, Context context) {
        this.appModelList = new ArrayList<>();
        this.recyclerViewAppListInterface = recyclerViewAppListInterface;
        this.editOption = false;
        this.orderOption = false;
        this.context = context;
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

    public void setEditOption(boolean value) {
        editOption = value;
        orderOption = false;
    }

    public void setOrderOption(boolean value) {
        orderOption = value;
        editOption = false;
    }

    public boolean isEditOption() {
        return editOption;
    }

    public boolean isOrderOption() {
        return orderOption;
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
                if(app.getPosition() != -1)
                    app.setPosition(-1);
                else
                    app.setPosition(0);
                itemAppBinding.buttonAppItem.setImageResource((app.getPosition() != - 1) ? R.drawable.ic_check : android.R.color.transparent);
            });

            this.itemAppBinding.upButton.setOnClickListener(v -> {
                int index = getAdapterPosition();
                if(index > 0)
                    recyclerViewAppListInterface.onUpButtonClick(appModelList.get(index), appModelList.get(index - 1));
                else
                    recyclerViewAppListInterface.onUpButtonClick(appModelList.get(index), null);
            });

            this.itemAppBinding.downButton.setOnClickListener(v -> {
                int index = getAdapterPosition();
                if((index < appModelList.size() - 1) && (appModelList.get(index + 1).getPosition() != -1))
                    recyclerViewAppListInterface.onDownButtonClick(appModelList.get(index), appModelList.get(index + 1));
                else
                    recyclerViewAppListInterface.onDownButtonClick(appModelList.get(index), null);
            });

        }

        public void bindView(AppModel app) {
            itemAppBinding.imageViewIcon.setImageDrawable(app.getIcon());
            itemAppBinding.textViewIcon.setText(app.getLabel());
            itemAppBinding.textViewIcon.setMaxWidth((editOption || orderOption)? dpToPx(180): dpToPx(300));
            itemAppBinding.buttonAppItem.setVisibility((editOption)? View.VISIBLE: View.GONE);
            itemAppBinding.buttonAppItem.setImageResource((app.getPosition() != - 1) ? R.drawable.ic_check : android.R.color.transparent);
            itemAppBinding.upButton.setVisibility((orderOption)? View.VISIBLE: View.GONE);
            itemAppBinding.downButton.setVisibility((orderOption)? View.VISIBLE: View.GONE);
        }

        public int dpToPx(float dp) {
            return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
        }

    }

}
