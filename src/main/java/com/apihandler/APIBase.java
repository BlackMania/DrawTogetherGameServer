package com.apihandler;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Arrays;

public abstract class APIBase {
    private static final String API_URL = "http://localhost:9091/api/";
    private static final org.apache.log4j.Logger logger = Logger.getLogger(APIBase.class);
    protected static final String sharedSecret = "M5lbXHN6Oq7JMjcVhkfwpe+uuR5SWTbR";

    private HttpURLConnection connection;

    public APIBase(String resourceUrl) {
        try {
            connection = (HttpURLConnection) new URL(API_URL + resourceUrl).openConnection();
            connection.setRequestProperty("Content-Type","application/json");
        } catch (MalformedURLException e) {
            logger.error(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void setPost()
    {
        try {
            connection.setRequestMethod("POST");
        } catch (ProtocolException e) {
            logger.error(e);
        }
    }

    protected void setGet()
    {
        try {
            connection.setRequestMethod("GET");
        } catch (ProtocolException e) {
            logger.error(e);
        }
    }

    protected void setRequestProperty(String key, String value){
        connection.setRequestProperty(key, value);
    }


    protected void setBody(JSONObject data)
    {
        try {
            OutputStream outputStream = connection.getOutputStream();
            connection.setDoOutput(true);
            byte[] dataArray = data.toString().getBytes();
            outputStream.write(dataArray);
            connection.setDoOutput(false);
            outputStream.close();
        } catch (IOException e) {
            logger.error(e);
        }
    }

    protected JSONObject getJsonBody()
    {
        JSONObject data = null;
        try {
            InputStream inputStream = connection.getInputStream();
            connection.setDoInput(true);
            byte[] dataArray = inputStream.readAllBytes();
            data = new JSONObject(Arrays.toString(dataArray));
            connection.setDoInput(false);
            inputStream.close();
        } catch (IOException e) {
            logger.error(e);
        }
        return data;
    }

    protected void makeCall()
    {
        try {
            connection.connect();
        } catch (IOException e) {
            logger.error(e);
        }
    }

    protected Integer getResponseCode()
    {
        int responseCode = 500;
        try {
            responseCode = connection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseCode;
    }
}
