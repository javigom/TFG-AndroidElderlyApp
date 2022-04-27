package com.example.simplemedicine.usecases.addmedication.page;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.simplemedicine.R;
import com.example.simplemedicine.databinding.FragmentAddMedicationPage1Binding;
import com.example.simplemedicine.model.medication.Medication;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

import petrov.kristiyan.colorpicker.ColorPicker;

public class AddMedicationPage1Fragment extends Fragment {

    // ATTRIBUTES

    private FragmentAddMedicationPage1Binding binding;
    private final boolean editMode;
    private final Medication medication;
    private Bitmap imageBitmap;

    private static final int PERMISSION_REQUEST_CAMERA = 100;

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Intent data = result.getData();
                if(data != null) {
                    try {
                        imageBitmap = createImage ((Bitmap) data.getExtras().get("data"));
                        binding.image.setImageBitmap(imageBitmap);
                    } catch (Exception e){
                        Toast.makeText(getContext(), "Se ha producido un error al cargar la foto", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );


    // CONSTRUCTOR

    public AddMedicationPage1Fragment(boolean editMode, Medication medication) {
        this.editMode = editMode;
        this.medication = medication;
    }


    // METHODS

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState) {
        binding = FragmentAddMedicationPage1Binding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        if (editMode) {
            if (medication.getPhoto() != null)
                binding.image.setImageURI(Uri.parse(medication.getPhoto()));
            binding.name.setText(medication.getName());
            binding.description.setText(medication.getDescription());
        }

        binding.colorButton.setBackgroundColor(medication.getColor());
        binding.image.setOnClickListener(v -> showCameraPreview());
        binding.description.setImeOptions(EditorInfo.IME_ACTION_DONE);
        binding.description.setRawInputType(InputType.TYPE_CLASS_TEXT);
        binding.colorButton.setOnClickListener(v -> {
            final ColorPicker colorPicker = new ColorPicker(getActivity());
            colorPicker.setOnFastChooseColorListener(new ColorPicker.OnFastChooseColorListener() {
                @Override
                public void setOnFastChooseColorListener(int position, int color) {
                    medication.setColor(color);
                    binding.colorButton.setBackgroundColor(color);
                }
                @Override
                public void onCancel(){
                    // Nothing happens
                }
            })
                    .setDefaultColorButton(medication.getColor())
                    .setColors(R.array.default_color_picker)
                    .setRoundColorButton(true)
                    .setTitle("Elige un color")
                    .setColumns(5)
                    .show();
        });
    }

    protected boolean fillData(Medication medication) {
        if(Objects.requireNonNull(binding.name.getText()).toString().equals("")){
            return false;
        }

        if(imageBitmap != null)
            medication.setPhoto(getUriFromBitmap(imageBitmap).toString());

        medication.setName(binding.name.getText().toString());
        medication.setDescription(Objects.requireNonNull(binding.description.getText()).toString());

        return true;
    }

    private void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activityResultLauncher.launch(intent);
    }

    // Image methods

    private Bitmap createImage(@NonNull Bitmap bitmap){
        if (bitmap.getWidth() >= bitmap.getHeight())
            return Bitmap.createBitmap(bitmap, bitmap.getWidth()/2 - bitmap.getHeight()/2,0, bitmap.getHeight(), bitmap.getHeight());
        else
            return Bitmap.createBitmap(bitmap, 0, bitmap.getHeight()/2 - bitmap.getWidth()/2, bitmap.getWidth(), bitmap.getWidth());
    }

    private Uri getUriFromBitmap(@NonNull Bitmap bitmap){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap, medication.getName(), null);
        return Uri.parse(path);
    }

    // Permission methods

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Snackbar.make(getView(), "Permisos de cámara concedidos", Snackbar.LENGTH_SHORT).show();
                startCamera();
            } else {
                // Permission request was denied.
                Snackbar.make(getView(), "Permisos de cámara denegados", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    private void showCameraPreview() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
            startCamera();
        else
            requestCameraPermission();
    }

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {
            Snackbar.make(getView(), "Los permisos de cámara son necesarios",
                    Snackbar.LENGTH_INDEFINITE).setAction("Ok", view -> {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
                    }).show();
        } else {
            Snackbar.make(getView(), "Cámara no disponible", Snackbar.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
        }
    }

}
