package com.websocketgateway.handler.clientmessage;

import org.json.JSONObject;

import javax.websocket.Session;

public class EndRoundClientMessage implements ClientMessageHandler {
    @Override
    public JSONObject processMessage(JSONObject jsonObject, Session clientSession) {
        return null;
    }

    @Override
    public boolean updateMessage(Session clientSession, JSONObject responseData) {
        return false;
    }
}
