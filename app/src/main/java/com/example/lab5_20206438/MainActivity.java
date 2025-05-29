package com.example.lab5_20206438;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView ivFoto;
    private TextView tvSaludo, tvMotivacion;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivFoto = findViewById(R.id.ivFoto);
        tvSaludo = findViewById(R.id.tvSaludo);
        tvMotivacion = findViewById(R.id.tvMotivacion);
        prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);

        cargarDatos();
        crearCanalesNotificacion();
        crearCanalMotivacion();
        programarMotivacionDiaria();


        ivFoto.setOnClickListener(v -> abrirGaleria());

        findViewById(R.id.btnVerMedicamentos).setOnClickListener(v ->
                startActivity(new Intent(this, MedicamentosActivity.class)));

        findViewById(R.id.btnConfiguraciones).setOnClickListener(v ->
                startActivity(new Intent(this, ConfiguracionesActivity.class)));
    }

    private void cargarDatos() {
        String nombre = prefs.getString("nombre", "Valeria");
        String mensaje = prefs.getString("mensaje", "¡Hoy es un buen día para cuidad tu salud!");
        tvSaludo.setText("¡Hola, " + nombre + "!");
        tvMotivacion.setText(mensaje);

        try {
            File file = new File(getFilesDir(), "perfil.jpg");
            if (file.exists()) {
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
                ivFoto.setImageBitmap(bitmap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            ivFoto.setImageURI(imageUri);

            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                FileOutputStream fos = openFileOutput("perfil.jpg", MODE_PRIVATE);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void crearCanalMotivacion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "motivacion",
                    "Notificaciones Motivacionales",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.enableVibration(true);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
    private void crearCanalesNotificacion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = getSystemService(NotificationManager.class);

            // Canales para medicamentos
            crearCanal(manager, "pastilla", "Pastillas", NotificationManager.IMPORTANCE_HIGH);
            crearCanal(manager, "jarabe", "Jarabes", NotificationManager.IMPORTANCE_HIGH);
            crearCanal(manager, "ampolla", "Ampollas", NotificationManager.IMPORTANCE_MAX);
            crearCanal(manager, "capsula", "Cápsulas", NotificationManager.IMPORTANCE_HIGH);
            crearCanal(manager, "inyeccion", "Inyecciones", NotificationManager.IMPORTANCE_MAX);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void crearCanal(NotificationManager manager, String id, String nombre, int importancia) {
        NotificationChannel canal = new NotificationChannel(id, nombre, importancia);
        canal.enableVibration(true);
        canal.setDescription("Notificaciones para " + nombre);
        manager.createNotificationChannel(canal);
    }


    private void programarMotivacionDiaria() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, MotivacionReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1001, intent, PendingIntent.FLAG_IMMUTABLE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);


        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,
                pendingIntent
        );
    }


}
