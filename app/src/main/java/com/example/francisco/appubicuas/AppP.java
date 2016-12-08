package com.example.francisco.appubicuas;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.example.francisco.appubicuas.receivers.BeaconReceiver;

import java.util.List;
import java.util.UUID;


public class AppP extends Application {

    static final UUID BEACON_UUID = UUID.fromString("f7826da6-4fa2-4e98-8024-bc5b71e0893e");

    private BeaconManager beaconManager;

    @Override
    public void onCreate() {
        super.onCreate();

        beaconManager = new BeaconManager(getApplicationContext());
        beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {
            @Override
            public void onEnteredRegion(Region region, List<Beacon> list) {
                Log.e("Location", "Tamaño de la lista :"+list.size());
                Log.e("Location", "major del beacon :"+list.get(0).getMajor());
                Intent intent = new Intent(BeaconReceiver.ACTION_BEACON);
                intent.putExtra(BeaconReceiver.EXTRA_MAJOR, list.get(0).getMajor());
                intent.putExtra(BeaconReceiver.EXTRA_MINOR, list.get(0).getMinor());
                sendBroadcast(intent);
                showNotification("Nueva promocion encontrada", "pulse para ver");

            }

            @Override
            public void onExitedRegion(Region region) {
                showNotification("Hola comprador","Estamos buscando mas promociones para tí");

            }
        });
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startMonitoring(new Region("monitored region",
                        UUID.fromString("f7826da6-4fa2-4e98-8024-bc5b71e0893e"), null, null));
            }
        });
    }
    public void showNotification(String title, String message) {
        Intent notifyIntent = new Intent(this, BeaconsPromotionsActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
                new Intent[]{notifyIntent}, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }
}
