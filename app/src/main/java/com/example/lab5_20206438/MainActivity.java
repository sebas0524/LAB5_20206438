package com.example.lab5_20206438;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private ImageView ivProfile;
    private TextView tvSaludo, tvMensaje;
    private static final int PICK_IMAGE = 100;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivProfile = findViewById(R.id.ivProfile);
        tvSaludo = findViewById(R.id.tvSaludo);
        tvMensaje = findViewById(R.id.tvMensaje);

        Button btnVer = findViewById(R.id.btnVerMedicamentos);
        Button btnConfig = findViewById(R.id.btnConfiguraciones);

        SharedPreferences prefs = getSharedPreferences("MisPrefs", MODE_PRIVATE);
        String nombre = prefs.getString("nombre", "Carlos");
        String mensaje = prefs.getString("mensaje", "¡Hoy es un buen día para cuidar tu salud!");

        tvSaludo.setText("Hola " + nombre);
        tvMensaje.setText(mensaje);

        ivProfile.setOnClickListener(v -> {
            Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickImage, PICK_IMAGE);
        });

        btnVer.setOnClickListener(v -> startActivity(new Intent(this, MedicamentosActivity.class)));
        btnConfig.setOnClickListener(v -> startActivity(new Intent(this, ConfiguracionesActivity.class)));

        // Cargar imagen desde internal storage
        Bitmap savedImage = StorageUtils.loadImageFromInternalStorage(this, "profile_image.png");
        if (savedImage != null) ivProfile.setImageBitmap(savedImage);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                ivProfile.setImageBitmap(bitmap);
                StorageUtils.saveImageToInternalStorage(this, bitmap, "profile_image.png");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}