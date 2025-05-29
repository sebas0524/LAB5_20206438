package com.example.lab5_20206438;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ConfiguracionesActivity extends AppCompatActivity {

    private EditText etNombre, etMensaje, etFrecuencia;
    private Button btnGuardar;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuraciones);

        etNombre = findViewById(R.id.etNombre);
        etMensaje = findViewById(R.id.etMensaje);
        etFrecuencia = findViewById(R.id.etFrecuencia);
        btnGuardar = findViewById(R.id.btnGuardar);
        prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);

        cargarPreferencias();
        cancelarAlarmaMotivacional();

        btnGuardar.setOnClickListener(v -> {
            guardarDatos();
            programarAlarmaMotivacional();
            Toast.makeText(this, "Configuración guardada", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void cargarPreferencias() {
        etNombre.setText(prefs.getString("nombre", ""));
        etMensaje.setText(prefs.getString("mensaje", "¡Sigue adelante!"));
        etFrecuencia.setText(String.valueOf(prefs.getInt("frecuencia", 6)));
    }

    private void guardarDatos() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("nombre", etNombre.getText().toString());
        editor.putString("mensaje", etMensaje.getText().toString());

        int frecuencia = 6; // valor por defecto
        try {
            String frecuenciaText = etFrecuencia.getText().toString().trim();
            if (!frecuenciaText.isEmpty()) {
                frecuencia = Integer.parseInt(frecuenciaText);
                if (frecuencia <= 0) frecuencia = 6;
            }
        } catch (NumberFormatException e) {
            frecuencia = 6; //
        }

        editor.putInt("frecuencia", frecuencia);
        editor.apply();
    }

    private void programarAlarmaMotivacional() {
        int frecuenciaHoras = prefs.getInt("frecuencia", 6);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, MotivacionReceiver.class);

        // Usar ID único y consistente
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                1001, //
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        long intervaloMillis = frecuenciaHoras * 60L * 60L * 1000L;
        long tiempoInicial = System.currentTimeMillis() + intervaloMillis;

        if (alarmManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setRepeating(
                        AlarmManager.RTC_WAKEUP,
                        tiempoInicial,
                        intervaloMillis,
                        pendingIntent
                );
            } else {
                alarmManager.setRepeating(
                        AlarmManager.RTC_WAKEUP,
                        tiempoInicial,
                        intervaloMillis,
                        pendingIntent
                );
            }
        }
    }

    private void cancelarAlarmaMotivacional() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, MotivacionReceiver.class);

        // Usar el mismo ID que para programar
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                1001,
                intent,
                PendingIntent.FLAG_NO_CREATE | PendingIntent.FLAG_IMMUTABLE
        );

        if (pendingIntent != null && alarmManager != null) {
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
        }
    }
}