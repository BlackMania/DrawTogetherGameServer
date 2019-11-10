package com.websocketgateway.handler.clientmessage;

import com.gamelogic.SessionCollection;
import org.json.JSONObject;

import javax.websocket.Session;
import java.io.IOException;
import java.sql.Timestamp;

// When a creategame message is being executed by the client, this class will handle the incoming message
public class CreateGameSessionMessage implements ClientMessageHandler {

    @Override
    public boolean processMessage(JSONObject jsonObject, Session session){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SessionCollection sessionCollection = SessionCollection.getInstance();
        sessionCollection.createGameSession(session.getId());
        System.out.printf("[New Game Session] %s | New Game Session created with ID: %s | %s \n", session.getId(), sessionCollection.getLastGameSession().getSessionId(), timestamp.toString());
        return true;
    }

    @Override
    public boolean updateMessage(Session session) {
        SessionCollection sessionCollection = SessionCollection.getInstance();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("task", "JoinGame");
            jsonObject.put("gameSessionId", sessionCollection.getSessionByHostId(session.getId()).getSessionId());
            session.getBasicRemote().sendText(jsonObject.toString());
            System.out.printf("[Client Updated] %s | %s \n", jsonObject.toString(), timestamp.toString());
        } catch (Exception exc) {
            System.out.printf("[Create Game Error Updating Client] Updated client | %s | %s \n", exc.getMessage(), timestamp.toString());
            return false;
        }
        return true;
    }
}
