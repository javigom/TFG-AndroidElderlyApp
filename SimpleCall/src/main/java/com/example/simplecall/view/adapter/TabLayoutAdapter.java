package com.example.simplecall.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.simplecall.model.CallModel;
import com.example.simplecall.model.ContactModel;
import com.example.simplecall.view.activity.MainActivity;
import com.example.simplecall.view.fragment.CallLogListFragment;
import com.example.simplecall.view.fragment.ContactListFragment;
import com.example.simplecall.view.fragment.FavContactListFragment;

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

    private MainActivity mainActivity;

    private CallLogListFragment callLogListFragment;
    private ContactListFragment contactListFragment;
    private FavContactListFragment favContactListFragment;

    public TabLayoutAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<ContactModel> contactModelList,
            List<ContactModel> favContactList, List<CallModel> callModelList, Map<String, ContactModel> phoneContactMap,
                            ContactListAdapter.SelectedContact selectedContact, CallLogListAdapter.SelectedCall selectedCall,
                            FavContactListAdapter.SelectedFavContact selectedFavContact, MainActivity mainActivity) {

        super(fragmentManager, lifecycle);
        this.contactModelList = contactModelList;
        this.favContactList = favContactList;
        this.callModelList = callModelList;
        this.phoneContactMap = phoneContactMap;
        this.selectedContact = selectedContact;
        this.selectedCall = selectedCall;
        this.selectedFavContact = selectedFavContact;
        this.mainActivity = mainActivity;
        notifyDataSetChanged();
    }

    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 0:
                this.favContactListFragment = new FavContactListFragment(favContactList, selectedFavContact);
                return favContactListFragment;
            case 1:
                this.contactListFragment = new ContactListFragment(contactModelList, selectedContact, mainActivity);
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
