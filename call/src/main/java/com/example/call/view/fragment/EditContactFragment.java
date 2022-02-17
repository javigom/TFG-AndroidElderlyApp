package com.example.call.view.fragment;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.call.R;
import com.example.call.model.ContactModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditContactFragment extends Fragment {

    private ContactModel contactModel;
    private ImageButton ibFav;
    private ImageView ivPhoto;
    private EditText etName, etNumber;
    private FloatingActionButton fbSave;
    private Button bDelete;
    private View view;
    private int isStarred;

    public EditContactFragment() {
        // Required empty public constructor
    }

    public EditContactFragment(ContactModel contactModel) {
        this.contactModel = contactModel;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_contact, container, false);
        ibFav = view.findViewById(R.id.ibFavFEC);
        ivPhoto = view.findViewById(R.id.ivPhotoFEC);
        etName = view.findViewById(R.id.etNameFEC);
        etNumber = view.findViewById(R.id.etNumberFEC);
        fbSave = view.findViewById(R.id.fbEditFDC);
        bDelete = view.findViewById(R.id.bDeleteFEC);

        if(contactModel.getIsStarred() == 1) {
            ibFav.setImageDrawable(getContext().getDrawable(R.drawable.ic_star_enabled));
            isStarred = 1;
        }
        else{
            ibFav.setImageDrawable(getContext().getDrawable(R.drawable.ic_star_disabled));
            isStarred = 0;
        }

        if(contactModel.getPhoto() != null){
            ivPhoto.setImageURI(Uri.parse(contactModel.getPhoto()));
        }

        etName.setText(contactModel.getName());
        etNumber.setText(contactModel.getPhone());

        ibFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isStarred == 1) {
                    ibFav.setImageDrawable(getContext().getDrawable(R.drawable.ic_star_disabled));
                    isStarred = 0;
                }
                else {
                    ibFav.setImageDrawable(getContext().getDrawable(R.drawable.ic_star_enabled));
                    isStarred = 1;
                }
            }
        });

        fbSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String newName = etName.getText().toString();
                    String newPhone = etNumber.getText().toString();
                    contactModel.setName(newName);
                    contactModel.setPhone(newPhone);
                    contactModel.setIsStarred(isStarred);
                    ChangeFragment changeFragment = (ChangeFragment) getActivity();
                    changeFragment.change(3);

                } catch (Exception e){
                    System.out.println("Something went wrong...");
                    e.printStackTrace();
                    Toast.makeText(getContext(), "An error occurred...", Toast.LENGTH_LONG).show();
                }
            }
        });

        bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Contacto eliminado", Toast.LENGTH_LONG).show();
                ChangeFragment changeFragment = (ChangeFragment) getActivity();
                changeFragment.change(2);
            }
        });

        return view;

    }
}