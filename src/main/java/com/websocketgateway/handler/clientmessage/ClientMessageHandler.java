package com.websocketgateway.handler.clientmessage;

import org.json.JSONObject;

import javax.websocket.Session;

interface ClientMessageHandler
{
    boolean processMessage(JSONObject jsonObject, Session session);

    boolean updateMessage(Session session);
}