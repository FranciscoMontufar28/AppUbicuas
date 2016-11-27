package com.example.francisco.appubicuas.net;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Francisco on 26/11/2016.
 */

public class HttpConnection {

    public String requestByGet(String url) throws IOException{
        URL u = new URL(url);
        HttpURLConnection con =(HttpURLConnection) u.openConnection();
        con.setRequestMethod("GET");
        con.setDoInput(true);
        con.connect();
        InputStream in = con.getInputStream();

        return processInputStream(in);

    }

    private String processInputStream(InputStream in) throws IOException {
        InputStreamReader reader = new InputStreamReader(in);
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int ch;

        while((ch = reader.read())!=-1){
            buffer.write(ch);
        }

        String response = new String(buffer.toByteArray());

        return response;
    }

}
