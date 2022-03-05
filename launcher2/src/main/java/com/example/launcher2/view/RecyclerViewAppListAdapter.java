package com.example.launcher2.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.launcher2.R;
import com.example.launcher2.model.AppModel;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAppListAdapter extends RecyclerView.Adapter<RecyclerViewAppListAdapter.ViewHolder> {

    // ATTRIBUTES

    private List<AppModel> appModelList;

    // CONSTRUCTOR

    public RecyclerViewAppListAdapter() {
        this.appModelList = new ArrayList<>();
    }

    // METHODS

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_app, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAppListAdapter.ViewHolder holder, int position) {
        AppModel appModel = appModelList.get(position);
        holder.textView.setText(appModel.getLabel());
        holder.imageView.setImageDrawable(appModel.getIcon());
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
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewIcon);
            textView = itemView.findViewById(R.id.textViewIcon);

        }
    }

}
