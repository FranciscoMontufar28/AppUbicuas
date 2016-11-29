package com.example.francisco.appubicuas.net.api;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.francisco.appubicuas.R;
import com.example.francisco.appubicuas.models.TagSearch;
import com.example.francisco.appubicuas.net.HttpApi;
import com.example.francisco.appubicuas.net.HttpAsyncTask;
import com.example.francisco.appubicuas.net.Response;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by jhovy on 26/11/2016.
 */

public class TagSearchApi extends HttpApi{

    static final int REQUEST_TAG = 0;

    public interface onTagSearch{
        void onTagSearch(List<TagSearch> data);
    }

    onTagSearch onTagSearch;

    public TagSearchApi(Context context) {
        super(context);
    }

    public void getTag(String id, onTagSearch onTagSearch){
        this.onTagSearch = onTagSearch;

        String url = urlBase+'"'+id+'"';
        Log.e("URL", ""+url);
        HttpAsyncTask task = makeTask(REQUEST_TAG, HttpAsyncTask.METHOD_GET);
        task.execute(url);
    }

    public void getTag(onTagSearch onTagSearch){
        this.onTagSearch = onTagSearch;

        String url = urlBase+"04f087ca9f3c80";
        Log.e("URL", ""+url);
        HttpAsyncTask task = makeTask(REQUEST_TAG, HttpAsyncTask.METHOD_GET);
        task.execute(url);
    }

    void processUsuarios(Response response){
        if(validateError(response)){
            Type type = new TypeToken<List<TagSearch>>(){}.getType();
            List<TagSearch> data = gson.fromJson(response.getMsg(),type);
            onTagSearch.onTagSearch(data);
        }
    }

    @Override
    public void onResponse(int request, Response response) {
        switch (request){
            case REQUEST_TAG:
                processUsuarios(response);
                break;
        }
    }
}
