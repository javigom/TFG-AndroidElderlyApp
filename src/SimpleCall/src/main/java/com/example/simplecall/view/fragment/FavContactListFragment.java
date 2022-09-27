package com.example.simplecall.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplecall.R;
import com.example.simplecall.model.ContactModel;
import com.example.simplecall.view.adapter.FavContactListAdapter;

import java.util.List;

public class FavContactListFragment extends Fragment {

    private final List<ContactModel> favContactList;
    private final FavContactListAdapter.SelectedFavContact selectedFavContact;
    private  FavContactListAdapter favContactListAdapter;

    public FavContactListFragment(List<ContactModel> favContactList, FavContactListAdapter.SelectedFavContact selectedFavContact) {
        this.favContactList = favContactList;
        this.selectedFavContact = selectedFavContact;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fav_contact_list, container, false);
        RecyclerView rvContacts = view.findViewById(R.id.rvContactsFFCL);
        rvContacts.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        favContactListAdapter = new FavContactListAdapter(this.favContactList, this.selectedFavContact);
        rvContacts.setAdapter(favContactListAdapter);

        return view;
    }

    public void update(){
        favContactListAdapter.notifyDataSetChanged();
    }

}
