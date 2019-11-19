package com.websocketgateway.handler.clientmessage;

import com.gamelogic.Lobby;
import com.gamelogic.LobbyCollection;
import com.gamelogic.Player;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.websocket.Session;
import java.io.IOException;

public class StartGameClientMessage implements ClientMessageHandler {
    @Override
    public JSONObject processMessage(JSONObject jsonObject, Session clientSession) {
        LobbyCollection lobbies = LobbyCollection.getInstance();
        JSONObject json = new JSONObject();
        Lobby lobby = lobbies.getLobbyByClientSession(clientSession);
        if(lobby.getPlayers().size() < 2)
        {
            json.put("status", "error");
            json.put("reason", "2 or more players are required to start the game");
            return json;
        }
        if(lobby.getRoomMaster().getClientSession() != clientSession)
        {
            json.put("status", "error");
            json.put("reason", "You are not the roommaster. Only the roommaster can start the game");
            return json;
        }
        if(lobby.isStarted())
        {
            json.put("status", "error");
            json.put("reason", "The game already started");
            return json;
        }
        else {
            lobby.setStarted(true);
            json.put("drawer", lobby.getAndSetRandomPlayerToDraw().getNickname());
            JSONArray array = new JSONArray();
            for(Player player : lobby.getPlayers())
            {
                JSONObject data = new JSONObject();
                data.put("playerName", player.getNickname());
                data.put("playerPoints", player.getPoints());
                array.put(data);
            }
            json.put("playerData", array);
            json.put("status", "successful");
        }
        return json;
    }

    @Override
    public boolean updateMessage(Session clientSession, JSONObject responseData) {
        LobbyCollection lobbies = LobbyCollection.getInstance();
        JSONObject clientResponse = responseData;
        clientResponse.put("task", "startGame");
        Lobby lobby = lobbies.getLobbyByClientSession(clientSession);
        if(!clientResponse.getString("status").equals("error"))
        {
            clientResponse.remove("status");
            for(Player player : lobby.getPlayers())
            {
                try {
                    player.getClientSession().getBasicRemote().sendText(clientResponse.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        else {
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
