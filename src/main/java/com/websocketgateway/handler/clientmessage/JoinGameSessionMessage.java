package com.websocketgateway.handler.clientmessage;

import com.gamelogic.GameSession;
import com.gamelogic.Player;
import com.gamelogic.SessionCollection;
import org.json.JSONObject;

import javax.websocket.Session;
import java.io.IOException;
import java.sql.Timestamp;

public class JoinGameSessionMessage implements ClientMessageHandler {
    @Override
    public void processMessage(JSONObject jsonObject, Session session) {
        SessionCollection sessionCollection = SessionCollection.getInstance();
        Player player = new Player(session, "Player1");
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
        try {
            GameSession gameSession = sessionCollection.getGameSessionBy(session);
            // ToDo
            // Create a state for the game to check whether the game has started, or is still waiting for players
            // If waiting for players, send players only
            // If game has started, send players, drawingcoord and other stuff on playerjoin
            for(Player player : gameSession.getPlayers())
            {
                if(session != player.getSession())
                {
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    player.getSession().getBasicRemote().sendText("A new player has joined!");
                    System.out.printf("[Clients Updated]  %s \n", timestamp.toString());
                } else
                {
                    player.getSession().getBasicRemote().sendText("You joined the gamesession");
                }
            }
        }
        catch(Exception exc)
        {
            exc.printStackTrace();
        }
    }
}
