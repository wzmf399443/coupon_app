package com.example.user.coupon_app.urlConnector;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpHandler extends AsyncTask<Void, Void, String> {
    // This is the JSON body of the post
    private JSONObject postData;
    private String Tag = "HttpHandler";
    private Map<String, String> header;
    private String urlString;
    private String method;

    // This is a constructor that allows you to pass in the JSON body
    public HttpHandler(String urlString, String method, Map<String, String> header, Map<String, String> postData) {
        if (postData != null) {
            this.postData = new JSONObject(postData);
        }
        this.urlString = urlString;
        this.method = method;
        this.header = header;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected String doInBackground(Void... param) {
        try {
            // This is getting the url from the string we passed in
            URL url = new URL(this.urlString);

            // Create the urlConnection
            final HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestMethod(method);

            // Set authorization header
            if (!this.header.isEmpty()) {
                this.header.forEach((k, v) -> urlConnection.setRequestProperty(k, v));
            }

            // Send the post body
            if (method.equals("POST")) {
                if (this.postData != null) {
                    OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
                    writer.write(postData.toString());
                    writer.flush();
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            if (urlConnection.getResponseCode() == 200) {
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String str;
                while ((str = reader.readLine()) != null) {
                    stringBuilder.append(str);
                }
            }
            String response = stringBuilder.toString();
            Log.d(Tag, response);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
