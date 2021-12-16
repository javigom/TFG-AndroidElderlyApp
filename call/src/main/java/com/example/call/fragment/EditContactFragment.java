package com.example.call.fragment;

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

public class EditContactFragment extends Fragment {

    private ContactModel contactModel;
    private ImageButton ibFav;
    private ImageView ivPhoto;
    private EditText etName, etNumber;
    private Button bSave;
    private View view;

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
        bSave = view.findViewById(R.id.bSaveFEC);

        if(contactModel.getIsStarred() == 1) {
            ibFav.setImageDrawable(getContext().getDrawable(R.drawable.ic_star_enabled));
        }
        else{
            ibFav.setImageDrawable(getContext().getDrawable(R.drawable.ic_star_disabled));
        }

        if(contactModel.getPhoto() != null){
            ivPhoto.setImageURI(Uri.parse(contactModel.getPhoto()));
        }

        etName.setText(contactModel.getName());
        etNumber.setText(contactModel.getPhone());

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    ChangeFragment changeFragment = (ChangeFragment) getActivity();
                    changeFragment.change(1);

                } catch (Exception e){
                    System.out.println("Something went wrong...");
                    e.printStackTrace();
                    Toast.makeText(getContext(), "An error occurred...", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;

    }
}