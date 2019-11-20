package com.websocketgateway.handler.clientmessage;

import com.gamelogic.*;
import org.json.JSONObject;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Timer;

public class SetWordClientMessage implements ClientMessageHandler {
    @Override
    public JSONObject processMessage(JSONObject jsonObject, Session clientSession) {
        JSONObject json = new JSONObject();
        LobbyCollection lobbies = LobbyCollection.getInstance();
        Lobby lobby = lobbies.getLobbyByClientSession(clientSession);
        if(lobby != null)
        {
            if(lobby.checkIfClientIsDrawer(clientSession))
            {
                lobby.setRoundIsRunning(true);
                lobby.setDrawing(new Drawing(jsonObject.getString("word")));
                json.put("wordCount",jsonObject.getString("word").length());
                json.put("status", "successful");
            }
            else {
                json.put("status", "error");
                json.put("reason", "Player is not the drawer");
            }
        }
        else {
            json.put("status", "error");
            json.put("reason", "Player is not in a lobby");
        }
        return json;
    }

    @Override
    public boolean updateMessage(Session clientSession, JSONObject responseData) {
        JSONObject clientResponse = new JSONObject();
        LobbyCollection lobbies = LobbyCollection.getInstance();
        Lobby lobby = lobbies.getLobbyByClientSession(clientSession);
        if(!responseData.getString("status").equals("error"))
        {
            clientResponse.put("task", "wordSet");
            clientResponse.put("wordCount", responseData.getInt("wordCount"));
            Timer timer = new Timer();
            timer.schedule(new GameTimer(lobby), 0, 1000);

            for(Player p : lobby.getPlayers())
            {
                try {
                    p.getClientSession().getBasicRemote().sendText(clientResponse.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        else {
            clientResponse.put("error", responseData.getString("reason"));
            try {
                clientSession.getBasicRemote().sendText(clientResponse.toString());
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
}
