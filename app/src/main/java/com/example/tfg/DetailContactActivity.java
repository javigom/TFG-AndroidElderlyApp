package com.example.tfg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tfg.model.ContactModel;

public class DetailContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_contact);

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
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contactModel.getPhone())));
                }catch(Exception e){
                    e.printStackTrace();
                }

            });
        }
    }

}