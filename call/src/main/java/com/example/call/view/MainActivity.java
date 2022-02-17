package com.example.call.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.call.R;
import com.example.call.model.ContactComparator;
import com.example.call.view.adapter.CallLogListAdapter;
import com.example.call.view.adapter.ContactListAdapter;
import com.example.call.view.adapter.FavContactListAdapter;
import com.example.call.view.adapter.TabLayoutAdapter;
import com.example.call.view.fragment.CallLogListFragment;
import com.example.call.model.CallModel;
import com.example.call.model.ContactModel;
import com.example.call.model.MyContentResolver;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback,
        ContactListAdapter.SelectedContact, CallLogListAdapter.SelectedCall, FavContactListAdapter.SelectedFavContact, UpdateContact {

    // Permission codes
    private static final int READ_CONTACTS = 100;
    private static final int READ_CALL_LOG = 101;
    private static final int CALL_PHONE = 102;
    private static final int WRITE_CONTACTS = 103;

    // Update codes
    private static final int UPDATE_CALL_LOG = 200;
    private static final int UPDATE_CONTACT = 201;
    private static final int DELETE_CONTACT = 202;
    private static final int ADD_CONTACT = 203;

    // Model
    private MyContentResolver myContentResolver;
    private List<ContactModel> contactModelList;
    private List<ContactModel> favContactModelList;
    private List<CallModel> callModelList;
    private Map<String, ContactModel> phoneContactMap;

    // View
    private TabLayout tabLayout;
    private ViewPager2 pager2;
    private TabLayoutAdapter tabLayoutAdapter;
    private FloatingActionButton fabDial;

    // For updating the call log tab if the user has done a call from DialNumberActivity
    private ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {

                if (result.getResultCode() == UPDATE_CALL_LOG)
                    updateCallLog();

                else if (result.getResultCode() == UPDATE_CONTACT)
                    updateContact(result);

                else if(result.getResultCode() == DELETE_CONTACT)
                    deleteContact(result);

                else if(result.getResultCode() == ADD_CONTACT)
                    addContact(result);
            }
    );

    private void updateCallLog(){
        myContentResolver.updateCalls(callModelList);
        CallLogListFragment fragment = tabLayoutAdapter.getCallLogListFragment();

        if (fragment != null) {
            fragment.update(1);
        }
    }

    private void updateContact(ActivityResult result){
        if (result.getData() != null) {
            ContactModel newContact = (ContactModel) result.getData().getSerializableExtra("contact");

            for (int i = 0; i < contactModelList.size(); i++) {
                ContactModel c = contactModelList.get(i);

                if (c.getId() == newContact.getId()) {

                    String oldPhone = c.getPhone(), oldName = c.getName();

                    c.setName(newContact.getName());
                    c.setPhone(newContact.getPhone());

                    if(c.getIsStarred() != newContact.getIsStarred()){
                        c.setIsStarred(newContact.getIsStarred());

                        if(newContact.getIsStarred() == 0) {
                            favContactModelList.remove(c);
                        }
                        else {
                            favContactModelList.add(c);
                        }

                        tabLayoutAdapter.getFavContactListFragment().update();

                    }

                    c.setPhoto(newContact.getPhoto());
                    tabLayoutAdapter.getContactListFragment().updateModify(i);
                    myContentResolver.editContact(c, oldPhone);
                    Toast.makeText(getApplicationContext(), "Contacto actualizado", Toast.LENGTH_LONG).show();
                    break;

                }
            }

        }

        else {
            Toast.makeText(getApplicationContext(), "Ha ocurrido un error editando el contacto...", Toast.LENGTH_LONG).show();
        }
    }

    private void deleteContact(ActivityResult result){
        if (result.getData() != null) {

            ContactModel newContact = (ContactModel) result.getData().getSerializableExtra("contact");

            for (int i = 0; i < contactModelList.size(); i++) {
                ContactModel c = contactModelList.get(i);

                if (c.getId() == newContact.getId()) {
                    myContentResolver.deleteContact(c);
                    contactModelList.remove(c);
                    favContactModelList.remove(c);
                    tabLayoutAdapter.getFavContactListFragment().update();
                    tabLayoutAdapter.getContactListFragment().updateDelete();
                    Toast.makeText(getApplicationContext(), "Contacto eliminado", Toast.LENGTH_LONG).show();
                }
            }

        }

        else {
            Toast.makeText(getApplicationContext(), "Ha ocurrido un error eliminando el contacto...", Toast.LENGTH_LONG).show();
        }
    }

    private void addContact(ActivityResult result){

        if (result.getData() != null) {

            ContactModel newContact = (ContactModel) result.getData().getSerializableExtra("contact");

            // Modificar bbdd
            long id = myContentResolver.addContact(newContact);

            newContact.setId(id);

            // Añado el contacto a las listas
            contactModelList.add(newContact);
            contactModelList.sort(new ContactComparator());
            tabLayoutAdapter.getContactListFragment().updateDelete();

            if(newContact.getIsStarred() == 1){
                favContactModelList.add(newContact);
                tabLayoutAdapter.getFavContactListFragment().update();
            }

            Toast.makeText(getApplicationContext(), "Contacto añadido", Toast.LENGTH_LONG).show();

        }

        else {
            Toast.makeText(getApplicationContext(), "Ha ocurrido un error añadiendo al contacto...", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initPermissions();
        initData();
        initView();
    }

    // ARREGLAR -> SOLICITAR PERMISOS NECESARIOS EN CADA FUNCIONALIDAD
    private void initPermissions(){
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
        }

        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALL_LOG}, 2);
        }

        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 3);
        }

        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CONTACTS}, 4);
        }
    }

    /**
     * Initialization of the data
      */
    private void initData() {

        myContentResolver = new MyContentResolver(getContentResolver());
        contactModelList = new ArrayList<>();
        favContactModelList = new ArrayList<>();
        callModelList = new ArrayList<>();
        phoneContactMap = new HashMap<>();
        myContentResolver.loadContacts(contactModelList, favContactModelList, phoneContactMap);
        myContentResolver.loadCallLogs(callModelList);

    }

    /**
     * Initialization of the view
     */
    private void initView() {
        tabLayout = findViewById(R.id.tlMenuAM);
        pager2 = findViewById(R.id.vp2PagerAM);

        FragmentManager fm = getSupportFragmentManager();
        tabLayoutAdapter = new TabLayoutAdapter(fm, getLifecycle(), contactModelList, favContactModelList, callModelList, phoneContactMap,
                this, this, this, this);
        pager2.setAdapter(tabLayoutAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

                // Call log tab updating
                if(tab.getPosition() == 2){

                    int newCalls = myContentResolver.updateCalls(callModelList);

                    // If something has changed and the fragment is not null
                    if(newCalls > 0 && tabLayoutAdapter.getCallLogListFragment() != null){
                        tabLayoutAdapter.getCallLogListFragment().update(newCalls);
                    }
                }
            }
        });

        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        fabDial = findViewById(R.id.fabDialAM);

        fabDial.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DialNumberActivity.class);
            activityResultLauncher.launch(intent);
            //startActivity(new Intent(MainActivity.this, DialNumberActivity.class));

        });

    }

    @Override
    public void selectedContact(ContactModel contactModel) {
        Intent intent = new Intent(MainActivity.this, ContactActivity.class);
        intent.putExtra("contact", contactModel);
        activityResultLauncher.launch(intent);
    }

    @Override
    public void selectedCall(CallModel callModel) {
        //startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + callModel.getCallPhone())));
    }

    @Override
    public void selectedFavContact(@NonNull ContactModel contactModel) {
        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contactModel.getPhone())));
    }

    public void addNewContact(){
        Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
        activityResultLauncher.launch(intent);
    }

    @Override
    public void updateContact(ContactModel contactModel) {

    }
}
