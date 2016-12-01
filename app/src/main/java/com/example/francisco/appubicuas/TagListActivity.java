package com.example.francisco.appubicuas;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.francisco.appubicuas.adapters.TagsAdapter;
import com.example.francisco.appubicuas.models.TagSearch;
import com.example.francisco.appubicuas.net.api.TagSearchApi;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

public class TagListActivity extends AppCompatActivity implements TagSearchApi.onTagSearch {

    List<TagSearch> data;
    TagsAdapter adapter;
    NfcAdapter nfcadapter;
    int price_string=0;
    int price =0;
    TextView precio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_list);

        NfcManager manager = (NfcManager) getSystemService(NFC_SERVICE);
        nfcadapter = manager.getDefaultAdapter();

        //RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        ListView list = (ListView) findViewById(R.id.list_tags);

        data = new ArrayList<>();
        adapter = new TagsAdapter(getLayoutInflater(), data);
        list.setAdapter(adapter);

        precio= (TextView) findViewById(R.id.price_total_list);
        //TagSearchApi api = new TagSearchApi(this);
        //api.getTag(this);



    }

    @Override
    public void onTagSearch(List<TagSearch> data) {
        for (TagSearch u : data){
            this.data.add(u);
            price= Integer.parseInt(u.getPrecio());

        }
        price_string=price+price_string;
        precio.setText("Precio total: "+price_string);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this, TagListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        nfcadapter.enableForegroundDispatch(this, pendingIntent, null, null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        nfcadapter.disableForegroundDispatch(this);
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
        TagSearchApi api = new TagSearchApi(this);
        api.getTag(""+builder, this);
        return ""+builder;

    }
}
