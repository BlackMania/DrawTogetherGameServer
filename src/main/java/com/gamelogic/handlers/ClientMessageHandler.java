package com.gamelogic.handlers;

import org.json.JSONObject;

interface ClientMessageHandler
{
    ClientResponsePair processMessage(JSONObject jsonObject, String clientid);
}