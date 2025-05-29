package com.example.lab5_20206438;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MedicamentoAdapter extends RecyclerView.Adapter<MedicamentoAdapter.ViewHolder> {
    private List<Medicamento> lista;
    private Context context;
    private SharedPreferences prefs;
    private Gson gson = new Gson();

    public MedicamentoAdapter(Context context, List<Medicamento> lista) {
        this.context = context;
        this.lista = lista;
        this.prefs = context.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medicamento, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Medicamento m = lista.get(position);
        holder.tvNombre.setText(m.getNombre());
        holder.tvTipo.setText("Tipo: " + m.getTipo());
        holder.tvDosis.setText("Dosis: " + m.getDosis());
        holder.tvFrecuencia.setText("Cada " + m.getFrecuenciaHoras() + " horas");
        holder.tvFechaHora.setText("Inicio: " + m.getFechaHoraInicio());
        holder.btnEliminar.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                new AlertDialog.Builder((Activity) context)
                        .setTitle("Eliminar medicamento")
                        .setMessage("¿Estás seguro de que querés eliminar este medicamento?")
                        .setPositiveButton("Sí", (dialog, which) -> {
                            lista.remove(pos);
                            notifyItemRemoved(pos);
                            guardarMedicamentos();
                            Toast.makeText(context, "Medicamento eliminado", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Cancelar", null)
                        .show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNombre, tvTipo, tvDosis, tvFrecuencia, tvFechaHora;
        ImageButton btnEliminar;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvTipo = itemView.findViewById(R.id.tvTipo);
            tvDosis = itemView.findViewById(R.id.tvDosis);
            tvFrecuencia = itemView.findViewById(R.id.tvFrecuencia);
            tvFechaHora = itemView.findViewById(R.id.tvFechaHora);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
    private void guardarMedicamentos() {
        String json = gson.toJson(lista);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("medicamentos", json);
        editor.apply();
    }
}
