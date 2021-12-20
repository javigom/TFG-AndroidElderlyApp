package com.example.call.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.call.fragment.CallLogListFragment;
import com.example.call.fragment.ContactListFragment;
import com.example.call.fragment.FavContactListFragment;
import com.example.call.model.CallModel;
import com.example.call.model.ContactModel;

import java.util.List;
import java.util.Map;

public class TabLayoutAdapter extends FragmentStateAdapter {

    private List<ContactModel> contactModelList;
    private List<ContactModel> favContactList;
    private List<CallModel> callModelList;
    private Map<String, ContactModel> phoneContactMap;
    private ContactListAdapter.SelectedContact selectedContact;
    private CallLogListAdapter.SelectedCall selectedCall;
    private FavContactListAdapter.SelectedFavContact selectedFavContact;

    private CallLogListFragment callLogListFragment;
    private ContactListFragment contactListFragment;
    private FavContactListFragment favContactListFragment;

    public TabLayoutAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<ContactModel> contactModelList,
            List<ContactModel> favContactList, List<CallModel> callModelList, Map<String, ContactModel> phoneContactMap,
                            ContactListAdapter.SelectedContact selectedContact, CallLogListAdapter.SelectedCall selectedCall,
                            FavContactListAdapter.SelectedFavContact selectedFavContact) {

        super(fragmentManager, lifecycle);
        this.contactModelList = contactModelList;
        this.favContactList = favContactList;
        this.callModelList = callModelList;
        this.phoneContactMap = phoneContactMap;
        this.selectedContact = selectedContact;
        this.selectedCall = selectedCall;
        this.selectedFavContact = selectedFavContact;
        notifyDataSetChanged();
    }

    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 0:
                this.favContactListFragment = new FavContactListFragment(favContactList, selectedFavContact);
                return favContactListFragment;
            case 1:
                this.contactListFragment = new ContactListFragment(contactModelList, selectedContact);
                return contactListFragment;
            case 2:
                this.callLogListFragment = new CallLogListFragment(callModelList, phoneContactMap, selectedCall);
                return callLogListFragment;
            default:
                return null;
        }

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public CallLogListFragment getCallLogListFragment(){
        return this.callLogListFragment;
    }

    public ContactListFragment getContactListFragment() {
        return this.contactListFragment;
    }

    public FavContactListFragment getFavContactListFragment() {
        return this.favContactListFragment;
    }

}
