package com.example.call.view.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.call.R;
import com.example.call.model.ContactModel;

import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {

    private List<ContactModel> contactModelList;
    private SelectedContact selectedContact;

    public ContactListAdapter(List<ContactModel> contactModelList, SelectedContact selectedContact){
        this.contactModelList = contactModelList;
        this.selectedContact = selectedContact;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ContactModel contactModel = contactModelList.get(position);
        holder.text_name.setText(contactModel.getName());
        holder.image.setImageResource(R.drawable.ic_default_contact_photo);

        if(contactModel.getPhoto() != null){
            holder.image.setImageURI(Uri.parse(contactModel.getPhoto()));
        }

    }

    @Override
    public int getItemCount() {
        return contactModelList.size();
    }

    public interface SelectedContact {
        void selectedContact(ContactModel contactModel);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_name;
        ImageFilterView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text_name = itemView.findViewById(R.id.tvNameIC);
            image = itemView.findViewById(R.id.ivPhotoIC);
            itemView.setOnClickListener(v -> selectedContact.selectedContact(contactModelList.get(getAdapterPosition())));
        }

    }
}
