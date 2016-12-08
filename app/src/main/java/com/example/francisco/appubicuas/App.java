package com.example.francisco.appubicuas;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.List;
import java.util.UUID;

import com.example.francisco.appubicuas.receivers.BeaconReceiver;

import static android.R.id.list;


public class App extends Application implements BeaconManager.MonitoringListener{

    //static final UUID BEACON_UUID = UUID.fromString("d0d3fa86-ca76-45ec-9bd9-6af463b4390a");
    //static final UUID BEACON_UUID = UUID.fromString("d0d3fa86-ca76-45ec-9bd9-6af4d3a610aa");
    static final UUID BEACON_UUID = UUID.fromString("f7826da6-4fa2-4e98-8024-bc5b71e0893e");
    static final int MAJOR = 63149;
    static final int MINOR = 49036;
    static int i=0;

    BeaconManager manager;
    Region region;

    @Override
    public void onCreate() {
        super.onCreate();


        region = new Region("beacons", BEACON_UUID, null, null);
        manager =  new BeaconManager(getApplicationContext());
        manager.connect(new BeaconManager.ServiceReadyCallback() {
        @Override
        public void onServiceReady() {
         // manager.startMonitoring(new Region("beacons", BEACON_UUID, 22771, null));
         //manager.setMonitoringListener(App.this);
         manager.startRanging(region);
        manager.setRangingListener(new BeaconManager.RangingListener() {
                    @Override
                    public void onBeaconsDiscovered(Region region, List<Beacon> list) {
                        //for (i = 0; i<list.size(); i++) {
                                    Log.e("localizate", "Entro en el if");
                                    Intent intent = new Intent(BeaconReceiver.ACTION_BEACON);
                                    intent.putExtra(BeaconReceiver.EXTRA_MAJOR, list.get(0).getMajor());
                                    intent.putExtra(BeaconReceiver.EXTRA_MINOR, list.get(0).getMinor());

                                    Log.e("localizate", "pocision dentro de else = " + 0);
                                    Log.e("localizate", "tamaño de datos = " + list.size());
                                    sendBroadcast(intent);

                       // }
                    }

                });
            }

        });

    }


     @Override
    public void onEnteredRegion(Region region, List<Beacon> list) {
         Toast.makeText(this, "entro en la region", Toast.LENGTH_LONG).show();
         Log.e("localizate", "Entro en REgion");
         int i = 0;
         //Enviar un mensaje en BROADCAST
       /* Log.e("localizate", "fuera del for");
         for ( i=0; i<list.size(); i++) {
               Log.e("localizate", "Entro en el for");
            Intent intent = new Intent(BeaconReceiver.ACTION_BEACON);
            intent.putExtra(BeaconReceiver.EXTRA_MAJOR, list.get(i).getMajor());
            intent.putExtra(BeaconReceiver.EXTRA_MINOR, list.get(i).getMinor());

               Log.e("localizate", "pocision = "+i);
               Log.e("localizate", "tamaño de datos = "+list.size());
            sendBroadcast(intent);


              /* Intent notifyIntent = new Intent(this, BeaconsPromotionsActivity.class);
               notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
               PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
                       new Intent[]{notifyIntent}, PendingIntent.FLAG_UPDATE_CURRENT);

            Notification notification = new NotificationCompat.Builder(this)
                    .setContentTitle("Beacon Detectado")
                    .setContentText("Nueva promocion encontrada")
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(android.R.drawable.ic_media_play)
                    .build();


            NotificationManager managerNotify = (NotificationManager)
                    getSystemService(NOTIFICATION_SERVICE);

            managerNotify.notify(101, notification);*/



        /*Log.e("weidth", ""+list.size());
        Intent intent = new Intent(BeaconReceiver.ACTION_BEACON);
        intent.putExtra(BeaconReceiver.EXTRA_MAJOR, list.get(0).getMajor());
        intent.putExtra(BeaconReceiver.EXTRA_MINOR, list.get(0).getMinor());
        sendBroadcast(intent);

        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("Beacon Detectado")
                .setContentText("MINOR = "+list.get(0).getMajor())
                .setSmallIcon(android.R.drawable.ic_media_play)
                .build();

        NotificationManager managerNotify = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        managerNotify.notify(101, notification);*/
         //Crear notificación

//   }
     }

    @Override
    public void onExitedRegion(Region region) {

    }

}
