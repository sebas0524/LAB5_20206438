package com.example.lab5_20206438;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class RegistrarMedicamentoActivity extends AppCompatActivity {

    private EditText etNombre, etDosis, etFrecuencia;
    private Spinner spinnerTipo;
    private TextView tvFechaHora;
    private Button btnGuardar;

    private Calendar fechaHoraInicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_medicamento);

        etNombre = findViewById(R.id.et_nombre);
        etDosis = findViewById(R.id.et_dosis);
        etFrecuencia = findViewById(R.id.et_frecuencia);
        spinnerTipo = findViewById(R.id.spinner_tipo);
        tvFechaHora = findViewById(R.id.tv_fecha_hora);
        btnGuardar = findViewById(R.id.btn_guardar);

        // Cargar tipos
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tipos_medicamento, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipo.setAdapter(adapter);

        fechaHoraInicio = Calendar.getInstance();

        tvFechaHora.setOnClickListener(v -> mostrarDatePicker());

        btnGuardar.setOnClickListener(v -> guardarMedicamento());
    }

    private void mostrarDatePicker() {
        int año = fechaHoraInicio.get(Calendar.YEAR);
        int mes = fechaHoraInicio.get(Calendar.MONTH);
        int dia = fechaHoraInicio.get(Calendar.DAY_OF_MONTH);

        new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            fechaHoraInicio.set(Calendar.YEAR, year);
            fechaHoraInicio.set(Calendar.MONTH, month);
            fechaHoraInicio.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            mostrarTimePicker();
        }, año, mes, dia).show();
    }

    private void mostrarTimePicker() {
        int hora = fechaHoraInicio.get(Calendar.HOUR_OF_DAY);
        int minuto = fechaHoraInicio.get(Calendar.MINUTE);

        new TimePickerDialog(this, (view, hourOfDay, minute) -> {
            fechaHoraInicio.set(Calendar.HOUR_OF_DAY, hourOfDay);
            fechaHoraInicio.set(Calendar.MINUTE, minute);

            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            tvFechaHora.setText(sdf.format(fechaHoraInicio.getTime()));
        }, hora, minuto, true).show();
    }

    private void guardarMedicamento() {
        String nombre = etNombre.getText().toString().trim();
        String tipo = spinnerTipo.getSelectedItem().toString();
        String dosis = etDosis.getText().toString().trim();
        String frecuenciaTexto = etFrecuencia.getText().toString().trim();

        if (nombre.isEmpty() || dosis.isEmpty() || frecuenciaTexto.isEmpty() || tvFechaHora.getText().toString().contains("Seleccionar")) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        int frecuencia;
        try {
            frecuencia = Integer.parseInt(frecuenciaTexto);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Frecuencia debe ser un número", Toast.LENGTH_SHORT).show();
            return;
        }

        Medicamento medicamento = new Medicamento(nombre, tipo, dosis, frecuencia, fechaHoraInicio.getTimeInMillis());

        ArrayList<Medicamento> lista = SharedPrefManager.cargarLista(this);
        lista.add(medicamento);
        SharedPrefManager.guardarLista(this, lista);

        Toast.makeText(this, "Medicamento registrado", Toast.LENGTH_SHORT).show();
        finish();
    }

}
