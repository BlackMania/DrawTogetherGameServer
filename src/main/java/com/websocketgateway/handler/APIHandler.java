package com.websocketgateway.handler;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class APIHandler {
    private String apiURL;

    public APIHandler(String apiURL) {
        this.apiURL = apiURL;
    }

    public HttpURLConnection verifyUserToken(String token) throws Exception{
        URL url = new URL(apiURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        connection.addRequestProperty("Authorization", "Bearer " + token);
        connection.connect();
        return connection;
    }
}
