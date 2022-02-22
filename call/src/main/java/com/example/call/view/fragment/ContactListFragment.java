package com.example.call.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.call.model.ContactModel;
import com.example.call.R;
import com.example.call.view.DialNumberActivity;
import com.example.call.view.MainActivity;
import com.example.call.view.adapter.ContactListAdapter;

import java.util.List;

public class ContactListFragment extends Fragment {

    private final List<ContactModel> contactModelList;
    private final ContactListAdapter.SelectedContact selectedContact;
    private ContactListAdapter contactListAdapter;

    private MainActivity mainActivity;

    public ContactListFragment(List<ContactModel> contactModelList, ContactListAdapter.SelectedContact selectedContact, MainActivity mainActivity) {
        this.contactModelList = contactModelList;
        this.selectedContact = selectedContact;
        this.mainActivity = mainActivity;
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
        contactListAdapter = new ContactListAdapter(this.contactModelList, this.selectedContact);
        recyclerView.setAdapter(contactListAdapter);

        Button button = view.findViewById(R.id.bAddFCL);
        button.setOnClickListener(v -> mainActivity.addNewContact());

        return view;
    }

    public void updateModify(int index){
        contactListAdapter.notifyItemChanged(index);
    }

    public void updateDelete(){
        contactListAdapter.notifyDataSetChanged();
    }

}
