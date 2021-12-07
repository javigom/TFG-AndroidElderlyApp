package com.example.tfg;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tfg.model.ContactModel;

public class DetailContactActivity extends AppCompatActivity {

    private static final int UPDATE_CALL_LOG = 200;
    private boolean callDone = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_contact);

        // ACTION BAR
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // VIEW BUTTONS
        TextView tvName = findViewById(R.id.tvNameADC);
        TextView tvPhone = findViewById(R.id.tvPhoneADC);
        ImageView ivPhoto = findViewById(R.id.ivPhotoADC);
        Button bCall = findViewById(R.id.bCallADC);

        Intent intent = getIntent();

        if(intent.getExtras() != null){
            ContactModel contactModel = (ContactModel) intent.getSerializableExtra("contact");
            tvName.setText(contactModel.getName());
            tvPhone.setText(contactModel.getPhone());

            if(contactModel.getPhoto() != null){
                ivPhoto.setImageURI(Uri.parse(contactModel.getPhoto()));
            }

            bCall.setOnClickListener(v -> {
                try{
                    callDone = true;
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contactModel.getPhone())));
                }catch(Exception e){
                    e.printStackTrace();
                }

            });
        }

        // BACK NAVIGATION BUTTON
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {

                if(callDone){
                    Intent intent = new Intent();
                    setResult(UPDATE_CALL_LOG, intent);
                }
                finish();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}