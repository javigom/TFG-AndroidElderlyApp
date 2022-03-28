package com.example.simplecall.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.simplecall.R;
import com.example.simplecall.model.ContactModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class AddContactActivity extends AppCompatActivity {

    private int isStarred = 0;
    private EditText etName, etPhone;
    private FloatingActionButton floatingActionButton;
    private Button bChangePhoto;
    private ImageView ivPhoto;

    private static final int ADD_CONTACT = 203;

    private static final int REQUEST_PERMISSION_CAMERA = 110;
    private static final int REQUEST_PERMISSION_READ_STORAGE = 111;

    private static final int TAKE_PICTURE = 301;
    private static final int OPEN_GALLERY = 302;

    private int resultCode = -1;

    private Bitmap imageBitmap = null;

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
                            Toast.makeText(this, "Se ha producido un error al cargar la foto", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                else if(resultCode == TAKE_PICTURE || resultCode == OPEN_GALLERY) {
                    Intent data = result.getData();

                    if(data != null) {
                        Uri imageUri = data.getData();

                        if(imageUri != null) {
                            try {
                                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                                imageBitmap = createImage (BitmapFactory.decodeStream(inputStream));
                                ivPhoto.setImageBitmap(imageBitmap);
                            } catch (Exception e){
                                Toast.makeText(this, "Se ha producido un error al cargar la foto", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        initActionBar();
        initView();
    }

    public void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
    }

    public void initView(){
        etName = findViewById(R.id.etNameAAC);
        etPhone = findViewById(R.id.etNumberAAC);
        bChangePhoto = findViewById(R.id.bChangePhotoAAC);
        bChangePhoto.setOnClickListener(v -> {
            if (imageBitmap == null)
                showDialog();
            else
                showDialog2();
        });

        floatingActionButton = findViewById(R.id.fbEditFDC);
        floatingActionButton.setOnClickListener(v -> {

            String name = etName.getText().toString();
            String phone = etPhone.getText().toString();

            if(name.length() > 0 && phone.length() > 0){

                String stringImageBitmap = null;

                if(imageBitmap != null){
                    stringImageBitmap = getUriFromBitmap(imageBitmap).toString();
                }

                ContactModel contactModel = new ContactModel((long) -1, name, phone, stringImageBitmap, isStarred);
                Intent intent = new Intent(AddContactActivity.this, MainActivity.class);
                intent.putExtra("contact", contactModel);
                setResult(ADD_CONTACT, intent);
                finish();
            }
            else{
                Toast.makeText(getApplicationContext(), "Algún campo está vacío", Toast.LENGTH_LONG).show();
            }
        });

        ivPhoto = findViewById(R.id.ivPhotoAAC);
    }

    /*
    public void onRequestPermissionsResult(int requestCode, @Nullable String[] permissions, @NonNull int[] grantResults){
        if(requestCode == REQUEST_PERMISSION_CAMERA || requestCode == REQUEST_PERMISSION_READ_STORAGE){
            if(permissions.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED){
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }*/

    private void checkPermissionGallery() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_READ_STORAGE);
            }
        }
    }

    private void checkPermissionCamera() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION_CAMERA);
            }
        }
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showDialog() {
        final CharSequence[] option = {"Abrir cámara", "Abrir galeria", "Cancelar"};

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
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

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }

}