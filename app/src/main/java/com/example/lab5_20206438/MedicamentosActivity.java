package com.example.lab5_20206438;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MedicamentosActivity extends AppCompatActivity {

    private RecyclerView rv;
    private MedicamentoAdapter adapter;
    private List<Medicamento> lista;
    private SharedPreferences prefs;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamentos);

        gson = new Gson();
        prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        rv = findViewById(R.id.rvMedicamentos);
        rv.setLayoutManager(new LinearLayoutManager(this));

        lista = cargarMedicamentos();
        adapter = new MedicamentoAdapter(this,lista);
        rv.setAdapter(adapter);

        findViewById(R.id.fabAgregarMedicamento).setOnClickListener(v -> {
            Intent i = new Intent(this, RegistroMedicamentoActivity.class);
            startActivity(i);
        });
        findViewById(R.id.btnRegresar).setOnClickListener(v -> finish());
    }

    private List<Medicamento> cargarMedicamentos() {
        String json = prefs.getString("medicamentos", "");
        if (json.isEmpty()) return new ArrayList<>();
        Type tipoLista = new TypeToken<List<Medicamento>>(){}.getType();
        return gson.fromJson(json, tipoLista);
    }

    @Override
    protected void onResume() {
        super.onResume();
        lista.clear();
        lista.addAll(cargarMedicamentos());
        adapter.notifyDataSetChanged();

        if (lista.isEmpty()) {
            Toast.makeText(this, "No hay medicamentos registrados", Toast.LENGTH_LONG).show();
        }
    }
}
