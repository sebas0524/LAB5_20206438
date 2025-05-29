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

/*public class ConfiguracionesActivity extends AppCompatActivity {

    private EditText etNombre, etMensaje, etFrecuencia;
    Button btnGuardar;
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
        cargarDatos();
        cancelarAlarmaMotivacional();

        findViewById(R.id.btnGuardar).setOnClickListener(v -> {
            guardarDatos();
            programarNotificacionMotivacional();
            Toast.makeText(this, "Configuración guardada", Toast.LENGTH_SHORT).show();
            finish(); // Volver a MainActivity
        });
        btnGuardar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString();
            String mensaje = etMensaje.getText().toString();
            int frecuencia = Integer.parseInt(etFrecuencia.getText().toString());

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("nombre", nombre);
            editor.putString("mensaje", mensaje);
            editor.putInt("frecuencia", frecuencia);
            editor.apply();

            programarAlarmaMotivacional(frecuencia);

            Toast.makeText(this, "Configuración guardada", Toast.LENGTH_SHORT).show();
            finish();
        });

    }

    private void cargarDatos() {
        etNombre.setText(prefs.getString("nombre", ""));
        etMensaje.setText(prefs.getString("mensaje", ""));
        etFrecuencia.setText(String.valueOf(prefs.getInt("frecuencia", 6)));
    }

    private void guardarDatos() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("nombre", etNombre.getText().toString());
        editor.putString("mensaje", etMensaje.getText().toString());

        int frecuencia = 6;
        try {
            frecuencia = Integer.parseInt(etFrecuencia.getText().toString());
        } catch (NumberFormatException e) {

        }
        editor.putInt("frecuencia", frecuencia);
        editor.apply();
    }
    private void cargarPreferencias() {
        etNombre.setText(prefs.getString("nombre", ""));
        etMensaje.setText(prefs.getString("mensaje", "¡Sigue adelante!"));
        etFrecuencia.setText(String.valueOf(prefs.getInt("frecuencia", 24)));
    }


    private void programarNotificacionMotivacional() {
        int frecuenciaHoras = prefs.getInt("frecuencia", 6);

        long intervaloMillis = frecuenciaHoras * 60L * 60L * 1000L;
        long tiempoInicial = System.currentTimeMillis() + intervaloMillis;

        Intent intent = new Intent(this, MotivacionReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1001, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, tiempoInicial, intervaloMillis, pendingIntent);
    }
    private void programarAlarmaMotivacional(int frecuenciaHoras) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, MotivacionReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 2001, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        long intervalo = frecuenciaHoras * 60 * 60 * 1000L;
        long primerDisparo = System.currentTimeMillis() + intervalo;

        if (alarmManager != null) {
            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    primerDisparo,
                    intervalo,
                    pendingIntent
            );
        }
    }
    private void cancelarAlarmaMotivacional() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, MotivacionReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 2001, intent, PendingIntent.FLAG_NO_CREATE | PendingIntent.FLAG_IMMUTABLE);

        if (pendingIntent != null && alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }


}*/
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

        // UN SOLO OnClickListener para btnGuardar
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
                if (frecuencia <= 0) frecuencia = 6; // validar que sea positivo
            }
        } catch (NumberFormatException e) {
            frecuencia = 6; // usar valor por defecto si hay error
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
                1001, // mismo ID que en MainActivity
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        long intervaloMillis = frecuenciaHoras * 60L * 60L * 1000L;
        long tiempoInicial = System.currentTimeMillis() + intervaloMillis;

        if (alarmManager != null) {
            // Para Android 6.0+ usar setExactAndAllowWhileIdle para mayor precisión
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