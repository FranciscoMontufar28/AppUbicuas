package com.example.francisco.appubicuas.net.api;

import android.content.Context;

import com.example.francisco.appubicuas.MainActivity;
import com.example.francisco.appubicuas.R;
import com.example.francisco.appubicuas.models.TagSearch;
import com.example.francisco.appubicuas.net.HttpAsyncTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by jhovy on 26/11/2016.
 */

public class TagSearchApi implements HttpAsyncTask.OnResponseReceived {

    OnTagSearch onTagSearch;

    public interface OnTagSearch{
        void onTagSearch(List<TagSearch> data);
    }

    public TagSearchApi(OnTagSearch onTagSearch) {
        this.onTagSearch = onTagSearch;
    }

    public void getDescriptionsSearch(Context context, String id){

        //String n = name.replaceAll(" ", "+");
        String url = String.format(context.getString(R.string.url_db_tags), id);
        HttpAsyncTask task = new HttpAsyncTask(this);
        task.execute(url);
    }

    @Override
    public void onResponse(boolean success, String json) {

        Type type = new TypeToken<List<TagSearch>>(){}.getType();
        Gson gson = new Gson();
        List<TagSearch> data = gson.fromJson(json, type);
        onTagSearch.onTagSearch(data);

        /*try {
            JSONObject obj = new JSONObject(json);
            Type type = new TypeToken<List<TagSearch>>(){}.getType();
            List<TagSearch> data = gson.fromJson(obj.get("Search").toString(), type);
            onTagSearch.onTagSearch(data);
        }catch (JSONException e){
            e.printStackTrace();
        }*/

    }
}
