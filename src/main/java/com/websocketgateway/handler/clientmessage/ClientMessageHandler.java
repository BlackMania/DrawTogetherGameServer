package com.websocketgateway.handler.clientmessage;

import org.json.JSONObject;

import javax.websocket.Session;

interface ClientMessageHandler
{
    JSONObject processMessage(JSONObject jsonObject, Session clientSession);

    boolean updateMessage(Session clientSession, JSONObject responseData);
}