package com.example.user.coupon_app.urlConnector;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpHandler extends AsyncTask<Void, Void, JSONObject> {
    // This is the JSON body of the post
    public static String get = "GET";
    public static String post = "POST";

    private JSONObject postData;
    private String Tag = "HttpHandler";
    private Map<String, String> header;
    private String urlString;
    private String method;

    public HttpHandler() {
    }

    // This is a constructor that allows you to pass in the JSON body
    public HttpHandler(String urlString, String method, Map<String, String> header, JSONObject post_Data) {
        this.postData = post_Data;
        this.urlString = urlString;
        this.method = method;
        this.header = header;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected JSONObject doInBackground(Void... param) {
        try {
            // This is getting the url from the string we passed in
            URL url = new URL(this.urlString);

            // Create the urlConnection
            final HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json");

            // Set authorization header
            if (this.header != null && !this.header.isEmpty()) {
                this.header.forEach((k, v) -> urlConnection.setRequestProperty(k, v));
            }

            // Send the post body
            if (method.equals("POST")) {
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                if (this.postData != null) {
                    OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
                    writer.write(postData.toString());
                    writer.flush();
                    writer.close();
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
            String str = stringBuilder.toString();
            Log.d(Tag, str);
            return new JSONObject(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
