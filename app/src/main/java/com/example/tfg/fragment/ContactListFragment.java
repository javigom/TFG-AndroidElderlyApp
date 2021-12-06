package com.example.tfg.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfg.model.ContactModel;
import com.example.tfg.R;
import com.example.tfg.adapter.ContactListAdapter;

import java.util.List;

public class ContactListFragment extends Fragment {

    private final List<ContactModel> contactModelList;
    private final ContactListAdapter.SelectedContact selectedContact;

    public ContactListFragment(List<ContactModel> contactModelList, ContactListAdapter.SelectedContact selectedContact) {
        this.contactModelList = contactModelList;
        this.selectedContact = selectedContact;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contact_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rvContactsFCL);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        ContactListAdapter contactListAdapter = new ContactListAdapter(this.contactModelList, this.selectedContact);
        recyclerView.setAdapter(contactListAdapter);

        return view;
    }

}
