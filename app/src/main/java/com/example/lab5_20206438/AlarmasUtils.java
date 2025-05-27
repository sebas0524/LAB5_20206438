package com.example.lab5_20206438;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class AlarmasUtils {

    public static void programarAlarmaMedicamentos(Context context, int horas) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, MedicamentoReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 101, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        long intervalo = horas * 60 * 60 * 1000L; // horas a milisegundos
        long inicio = System.currentTimeMillis() + intervalo;

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, inicio, intervalo, pendingIntent);
    }

    public static void programarAlarmaMotivacional(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, MotivacionalReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 102, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long trigger = calendar.getTimeInMillis();

        if (System.currentTimeMillis() > trigger) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            trigger = calendar.getTimeInMillis();
        }

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, trigger, AlarmManager.INTERVAL_DAY, pendingIntent);
    }
}