package com.example.user.coupon_app.urlConnector;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpHandler extends AsyncTask<String, String, String> {
    // This is the JSON body of the post
    private JSONObject postData;
    private String Tag = "HttpHandler";
    private Map<String, String> header;

    public HttpHandler() {
    }

    // This is a constructor that allows you to pass in the JSON body
    public HttpHandler(Map<String, String> postData) {
        if (postData != null) {
            this.postData = new JSONObject(postData);
        }
    }

    public void SetHeader(Map<String, String> map) {
        this.header = map;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected String doInBackground(String... strings) {
        String url_to_get = strings[0];
        String method = strings[1];
        try {
            // This is getting the url from the string we passed in
            URL url = new URL(url_to_get);

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

            int statusCode = urlConnection.getResponseCode();

            if (statusCode == 200) {

                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());

                String response;
                ByteArrayOutputStream result = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) != -1) {
                    result.write(buffer, 0, length);
                }

                response = result.toString();
                Log.d(Tag, response);
                // From here you can convert the string to JSON with whatever JSON parser you like to use
                // After converting the string to JSON, I call my custom callback. You can follow this process too, or you can implement the onPostExecute(Result) method
                return response;
            } else {
                // Status code is not 200
                // Do something to handle the error
                return "";
            }

        } catch (Exception e) {
            Log.d(Tag, e.getLocalizedMessage());
            return "";
        }
    }
}
