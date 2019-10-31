package com.websocketgateway.handler.clientmessage;

import org.json.JSONObject;

interface ClientMessageHandler
{
    void processMessage(JSONObject jsonObject);
}