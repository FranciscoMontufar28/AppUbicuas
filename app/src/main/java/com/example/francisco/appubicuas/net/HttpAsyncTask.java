package com.example.francisco.appubicuas.net;


import android.os.AsyncTask;

import java.io.IOException;

/**
 * Created by Francisco on 26/11/2016.
 */

public class HttpAsyncTask extends AsyncTask<String, Integer, String> {



    public interface OnResponseReceived{
        void onResponse(boolean success, String json);
    }

    OnResponseReceived onResponseReceived;

    public HttpAsyncTask(OnResponseReceived onResponseReceived) {
        this.onResponseReceived = onResponseReceived;
    }

    @Override
    protected String doInBackground(String... params) {

        HttpConnection con = new HttpConnection();
        String response = null;
        try {
            response = con.requestByGet(params[0]);
        }catch (IOException e){
            e.printStackTrace();
        }
        return response;
    }
}
