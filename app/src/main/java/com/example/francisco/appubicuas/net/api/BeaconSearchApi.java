package com.example.francisco.appubicuas.net.api;

import android.content.Context;
import android.util.Log;

import com.example.francisco.appubicuas.models.BeaconSearch;
import com.example.francisco.appubicuas.net.HttpApi;
import com.example.francisco.appubicuas.net.HttpAsyncTask;
import com.example.francisco.appubicuas.net.Response;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class BeaconSearchApi extends HttpApi{

    static final int REQUEST_BEACON = 0;

    public interface onBeaconSearch{
        void onBeaconSearch(List<BeaconSearch> data);
    }

    onBeaconSearch onBeaconSearch;

    public BeaconSearchApi(Context context) {
        super(context);
    }

    public void getBeacon(String id, BeaconSearchApi.onBeaconSearch onBeaconSearch){
        this.onBeaconSearch = onBeaconSearch;

        String url = urlBaseBeacons+id;
        Log.e("URL", ""+url);
        HttpAsyncTask task = makeTask(REQUEST_BEACON, HttpAsyncTask.METHOD_GET);
        task.execute(url);
    }

    public void getBeacon(BeaconSearchApi.onBeaconSearch onBeaconSearch){
        this.onBeaconSearch = onBeaconSearch;

        String url = urlBaseBeacons+"22771";
        Log.e("URL", ""+url);
        HttpAsyncTask task = makeTask(REQUEST_BEACON, HttpAsyncTask.METHOD_GET);
        task.execute(url);
    }

    void processUsuarios(Response response){
        if(validateError(response)){
            Type type = new TypeToken<List<BeaconSearch>>(){}.getType();
            List<BeaconSearch> data = gson.fromJson(response.getMsg(),type);
            onBeaconSearch.onBeaconSearch(data);
        }
    }

    @Override
    public void onResponse(int request, Response response) {
        switch (request){
            case REQUEST_BEACON:
                processUsuarios(response);
                break;
        }
    }
}

