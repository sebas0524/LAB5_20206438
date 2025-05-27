package com.example.lab5_20206438;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SharedPrefManager {

    private static final String PREFS = "MisPrefs";
    private static final String KEY_LISTA = "ListaMedicamentos";

    public static void guardarLista(Context context, ArrayList<Medicamento> lista) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(lista);
        editor.putString(KEY_LISTA, json);
        editor.apply();
    }

    public static ArrayList<Medicamento> cargarLista(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_LISTA, null);
        if (json != null) {
            Type tipoLista = new TypeToken<ArrayList<Medicamento>>() {}.getType();
            return new Gson().fromJson(json, tipoLista);
        }
        return new ArrayList<>();
    }
}