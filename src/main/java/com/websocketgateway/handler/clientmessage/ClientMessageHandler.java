package com.websocketgateway.handler.clientmessage;

import org.json.JSONObject;

import javax.websocket.Session;

interface ClientMessageHandler
{
    JSONObject processMessage(JSONObject jsonObject, String clientid);

    boolean updateMessage(String clientid, JSONObject responseData);
}