package ro.ase.com.onlineshop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

public class AddHainaActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText editTextNume, editTextBrand, editTextDescriere, editTextCategorie, editTextCuloare, editTextPret, editTextMarime, editTextStoc;
    private ImageView imageViewHaina;
    private Button buttonAddHaina, buttonChooseImage;

    private Uri imageUri;

    private FirebaseStorage storage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_haina);

        editTextNume = findViewById(R.id.editTextNume);
        editTextBrand = findViewById(R.id.editTextBrand);
        editTextDescriere = findViewById(R.id.editTextDescriere);
        editTextCategorie = findViewById(R.id.editTextCategorie);
        editTextCuloare = findViewById(R.id.editTextCuloare);
        editTextPret = findViewById(R.id.editTextPret);
        editTextMarime = findViewById(R.id.editTextMarime); // Adăugăm câmp pentru mărime
        editTextStoc = findViewById(R.id.editTextStoc); // Adăugăm câmp pentru stoc
        imageViewHaina = findViewById(R.id.imageViewHaina);
        buttonAddHaina = findViewById(R.id.buttonAdauga);
        buttonChooseImage = findViewById(R.id.buttonChooseImage);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        buttonChooseImage.setOnClickListener(v -> openFileChooser());
        buttonAddHaina.setOnClickListener(v -> uploadImage());
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imageViewHaina.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {
        if (imageUri != null) {
            StorageReference fileReference = storageReference.child("haine/" + System.currentTimeMillis() + ".jpg");
            fileReference.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                            String imagineUrl = uri.toString();
                            addHainaToDatabase(imagineUrl);
                        });
                    })
                    .addOnFailureListener(e -> Toast.makeText(AddHainaActivity.this, "Eroare la încărcarea imaginii", Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(this, "Alege o imagine", Toast.LENGTH_SHORT).show();
        }
    }

    private void addHainaToDatabase(String imagineUrl) {
        String nume = editTextNume.getText().toString().trim();
        String brand = editTextBrand.getText().toString().trim();
        String descriere = editTextDescriere.getText().toString().trim();
        String categorie = editTextCategorie.getText().toString().trim();
        String culoare = editTextCuloare.getText().toString().trim();
        double pret = Double.parseDouble(editTextPret.getText().toString().trim());
        String marime = editTextMarime.getText().toString().trim(); // Obține mărimea
        int stoc = Integer.parseInt(editTextStoc.getText().toString().trim()); // Obține stocul

        Haina haina = new Haina(nume, brand, descriere, categorie, culoare, pret, imagineUrl, marime, stoc);

        FirebaseFirestore.getInstance().collection("haine").add(haina)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(AddHainaActivity.this, "Haina adăugată cu succes", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(AddHainaActivity.this, "Eroare la adăugarea hainei", Toast.LENGTH_SHORT).show());
    }
}
