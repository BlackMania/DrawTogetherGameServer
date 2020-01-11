package com.websocketgateway.handler.clientmessage;

import org.json.JSONObject;

interface ClientMessageHandler
{
    ClientResponsePair processMessage(JSONObject jsonObject, String clientid);
}