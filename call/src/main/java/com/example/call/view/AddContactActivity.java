package com.example.call.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.call.R;
import com.example.call.model.ContactModel;
import com.example.call.view.fragment.ChangeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddContactActivity extends AppCompatActivity {

    private int isStarred = 0;
    private EditText etName, etPhone;

    private static final int ADD_CONTACT = 203;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        // ACTION BAR
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        etName = findViewById(R.id.etNameAAC);
        etPhone = findViewById(R.id.etNumberAAC);

        FloatingActionButton floatingActionButton = findViewById(R.id.fbEditFDC);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = etName.getText().toString();
                String phone = etPhone.getText().toString();

                if(name.length() > 0 && phone.length() > 0){

                    ContactModel contactModel = new ContactModel((long) -1, name, phone, null, isStarred);
                    Intent intent = new Intent(AddContactActivity.this, MainActivity.class);
                    intent.putExtra("contact", contactModel);
                    setResult(ADD_CONTACT, intent);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Algún campo está vacío", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}