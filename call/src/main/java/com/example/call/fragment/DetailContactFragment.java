package com.example.call.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.call.ContactActivity;
import com.example.call.R;
import com.example.call.model.ContactModel;

public class DetailContactFragment extends Fragment {

    private ContactModel contactModel;
    private TextView tvName, tvPhone;
    private ImageView ivPhoto;
    private Button bCall, bEdit;
    private View view;
    private ContactActivity detailContactActivity;


    public DetailContactFragment(){
        // Required empty public constructor
    }

    public DetailContactFragment(ContactModel contactModel) {
        this.contactModel = contactModel;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_detail_contact, container, false);
        tvName = view.findViewById(R.id.tvNameFDC);
        tvPhone = view.findViewById(R.id.tvPhoneFDC);
        ivPhoto = view.findViewById(R.id.ivPhotoFDC);
        bCall = view.findViewById(R.id.bCallFDC);
        bEdit = view.findViewById(R.id.bEditFDC);

        tvName.setText(contactModel.getName());
        tvPhone.setText(contactModel.getPhone());

        if(contactModel.getPhoto() != null){
            ivPhoto.setImageURI(Uri.parse(contactModel.getPhoto()));
        }

        bCall.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contactModel.getPhone()))));
        bEdit.setOnClickListener(v -> {

            try{
                ChangeFragment changeFragment = (ChangeFragment) getActivity();
                changeFragment.change(0);

            } catch (Exception e){
                System.out.println("Something went wrong...");
                e.printStackTrace();
                Toast.makeText(getContext(), "An error occurred...", Toast.LENGTH_LONG).show();
            }

        });

        return view;
    }
}