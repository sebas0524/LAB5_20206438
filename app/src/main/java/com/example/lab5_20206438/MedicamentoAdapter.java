package com.example.lab5_20206438;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

/*import java.util.ArrayList;

public class MedicamentoAdapter extends RecyclerView.Adapter<MedicamentoAdapter.ViewHolder> {

    private final ArrayList<Medicamento> lista;

    public MedicamentoAdapter(ArrayList<Medicamento> lista) {
        this.lista = lista;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvFrecuencia, tvTipo;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvFrecuencia = itemView.findViewById(R.id.tvFrecuencia);
            tvTipo = itemView.findViewById(R.id.tvTipo);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medicamento, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder h, int pos) {
        Medicamento m = lista.get(pos);
        h.tvNombre.setText(m.getNombre());
        h.tvFrecuencia.setText("Cada " + m.getFrecuencia() + " hrs");
        h.tvTipo.setText(m.getTipo());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}*/
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MedicamentoAdapter extends RecyclerView.Adapter<MedicamentoAdapter.ViewHolder> {

    private final ArrayList<Medicamento> lista;
    private final Context context;

    public MedicamentoAdapter(Context context,ArrayList<Medicamento> lista) {
        this.context = context;
        this.lista = lista;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvFrecuencia, tvTipo, tvDosis, tvFechaInicio;
        ImageView ivEliminar;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvFrecuencia = itemView.findViewById(R.id.tvFrecuencia);
            tvTipo = itemView.findViewById(R.id.tvTipo);
            tvDosis =itemView.findViewById(R.id.tvDosis);
            tvFechaInicio = itemView.findViewById(R.id.tvFechaInicio);
            ivEliminar = itemView.findViewById(R.id.ivEliminar);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medicamento, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder h, int pos) {
        Medicamento m = lista.get(pos);
        h.tvNombre.setText(m.getNombre());
        h.tvFrecuencia.setText("Cada " + m.getFrecuencia() + " horas");
        h.tvTipo.setText("Tipo: " + m.getTipo());
        h.tvDosis.setText("Dosis: " + m.getDosis());
        //h.tvFechaInicio.setText(String.valueOf(m.getFechaHoraInicio()));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        h.tvFechaInicio.setText("Inicio: " + sdf.format(new Date(m.getFechaHoraInicio())));
        /*h.ivEliminar.setOnClickListener(v -> {
            lista.remove(pos);
            notifyItemRemoved(pos);
            guardarEnSharedPreferences();
            Toast.makeText(context, "Medicamento eliminado", Toast.LENGTH_SHORT).show();
        });*/
        h.ivEliminar.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Eliminar medicamento")
                    .setMessage("¿Estás seguro de que deseas eliminar este medicamento?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        lista.remove(pos);
                        notifyItemRemoved(pos);
                        notifyItemRangeChanged(pos, lista.size());
                        SharedPrefManager.guardarLista(context, lista); // Guardar cambios
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        });

    }


    @Override
    public int getItemCount() {
        return lista.size();
    }
    /*private void guardarEnSharedPreferences() {
        SharedPreferences prefs = context.getSharedPreferences("MisPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        editor.putString("medicamentos", gson.toJson(lista));
        editor.apply();
    }*/
}