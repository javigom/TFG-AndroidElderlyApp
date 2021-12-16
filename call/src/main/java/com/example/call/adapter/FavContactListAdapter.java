package com.example.call.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.call.model.ContactModel;
import com.example.call.R;

import java.util.List;

public class FavContactListAdapter extends RecyclerView.Adapter<FavContactListAdapter.ViewHolder> {

    private List<ContactModel> favContactModelList;
    private SelectedFavContact selectedFavContact;

    public FavContactListAdapter(List<ContactModel> contactModelList, SelectedFavContact selectedFavContact){
        this.favContactModelList = contactModelList;
        this.selectedFavContact = selectedFavContact;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fav_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ContactModel contactModel = favContactModelList.get(position);
        holder.text_name.setText(contactModel.getName());
        holder.image.setImageResource(R.drawable.ic_default_contact_photo);

        if(contactModel.getPhoto() != null){
            holder.image.setImageURI(Uri.parse(contactModel.getPhoto()));
        }

    }

    @Override
    public int getItemCount() {
        return favContactModelList.size();
    }

    public interface SelectedFavContact {
        void selectedFavContact(ContactModel contactModel);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_name;
        ImageFilterView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text_name = itemView.findViewById(R.id.tvNameIFC);
            image = itemView.findViewById(R.id.ivPhotoIFC);
            itemView.setOnClickListener(v -> selectedFavContact.selectedFavContact(favContactModelList.get(getAdapterPosition())));
        }

    }
}
