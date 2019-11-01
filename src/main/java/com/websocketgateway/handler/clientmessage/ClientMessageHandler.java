package com.websocketgateway.handler.clientmessage;

import org.json.JSONObject;

import javax.websocket.Session;

interface ClientMessageHandler
{
    void processMessage(JSONObject jsonObject, Session session);

    void updateMessage(Session session);
}