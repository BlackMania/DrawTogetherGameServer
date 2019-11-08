package com.websocketgateway.handler.clientmessage;

import com.gamelogic.GameSession;
import com.gamelogic.SessionCollection;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.websocket.Session;
import java.io.IOException;

public class GetGamesClientMessage implements ClientMessageHandler {
    @Override
    public void processMessage(JSONObject jsonObject, Session session) {
        return;
    }

    @Override
    public void updateMessage(Session session) {
        SessionCollection sessionCollection = SessionCollection.getInstance();
        JSONArray jsonArray = new JSONArray();
        for(GameSession gameSession : sessionCollection.getGameSessions())
        {

            jsonArray.put(gameSession.getSessionId());
        }
        try {
            session.getBasicRemote().sendText(jsonArray.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
