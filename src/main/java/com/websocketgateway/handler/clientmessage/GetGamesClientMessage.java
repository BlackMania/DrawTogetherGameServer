package com.websocketgateway.handler.clientmessage;

import com.gamelogic.GameSession;
import com.gamelogic.SessionCollection;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.websocket.Session;
import java.io.IOException;
import java.sql.Timestamp;

public class GetGamesClientMessage implements ClientMessageHandler {
    @Override
    public boolean processMessage(JSONObject jsonObject, Session session) {
        System.out.printf("[Executing] GetGames | No task to run \n");
        return true;
    }

    @Override
    public boolean updateMessage(Session session) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SessionCollection sessionCollection = SessionCollection.getInstance();
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonObject.put("task", "updateGameList");
        for(GameSession gameSession : sessionCollection.getGameSessions())
        {
            jsonArray.put(gameSession.getSessionId());
        }
        jsonObject.put("gameLobbys", jsonArray);
        try {
            session.getBasicRemote().sendText(jsonObject.toString());
            System.out.printf("[Client Updated] Message: %s | %s \n", jsonObject.toString(), timestamp.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
