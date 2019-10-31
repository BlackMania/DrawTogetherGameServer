package com.websocketgateway.handler;

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
        connection.addRequestProperty("Authorization", "Bearer " + token);

        connection.connect();
        return connection;
    }
}
