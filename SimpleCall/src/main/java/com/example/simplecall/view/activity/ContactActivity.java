package com.example.simplecall.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.simplecall.R;
import com.example.simplecall.model.ContactModel;
import com.example.simplecall.view.fragment.ChangeFragment;
import com.example.simplecall.view.fragment.DetailContactFragment;
import com.example.simplecall.view.fragment.EditContactFragment;

public class ContactActivity extends AppCompatActivity implements ChangeFragment {

    private DetailContactFragment detailContactFragment;
    private EditContactFragment editContactFragment;
    private FragmentManager fragmentManager;
    private int actualFragment;
    private ContactModel contactModel;

    private static final int UPDATE_CONTACT = 201;
    private static final int DELETE_CONTACT = 202;

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
                    Intent intent = new Intent(ContactActivity.this, MainActivity.class);
                    intent.putExtra("contact", contactModel);
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
            contactModel = (ContactModel) intent.getSerializableExtra("contact");
            detailContactFragment = new DetailContactFragment(contactModel);
            editContactFragment = new EditContactFragment(contactModel, this);

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
            detailContactFragment.updateView();
            actualFragment = 0;
        }

        else if(code == 2) {
            Intent intent = new Intent(ContactActivity.this, MainActivity.class);
            intent.putExtra("contact", contactModel);
            setResult(DELETE_CONTACT, intent);
            finish();
        }

        else if(code == 3) {
            Intent intent = new Intent(ContactActivity.this, MainActivity.class);
            intent.putExtra("contact", contactModel);
            setResult(UPDATE_CONTACT, intent);
            finish();
        }

        transaction.commit();

    }
}