package com.example.lab5_20206438;

import java.io.Serializable;

public class Medicamento implements Serializable {
    private String nombre;
    private int frecuencia; // en horas
    private String dosis;
    private String tipo; // Pastilla, Jarabe, Inyección
    private long fechaHoraInicio; // Guardado como timestamp

    public Medicamento(String nombre,String tipo,String dosis, int frecuencia, long fechaHoraInicio) {
        this.nombre = nombre;
        this.frecuencia = frecuencia;
        this.dosis = dosis;
        this.tipo = tipo;
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public String getNombre() { return nombre; }
    public int getFrecuencia() { return frecuencia; }
    public String getTipo() { return tipo; }
    public String getDosis() { return dosis; }
    public CharSequence getFechaHoraInicio() { return fechaHoraInicio; }
}