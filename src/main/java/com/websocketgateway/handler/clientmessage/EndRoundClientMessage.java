package com.websocketgateway.handler.clientmessage;

import org.json.JSONObject;

import javax.websocket.Session;

public class EndRoundClientMessage implements ClientMessageHandler {
    @Override
    public JSONObject processMessage(JSONObject jsonObject, String clientid) {
        return null;
    }

    @Override
    public boolean updateMessage(String clientid, JSONObject responseData) {
        return false;
    }
}
