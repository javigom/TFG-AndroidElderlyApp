package com.example.call.view.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
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
import com.example.call.view.ContactActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class EditContactFragment extends Fragment {

    private ContactModel contactModel;
    private ImageButton ibFav;
    private ImageView ivPhoto;
    private EditText etName, etNumber;
    private FloatingActionButton fbSave, bDelete;
    private Button bPhoto;
    private View view;
    private int isStarred;
    private Bitmap imageBitmap = null;

    private ContactActivity contactActivity;

    private static final int REQUEST_PERMISSION_CAMERA = 110;
    private static final int REQUEST_PERMISSION_READ_STORAGE = 111;

    private static final int TAKE_PICTURE = 301;
    private static final int OPEN_GALLERY = 302;

    private int resultCode = -1;

    private ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {

                if(resultCode == TAKE_PICTURE){
                    Intent data = result.getData();

                    if(data != null) {
                        try {
                            imageBitmap = createImage ((Bitmap) data.getExtras().get("data"));
                            ivPhoto.setImageBitmap(imageBitmap);
                        } catch (Exception e){
                            Toast.makeText(contactActivity, "Se ha producido un error al cargar la foto", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                else if(resultCode == TAKE_PICTURE || resultCode == OPEN_GALLERY) {
                    Intent data = result.getData();

                    if(data != null) {
                        Uri imageUri = data.getData();

                        if(imageUri != null) {
                            try {
                                InputStream inputStream = contactActivity.getContentResolver().openInputStream(imageUri);
                                imageBitmap = createImage (BitmapFactory.decodeStream(inputStream));
                                ivPhoto.setImageBitmap(imageBitmap);
                            } catch (Exception e){
                                Toast.makeText(contactActivity, "Se ha producido un error al cargar la foto", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
    );

    public EditContactFragment() {
        // Required empty public constructor
    }

    public EditContactFragment(ContactModel contactModel, ContactActivity contactActivity) {
        this.contactModel = contactModel;
        this.contactActivity = contactActivity;
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
        fbSave = view.findViewById(R.id.fbEditFEC);
        bDelete = view.findViewById(R.id.fbDeleteFEC);
        bPhoto = view.findViewById(R.id.bChangePhotoFEC);

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
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(contactActivity.getContentResolver(), Uri.parse(contactModel.getPhoto()));
            } catch (IOException e) {
                e.printStackTrace();
            }
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

                    if(imageBitmap != null){
                        contactModel.setPhoto(getUriFromBitmap(imageBitmap).toString());
                    }

                    else {
                        contactModel.setPhoto(null);
                    }

                    ChangeFragment changeFragment = (ChangeFragment) getActivity();
                    changeFragment.change(3);

                } catch (Exception e){
                    System.out.println("Something went wrong...");
                    e.printStackTrace();
                    Toast.makeText(getContext(), "An error occurred...", Toast.LENGTH_LONG).show();
                }
            }
        });

        bDelete.setOnClickListener(v -> showDialogDelete());

        bPhoto.setOnClickListener(v -> {
            if (imageBitmap == null)
                showDialog();
            else
                showDialog2();
        });

        return view;

    }

    private void checkPermissionGallery() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(ActivityCompat.checkSelfPermission(contactActivity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(contactActivity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_READ_STORAGE);
            }
        }
    }

    private void checkPermissionCamera() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(ActivityCompat.checkSelfPermission(contactActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(contactActivity, new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION_CAMERA);
            }
        }
    }

    public void showDialog() {
        final CharSequence[] option = {"Abrir cámara", "Abrir galeria", "Cancelar"};

        final AlertDialog.Builder builder = new AlertDialog.Builder(contactActivity);
        builder.setTitle("Elige una opción");
        builder.setItems(option, (dialog, which) -> {
            if(option[which] == "Abrir cámara")
                openCamera();
            else if(option[which] == "Abrir galeria")
                openGallery();
            else if (option[which] == "Cancelar")
                dialog.dismiss();

        });

        builder.show();
    }

    public void showDialog2() {
        final CharSequence[] option = {"Abrir cámara", "Abrir galeria", "Eliminar foto", "Cancelar"};

        final AlertDialog.Builder builder = new AlertDialog.Builder(contactActivity);
        builder.setTitle("Elige una opción");
        builder.setItems(option, (dialog, which) -> {
            if(option[which] == "Abrir cámara")
                openCamera();
            else if(option[which] == "Abrir galeria")
                openGallery();
            else if (option[which] == "Cancelar")
                dialog.dismiss();
            else {
                imageBitmap = null;
                ivPhoto.setImageResource(R.drawable.ic_default_contact_photo);
            }

        });

        builder.show();
    }

    public void showDialogDelete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(contactActivity);

        builder.setTitle("Eliminar el contacto");
        builder.setMessage("¿Estas seguro?");

        builder.setPositiveButton("SÍ", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "Contacto eliminado", Toast.LENGTH_LONG).show();
                ChangeFragment changeFragment = (ChangeFragment) getActivity();
                changeFragment.change(2);
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void openCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        checkPermissionCamera();
        activityResultLauncher.launch(intent);
        resultCode = TAKE_PICTURE;
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        checkPermissionGallery();
        intent.setType("image/*");
        activityResultLauncher.launch(intent);
        resultCode = OPEN_GALLERY;
    }

    private Bitmap createImage(Bitmap bitmap){

        if (bitmap.getWidth() >= bitmap.getHeight())
            return Bitmap.createBitmap(bitmap, bitmap.getWidth()/2 - bitmap.getHeight()/2,0, bitmap.getHeight(), bitmap.getHeight());
        else
            return Bitmap.createBitmap(bitmap, 0, bitmap.getHeight()/2 - bitmap.getWidth()/2, bitmap.getWidth(), bitmap.getWidth());
    }

    private Uri getUriFromBitmap(Bitmap bitmap){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(contactActivity.getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }
}