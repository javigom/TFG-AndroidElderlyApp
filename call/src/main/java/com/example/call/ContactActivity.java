package com.example.call;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.call.fragment.ChangeFragment;
import com.example.call.fragment.DetailContactFragment;
import com.example.call.fragment.EditContactFragment;
import com.example.call.model.ContactModel;

public class ContactActivity extends AppCompatActivity implements ChangeFragment {

    private DetailContactFragment detailContactFragment;
    private EditContactFragment editContactFragment;
    private FragmentManager fragmentManager;
    private int actualFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_contact);

        fragmentManager = getSupportFragmentManager();


        // ACTION BAR
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        // BACK NAVIGATION BUTTON
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                if(actualFragment == 0){
                    finish();
                }

                else if(actualFragment == 1){
                    change(actualFragment);

                }
            }
        };

        getOnBackPressedDispatcher().addCallback(this, callback);

        Intent intent = getIntent();

        if(intent.getExtras() != null){
            ContactModel contactModel = (ContactModel) intent.getSerializableExtra("contact");
            detailContactFragment = new DetailContactFragment(contactModel);
            editContactFragment = new EditContactFragment(contactModel);

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.detail_contact_fragment, detailContactFragment);
            transaction.add(R.id.edit_contact_fragment, editContactFragment);
            transaction.commit();

        }

    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void change(int code) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // DetailContact to EditContact
        if(code == 0){
            transaction.show(editContactFragment);
            transaction.hide(detailContactFragment);
            actualFragment = 1;
        }

        // EditContact to DetailContact
        else if(code == 1){
            transaction.show(detailContactFragment);
            transaction.hide(editContactFragment);
            actualFragment = 0;
        }

        transaction.commit();

    }
}