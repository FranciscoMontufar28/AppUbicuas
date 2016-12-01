package com.example.francisco.appubicuas;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.francisco.appubicuas.net.api.TagSearchApi;

public class MainActivity extends AppCompatActivity {

    NfcAdapter adapter;
    TagSearchApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //NfcManager manager = (NfcManager) getSystemService(NFC_SERVICE);
        //adapter = manager.getDefaultAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        adapter.enableForegroundDispatch(this, pendingIntent, null, null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        adapter.disableForegroundDispatch(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        ///Log.i("NFCAPP", "Detectamos una etiqueta NFC");
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

        byte[] id = tag.getId();
        String ids = bytesToStringHex(id);
        Log.i("Nfcaapp", "ID:"+ids);
    }

    private String bytesToStringHex(byte[] id){
        StringBuilder builder = new StringBuilder();

        char[] hex = new char[2];
        for (int i = 0; i< id.length; i++){
            hex[0] = Character.forDigit((id[i] >>> 4) & 0x0f,16);
            hex[1] = Character.forDigit(id[i] & 0x0f,16);
            builder.append(hex);
        }

        NfcManager manager = (NfcManager) getSystemService(NFC_SERVICE);
        adapter = manager.getDefaultAdapter();

        return ""+builder;
    }
}
