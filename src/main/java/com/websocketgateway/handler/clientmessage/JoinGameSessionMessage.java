package com.websocketgateway.handler.clientmessage;

import com.gamelogic.GameSession;
import com.gamelogic.Player;
import com.gamelogic.SessionCollection;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.websocket.Session;
import java.io.IOException;
import java.sql.Timestamp;

public class JoinGameSessionMessage implements ClientMessageHandler {
    @Override
    public void processMessage(JSONObject jsonObject, Session session) {
        SessionCollection sessionCollection = SessionCollection.getInstance();
        Player player = new Player(session, jsonObject.getString("nickname"));
        try {
            sessionCollection.joinGameSession(player, jsonObject.get("gameSessionId").toString());
            try{
                session.getBasicRemote().sendText("You joined the game");
            }
            catch(IOException exc)
            {
                exc.printStackTrace();
            }
        }
        catch(Exception exc)
        {
            try{
                session.getBasicRemote().sendText(exc.getMessage());
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void updateMessage(Session session) {
        SessionCollection sessionCollection = SessionCollection.getInstance();

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try {
            GameSession gameSession = sessionCollection.getGameSessionByClientSession(session);
            for(Player player : gameSession.getPlayers())
            {
                JSONObject jsonObject;
                if(gameSession.isStarted())
                {
                    // ToDo
                    // fix this with task: "updateGameField"
                    // when game is started already, send data to the new joined player and send the new joined player to the others
                }
                else
                {
                    if(session == player.getSession())
                    {
                        jsonObject = new JSONObject();
                        JSONArray array = new JSONArray();
                        for(Player p : gameSession.getPlayers())
                        {
                                array.put(p.getNickname());
                        }
                        jsonObject.put("players", array);
                        jsonObject.put("task", "addPlayers");
                        player.getSession().getBasicRemote().sendText(jsonObject.toString());
                        System.out.printf("[Clients Updated from Gamesession: " + gameSession.getSessionId() + "] Message: %s | %s \n", jsonObject.toString(), timestamp.toString());
                    }
                    else {
                        jsonObject = new JSONObject();
                        jsonObject.put("newPlayer", gameSession.getPlayerBySession(session).getNickname());
                        jsonObject.put("task", "addNewPlayer");
                        player.getSession().getBasicRemote().sendText(jsonObject.toString());
                        System.out.printf("[Clients Updated from Gamesession: " + gameSession.getSessionId() + "] Message: %s | %s \n", jsonObject.toString(), timestamp.toString());
                    }
                }
            }
        }
        catch(Exception exc)
        {
            exc.printStackTrace();
        }
    }
}
