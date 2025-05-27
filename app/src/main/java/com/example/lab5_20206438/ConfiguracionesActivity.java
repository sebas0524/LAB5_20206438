package com.example.lab5_20206438;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ConfiguracionesActivity extends AppCompatActivity {

    private EditText etNombre, etMensaje;
    private Spinner spFrecuencia;
    private final String[] frecuencias = {"6", "8", "12", "24"}; // en horas

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuraciones);

        etNombre = findViewById(R.id.etNombre);
        etMensaje = findViewById(R.id.etMensaje);
        spFrecuencia = findViewById(R.id.spFrecuencia);
        Button btnGuardar = findViewById(R.id.btnGuardar);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, frecuencias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFrecuencia.setAdapter(adapter);

        SharedPreferences prefs = getSharedPreferences("MisPrefs", MODE_PRIVATE);
        etNombre.setText(prefs.getString("nombre", ""));
        etMensaje.setText(prefs.getString("mensaje", ""));
        String frecuenciaGuardada = prefs.getString("frecuencia", "8");
        int idx = adapter.getPosition(frecuenciaGuardada);
        spFrecuencia.setSelection(idx);

        btnGuardar.setOnClickListener(v -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("nombre", etNombre.getText().toString());
            editor.putString("mensaje", etMensaje.getText().toString());
            editor.putString("frecuencia", spFrecuencia.getSelectedItem().toString());
            editor.apply();
            Toast.makeText(this, "Configuración guardada", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}