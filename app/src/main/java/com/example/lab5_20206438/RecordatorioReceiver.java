package com.example.lab5_20206438;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.Manifest;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class RecordatorioReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String nombre = intent.getStringExtra("nombre");
        String dosis = intent.getStringExtra("dosis");
        String tipo = intent.getStringExtra("tipo");


        String canalId = tipo.toLowerCase();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, canalId)
                .setSmallIcon(R.drawable.ic_medicina)
                .setContentTitle("Hora de tu medicamento")
                .setContentText("Tomar " + dosis + " de " + nombre)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManagerCompat manager = NotificationManagerCompat.from(context);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            manager.notify((int) System.currentTimeMillis(), builder.build());
        }
    }
}

