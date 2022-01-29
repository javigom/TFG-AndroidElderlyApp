package com.example.launcher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<AppModel> appList;
    private Activity activity;

    public RecyclerViewAdapter(List<AppModel> appList, Activity activity){
        this.appList = appList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_launcher, null, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.button.setText(appList.get(position).getAppName());

        if((appList.get(position).getAppIcon()) != -1){
            holder.imageView.setImageResource(appList.get(position).getAppIcon());
        }

        //holder.button.setBackgroundColor(appList.get(position).getAppColor());
        //holder.button.setBackgroundResource(R.color.black);
        holder.button.setBackgroundColor(appList.get(position).getAppColor());


        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String path = appList.get(holder.getAdapterPosition()).getAppPackage();

                if(path != null){
                    Intent intent = activity.getPackageManager().getLaunchIntentForPackage(appList.get(holder.getAdapterPosition()).getAppPackage());
                    activity.startActivity(intent);
                }

                else{
                    Toast.makeText(activity, "Error al abrir la app...", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return appList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Button button;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.buttonIL);
            imageView = itemView.findViewById(R.id.ivIconIL);
        }
    }
}
