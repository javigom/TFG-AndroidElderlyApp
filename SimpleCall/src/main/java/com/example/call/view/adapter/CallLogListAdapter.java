package com.example.call.view.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.call.R;
import com.example.call.model.CallModel;
import com.example.call.model.ContactModel;

import java.util.List;
import java.util.Map;

public class CallLogListAdapter extends RecyclerView.Adapter<CallLogListAdapter.ViewHolder> {

    private List<CallModel> callModelList;
    private Map<String, ContactModel> phoneContactMap;
    private SelectedCall selectedCall;

    public CallLogListAdapter(List<CallModel> callModelList, Map<String, ContactModel> phoneContactMap, SelectedCall selectedCall){
        this.callModelList = callModelList;
        this.selectedCall = selectedCall;
        this.phoneContactMap = phoneContactMap;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_call, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CallModel callModel = callModelList.get(position);

        ContactModel contact = null;

        if(phoneContactMap.containsKey(callModel.getPhone())){
            contact = phoneContactMap.get(callModel.getPhone());
        }

        holder.text_name.setText(callModel.getPhone());
        holder.image_contact.setImageResource(R.drawable.ic_default_contact_photo);

        if (contact != null) {
            holder.text_name.setText(contact.getName());

            if(contact.getPhoto() != null) {
                holder.image_contact.setImageURI(Uri.parse(contact.getPhoto()));
            }
        }

        holder.text_info.setText(callModel.getFormattedDate());

        switch(callModel.getCallType()){
            case "INCOMING_TYPE":
                holder.image_type.setImageResource(R.drawable.ic_call_incoming);
                break;
            case "OUTGOING_TYPE":
                holder.image_type.setImageResource(R.drawable.ic_call_outgoing);
                break;
            case "MISSED_TYPE":
                holder.image_type.setImageResource(R.drawable.ic_call_missed);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return callModelList.size();
    }

    public interface SelectedCall {
        void selectedCall(CallModel callModel);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_name, text_info;
        ImageView image_contact, image_type;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text_name = itemView.findViewById(R.id.tvNameIC);
            text_info = itemView.findViewById(R.id.tvCallInfoIC);
            image_contact = itemView.findViewById(R.id.ivPhotoIC);
            image_type = itemView.findViewById(R.id.ivCallTypeIC);

            itemView.setOnClickListener(v -> selectedCall.selectedCall(callModelList.get(getAdapterPosition())));
        }
    }

}
