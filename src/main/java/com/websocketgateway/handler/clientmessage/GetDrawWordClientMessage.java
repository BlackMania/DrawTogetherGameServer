package com.websocketgateway.handler.clientmessage;

import org.json.JSONObject;

import javax.websocket.Session;
import java.io.IOException;

public class GetDrawWordClientMessage implements ClientMessageHandler {
    @Override
    public JSONObject processMessage(JSONObject jsonObject, Session clientSession) {
        JSONObject json = new JSONObject();
        json.put("word1", "House");
        json.put("word2", "Key");
        json.put("word3", "Airplane");
        return json;
    }

    @Override
    public boolean updateMessage(Session clientSession, JSONObject responseData) {
        JSONObject clientResponse = new JSONObject();
        clientResponse = responseData;
        clientResponse.put("task", "chooseWord");
        try {
            clientSession.getBasicRemote().sendText(clientResponse.toString());
        } catch(IOException exc)
        {
            exc.printStackTrace();
            return false;
        }
        return true;
    }
}
