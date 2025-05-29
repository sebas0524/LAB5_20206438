package com.example.lab5_20206438;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar; // ✅ Correcto


import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RegistroMedicamentoActivity extends AppCompatActivity {

    private EditText etNombre, etDosis, etFrecuencia, etFechaHora;
    private Spinner spTipo;
    private SharedPreferences prefs;
    private Gson gson;
    private List<Medicamento> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_medicamento);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Registrar Medicamento");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        etNombre = findViewById(R.id.etNombre);
        etDosis = findViewById(R.id.etDosis);
        etFrecuencia = findViewById(R.id.etFrecuencia);
        etFechaHora = findViewById(R.id.etFechaHora);
        spTipo = findViewById(R.id.spTipo);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tipos_medicamento, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipo.setAdapter(adapter);

        etFechaHora.setOnClickListener(v -> mostrarDialogoFechaHora());

        prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        gson = new Gson();

        lista = cargarMedicamentos();

        findViewById(R.id.btnGuardar).setOnClickListener(v -> guardarMedicamento());


    }

    private void mostrarDialogoFechaHora() {
        Calendar calendario = Calendar.getInstance();
        DatePickerDialog dp = new DatePickerDialog(this, (view, year, month, day) -> {
            TimePickerDialog tp = new TimePickerDialog(this, (view1, hour, minute) -> {
                String fechaHora = String.format("%02d/%02d/%04d %02d:%02d",
                        day, month + 1, year, hour, minute);
                etFechaHora.setText(fechaHora);
            }, calendario.get(Calendar.HOUR_OF_DAY), calendario.get(Calendar.MINUTE), true);
            tp.show();
        }, calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH), calendario.get(Calendar.DAY_OF_MONTH));
        dp.show();
    }

    private void guardarMedicamento() {
        String nombre = etNombre.getText().toString().trim();
        String tipo = spTipo.getSelectedItem().toString();
        String dosis = etDosis.getText().toString().trim();
        String frecuenciaStr = etFrecuencia.getText().toString().trim();
        String fechaHora = etFechaHora.getText().toString().trim();

        if (nombre.isEmpty() || dosis.isEmpty() || frecuenciaStr.isEmpty() || fechaHora.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        int frecuencia = Integer.parseInt(frecuenciaStr);

        Medicamento m = new Medicamento(nombre, tipo, dosis, frecuencia, fechaHora);
        lista.add(m);
        guardarLista(lista);
        programarAlarma(m);

        Toast.makeText(this, "Medicamento guardado", Toast.LENGTH_SHORT).show();
        finish();
    }

    private List<Medicamento> cargarMedicamentos() {
        String json = prefs.getString("medicamentos", "");
        if (json.isEmpty()) return new ArrayList<>();
        Type tipoLista = new TypeToken<List<Medicamento>>() {}.getType();
        return gson.fromJson(json, tipoLista);
    }

    private void guardarLista(List<Medicamento> lista) {
        String json = gson.toJson(lista);
        prefs.edit().putString("medicamentos", json).apply();
    }
    private void programarAlarma(Medicamento m) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, RecordatorioReceiver.class);
        intent.putExtra("nombre", m.getNombre());
        intent.putExtra("dosis", m.getDosis());
        intent.putExtra("tipo", m.getTipo());

        int requestCode = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, requestCode, intent, PendingIntent.FLAG_IMMUTABLE);

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        try {
            Date fechaInicio = formato.parse(m.getFechaHoraInicio());
            long tiempoInicial = fechaInicio.getTime();

            long intervalo = m.getFrecuenciaHoras() * 60 * 60 * 1000L;

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, tiempoInicial, intervalo, pendingIntent);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
