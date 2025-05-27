package com.example.lab5_20206438;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MedicamentosActivity extends AppCompatActivity {

    private RecyclerView rvMedicamentos;
    private MedicamentoAdapter adapter;
    private ArrayList<Medicamento> listaMedicamentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamentos);

        rvMedicamentos = findViewById(R.id.rvMedicamentos);
        Button btnAgregar = findViewById(R.id.btnAgregar);

        listaMedicamentos = SharedPrefManager.cargarLista(this);

        adapter = new MedicamentoAdapter(this,listaMedicamentos);
        rvMedicamentos.setLayoutManager(new LinearLayoutManager(this));
        rvMedicamentos.setAdapter(adapter);

        btnAgregar.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegistrarMedicamentoActivity.class);
            startActivityForResult(intent, 1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Medicamento nuevo = (Medicamento) data.getSerializableExtra("medicamento");
            listaMedicamentos.add(nuevo);
            adapter.notifyItemInserted(listaMedicamentos.size() - 1);
            SharedPrefManager.guardarLista(this, listaMedicamentos);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Vuelve a cargar la lista completa por si hubo cambios
        listaMedicamentos.clear();
        listaMedicamentos.addAll(SharedPrefManager.cargarLista(this));
        adapter.notifyDataSetChanged();
    }
}