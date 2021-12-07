package com.example.tfg;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.tfg.adapter.CallLogListAdapter;
import com.example.tfg.adapter.ContactListAdapter;
import com.example.tfg.adapter.FavContactListAdapter;
import com.example.tfg.adapter.TabLayoutAdapter;
import com.example.tfg.fragment.CallLogListFragment;
import com.example.tfg.model.CallModel;
import com.example.tfg.model.ContactModel;
import com.example.tfg.model.MyContentResolver;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback,
        ContactListAdapter.SelectedContact, CallLogListAdapter.SelectedCall, FavContactListAdapter.SelectedFavContact {

    // Permission codes
    private static final int READ_CONTACTS = 100;
    private static final int READ_CALL_LOG = 101;
    private static final int CALL_PHONE = 102;

    // Update codes
    private static final int UPDATE_CALL_LOG = 200;

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

    // For updating the call log tab if the user has done a call from DetailContactActivity or DialNumberActivity
    private ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == UPDATE_CALL_LOG){

                    myContentResolver.updateCalls(callModelList);
                    CallLogListFragment fragment =  tabLayoutAdapter.getCallLogListFragment();

                    if(fragment != null) {
                        fragment.update();
                    }
                }

            }
    );

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
        tabLayoutAdapter = new TabLayoutAdapter(fm, getLifecycle(), contactModelList, favContactModelList, callModelList, phoneContactMap,this, this, this);
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

                // Call log tab updating (for calls made from the fav tab)
                if(tab.getPosition() == 2){

                    // If something has changed and the fragment is not null
                    if(myContentResolver.updateCalls(callModelList) && tabLayoutAdapter.getCallLogListFragment() != null){
                        tabLayoutAdapter.getCallLogListFragment().update();
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

        });

    }

    @Override
    public void selectedContact(ContactModel contactModel) {
        Intent intent = new Intent(MainActivity.this, DetailContactActivity.class).putExtra("contact", contactModel);
        activityResultLauncher.launch(intent);
    }

    @Override
    public void selectedCall(CallModel callModel) {
        //startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + callModel.getCallPhone())));
    }

    @Override
    public void selectedFavContact(@NonNull ContactModel contactModel) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contactModel.getPhone()));
        activityResultLauncher.launch(intent);

    }

}
