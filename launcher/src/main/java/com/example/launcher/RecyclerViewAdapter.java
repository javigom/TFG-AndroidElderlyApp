package com.example.launcher;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<String> appList;
    private Activity activity;

    public RecyclerViewAdapter(List<String> appList, Activity activity){
        this.appList = appList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_launcher, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.button.setText(appList.get(position));
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent i = packageManager.getLaunchIntentForPackage(appList.get(holder.getAdapterPosition()));

                /*
                Intent intent = activity.getPackageManager().getLaunchIntentForPackage("com.example.call");


                if(intent != null){
                    activity.startActivity(intent);
                }

                else{
                    Toast.makeText(activity, "Error al abrir la app...", Toast.LENGTH_LONG).show();
                }*/

                activity.startActivity(new Intent(activity, com.example.call.MainActivity.class));


            }
        });
    }

    @Override
    public int getItemCount() {
        return appList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.buttonIL);
        }
    }
}
