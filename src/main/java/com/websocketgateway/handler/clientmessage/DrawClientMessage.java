package com.websocketgateway.handler.clientmessage;

import com.gamelogic.SessionCollection;
import org.json.JSONObject;

import javax.websocket.Session;

// When a draw message is being executed by the client, this class will handle the incoming message
public class DrawClientMessage implements ClientMessageHandler {
    @Override
    public void processMessage(JSONObject jsonObject, Session session)
    {
    }

    @Override
    public void updateMessage(Session session) {

    }
}
