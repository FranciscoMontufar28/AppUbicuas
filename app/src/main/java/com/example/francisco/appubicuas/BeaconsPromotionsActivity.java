package com.example.francisco.appubicuas;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.SystemRequirementsChecker;
import com.example.francisco.appubicuas.adapters.BeaconsAdapter;
import com.example.francisco.appubicuas.models.BeaconSearch;
import com.example.francisco.appubicuas.net.api.BeaconSearchApi;
import com.example.francisco.appubicuas.receivers.BeaconReceiver;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BeaconsPromotionsActivity extends AppCompatActivity implements BeaconSearchApi.onBeaconSearch, BeaconReceiver.OnBeaconListener, View.OnClickListener {

    BeaconReceiver receiver;
    List<BeaconSearch> data;
    BeaconsAdapter adapter;
    FloatingActionButton button;
    Context context;

    public static final int SECCIONUNO = 22771;
    public static final int SECCIONDOS = 22772;

    private BeaconManager beaconManager;
    private Region region;
    static int majorbeacon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacons_promotions);
        //SystemRequirementsChecker.checkWithDefaultDialogs(this);
        Log.e("ciclo", "onCreate");


        ListView lista = (ListView) findViewById(R.id.list_promotions);

        data = new ArrayList<>();
        adapter = new BeaconsAdapter(getLayoutInflater(), data);
        lista.setAdapter(adapter);

        receiver = new BeaconReceiver(this);
        IntentFilter filter = new IntentFilter(BeaconReceiver.ACTION_BEACON);
        registerReceiver(receiver, filter);

        button = (FloatingActionButton) findViewById(R.id.float_action_add);
        button.setOnClickListener(this);

        beaconManager = new BeaconManager(this);
        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {

            }
        });
        region = new Region("ranged region", UUID.fromString("f7826da6-4fa2-4e98-8024-bc5b71e0893e"), null, null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("ciclo", "onResume");
        SystemRequirementsChecker.checkWithDefaultDialogs(this);
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging(region);
            }
        });

        IntentFilter filter = new IntentFilter(BeaconReceiver.ACTION_BEACON);
        registerReceiver(receiver, filter);
    }

    /*@Override
    protected void onRestart() {
        SystemRequirementsChecker.checkWithDefaultDialogs(this);
        IntentFilter filter = new IntentFilter(BeaconReceiver.ACTION_BEACON);
        registerReceiver(receiver, filter);
        super.onRestart();

    }*/

    @Override
    protected void onPause() {
        Log.e("ciclo", "onPause");
        //beaconManager.stopRanging(region);
        SystemRequirementsChecker.checkWithDefaultDialogs(this);

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging(region);
            }
        });

        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("ciclo", "onDestroy");
        SystemRequirementsChecker.checkWithDefaultDialogs(this);
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging(region);
            }
        });
    }

    @Override
    public void onBeaconSearch(List<BeaconSearch> data) {
        for (BeaconSearch u : data){
            this.data.add(u);
        }
        Log.e("ciclo", "estoy aqui onBeaconSearch");
        adapter.notifyDataSetChanged();

        SystemRequirementsChecker.checkWithDefaultDialogs(this);

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging(region);
            }
        });
    }

    @Override
    public void onBeacon(int major, int minor) {

        Toast.makeText(this, ""+major, Toast.LENGTH_LONG).show();
        BeaconSearchApi api = new BeaconSearchApi(this);
        Log.e("ciclo", "estoy aqui onBeacon");
        if(SECCIONUNO==major) {
            api.getBeacon("" + major, this);
            SystemRequirementsChecker.checkWithDefaultDialogs(this);
            beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
                @Override
                public void onServiceReady() {
                    beaconManager.startRanging(region);
                }
            });
        }else {
        SystemRequirementsChecker.checkWithDefaultDialogs(this);
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging(region);
            }
        });

    }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.float_action_add:
                Intent intent = new Intent(this, TagListActivity.class);
                startActivity(intent);
        }
    }
}
