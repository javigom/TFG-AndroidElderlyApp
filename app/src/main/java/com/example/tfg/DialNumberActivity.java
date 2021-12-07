package com.example.tfg;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class DialNumberActivity extends AppCompatActivity {

    private static final int UPDATE_CALL_LOG = 200;

    private TextView tpPhone;
    private Button bCall, b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, bAsterisk, bHashMark, bDelete;
    private boolean callDone = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial_number);

        // ACTION BAR
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // VIEW BUTTONS
        tpPhone = findViewById(R.id.tpPhoneADN);
        bCall = findViewById(R.id.bCallADN);
        b1 = findViewById(R.id.b1ADN);
        b2 = findViewById(R.id.b2ADN);
        b3 = findViewById(R.id.b3ADN);
        b4 = findViewById(R.id.b4ADN);
        b5 = findViewById(R.id.b5ADN);
        b6 = findViewById(R.id.b6ADN);
        b7 = findViewById(R.id.b7ADN);
        b8 = findViewById(R.id.b8ADN);
        b9 = findViewById(R.id.b9ADN);
        b0 = findViewById(R.id.b0ADN);
        bAsterisk = findViewById(R.id.bAsteriscoADN);
        bHashMark = findViewById(R.id.bAlmohadillaADN);
        bDelete = findViewById(R.id.bDeleteADN);


        // CALL BUTTON
        bCall.setOnClickListener(v -> {
            callDone = true;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tpPhone.getText())));
        });

        // NUMBER KEYBOARD
        b1.setOnClickListener(v -> {
            String text = tpPhone.getText().toString() + "1";
            tpPhone.setText(text);
        });
        b2.setOnClickListener(v -> {
            String text = tpPhone.getText().toString() + "2";
            tpPhone.setText(text);
        });
        b3.setOnClickListener(v -> {
            String text = tpPhone.getText().toString() + "3";
            tpPhone.setText(text);
        });
        b4.setOnClickListener(v -> {
            String text = tpPhone.getText().toString() + "4";
            tpPhone.setText(text);
        });
        b5.setOnClickListener(v -> {
            String text = tpPhone.getText().toString() + "5";
            tpPhone.setText(text);
        });
        b6.setOnClickListener(v -> {
            String text = tpPhone.getText().toString() + "6";
            tpPhone.setText(text);
        });
        b7.setOnClickListener(v -> {
            String text = tpPhone.getText().toString() + "7";
            tpPhone.setText(text);
        });
        b8.setOnClickListener(v -> {
            String text = tpPhone.getText().toString() + "8";
            tpPhone.setText(text);
        });
        b9.setOnClickListener(v -> {
            String text = tpPhone.getText().toString() + "9";
            tpPhone.setText(text);
        });
        b0.setOnClickListener(v -> {
            String text = tpPhone.getText().toString() + "0";
            tpPhone.setText(text);
        });
        bHashMark.setOnClickListener(v -> {
            String text = tpPhone.getText().toString() + "#";
            tpPhone.setText(text);
        });
        bAsterisk.setOnClickListener(v -> {
            String text = tpPhone.getText().toString() + "*";
            tpPhone.setText(text);
        });
        bDelete.setOnClickListener(v -> {
            String text = tpPhone.getText().toString();
            if (text.length() > 0) {
                tpPhone.setText(text.substring(0, text.length() - 1));
            }
        });

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
