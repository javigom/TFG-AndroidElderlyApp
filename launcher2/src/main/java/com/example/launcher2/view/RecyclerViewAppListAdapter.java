package com.example.launcher2.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.launcher2.databinding.ItemAppBinding;
import com.example.launcher2.model.AppModel;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAppListAdapter extends RecyclerView.Adapter<RecyclerViewAppListAdapter.ViewHolder> {

    // ATTRIBUTES

    private List<AppModel> appModelList;
    private RecyclerViewAppListInterface recyclerViewAppListInterface;

    // CONSTRUCTOR

    public RecyclerViewAppListAdapter(RecyclerViewAppListInterface recyclerViewAppListInterface) {
        this.appModelList = new ArrayList<>();
        this.recyclerViewAppListInterface = recyclerViewAppListInterface;
    }

    // METHODS

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_app, parent, false);
        //return new ViewHolder(view);

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemAppBinding itemAppBinding = ItemAppBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(itemAppBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAppListAdapter.ViewHolder holder, int position) {
        //AppModel appModel = appModelList.get(position);
        //holder.textView.setText(appModel.getLabel());
        //holder.imageView.setImageDrawable(appModel.getIcon());

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

        /*
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewIcon);
            textView = itemView.findViewById(R.id.textViewIcon);

        }*/

        ItemAppBinding itemAppBinding;

        public ViewHolder(@NonNull ItemAppBinding binding) {
            super(binding.getRoot());
            this.itemAppBinding = binding;
            this.itemAppBinding.linearLayout.setOnClickListener(view -> recyclerViewAppListInterface.onItemClick(appModelList.get(getAdapterPosition())));
        }

        public void bindView(AppModel app) {
            itemAppBinding.imageViewIcon.setImageDrawable(app.getIcon());
            itemAppBinding.textViewIcon.setText(app.getLabel());
        }

    }

}
