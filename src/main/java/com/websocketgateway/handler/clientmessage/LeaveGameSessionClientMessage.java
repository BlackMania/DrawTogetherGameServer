package com.websocketgateway.handler.clientmessage;

import com.gamelogic.GameSession;
import com.gamelogic.Player;
import com.gamelogic.SessionCollection;
import org.json.JSONObject;

import javax.websocket.Session;
import java.sql.Timestamp;

public class LeaveGameSessionClientMessage implements ClientMessageHandler {
    @Override
    public boolean processMessage(JSONObject jsonObject, Session session) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SessionCollection sessionCollection = SessionCollection.getInstance();
        GameSession gameSession = sessionCollection.getGameSessionByClientSession(session);
        if(gameSession != null)
        {
            System.out.printf("[Removing player] %s | %s | %s \n", session.getId(), gameSession.getSessionId(), timestamp.toString());
            return true;
        } else return false;
    }

    @Override
    public boolean updateMessage(Session session) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SessionCollection sessionCollection = SessionCollection.getInstance();
        GameSession gameSession = sessionCollection.getGameSessionByClientSession(session);
        JSONObject object = new JSONObject();
        object.put("task", "removePlayer");
        object.put("removedPlayer", gameSession.getPlayerBySession(session).getNickname());
        gameSession.removePlayer(session);
        System.out.printf("[Removed player] %s | %s | %s \n", session.getId(), gameSession.getSessionId(), timestamp.toString());
        try {
            if(gameSession.getPlayers().size() != 0)
            {
                for(Player player : gameSession.getPlayers())
                {
                    player.getSession().getBasicRemote().sendText(object.toString());
                }
            }
        }
        catch(Exception exc)
        {
            System.out.printf("[Error updating clients on close] %s | %s | %s \n", session.getId(), exc.getMessage(), timestamp.toString());
            return false;
        }
        return true;
    }
}
