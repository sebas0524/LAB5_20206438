package com.example.lab5_20206438;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import java.util.ArrayList;

public class MedicamentoAdapter extends RecyclerView.Adapter<MedicamentoAdapter.ViewHolder> {

    private final ArrayList<Medicamento> lista;

    public MedicamentoAdapter(ArrayList<Medicamento> lista) {
        this.lista = lista;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvFrecuencia, tvTipo, tvDosis, tvFechaInicio;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvFrecuencia = itemView.findViewById(R.id.tvFrecuencia);
            tvTipo = itemView.findViewById(R.id.tvTipo);
            tvDosis =itemView.findViewById(R.id.tvDosis);
            tvFechaInicio = itemView.findViewById(R.id.tvFechaInicio);
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
        h.tvDosis.setText(m.getDosis());
        h.tvFechaInicio.setText(m.getFechaHoraInicio());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}