package com.example.lab5_20206438;

import java.io.Serializable;

public class Medicamento {
    private String nombre;
    private String tipo;
    private String dosis;
    private int frecuenciaHoras;
    private String fechaHoraInicio;

    public Medicamento(String nombre, String tipo, String dosis, int frecuenciaHoras, String fechaHoraInicio) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.dosis = dosis;
        this.frecuenciaHoras = frecuenciaHoras;
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public String getNombre() { return nombre; }
    public String getTipo() { return tipo; }
    public String getDosis() { return dosis; }
    public int getFrecuenciaHoras() { return frecuenciaHoras; }
    public String getFechaHoraInicio() { return fechaHoraInicio; }
}
