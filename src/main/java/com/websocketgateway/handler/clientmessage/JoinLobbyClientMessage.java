package com.websocketgateway.handler.clientmessage;

import com.gamelogic.Lobby;
import com.gamelogic.LobbyCollection;
import com.gamelogic.Player;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.websocket.Session;
import java.io.IOException;

public class JoinLobbyClientMessage implements ClientMessageHandler {
    @Override
    public JSONObject processMessage(JSONObject jsonObject, Session clientSession){
        LobbyCollection lobbies = LobbyCollection.getInstance();
        JSONObject json = new JSONObject();
        if(jsonObject.get("nickname") != null || jsonObject.get("gameSessionId") != null)
        {
            lobbies.joinLobby(new Player(clientSession, jsonObject.getString("nickname")), jsonObject.getString("gameSessionId"));
            json.put("status", "successful");
        }
        else
        {
            json.put("status", "error");
            json.put("reason", "Something went wrong joining the game");
        }

        return json;
    }

    @Override
    public boolean updateMessage(Session clientSession, JSONObject responseData) {
        LobbyCollection lobbies = LobbyCollection.getInstance();
        JSONObject thisClientResponse = new JSONObject();
        JSONObject lobbyClientResponse = new JSONObject();
        if(responseData.getString("status").equals("successful"))
        {
            JSONArray array = new JSONArray();
            Lobby lobby = lobbies.getLobbyByClientSession(clientSession);
            for(Player player : lobby.getPlayers())
            {
                if(player.getClientSession() == clientSession)
                {
                    for(Player p : lobby.getPlayers())
                    {
                        array.put(p.getNickname());
                    }
                    thisClientResponse.put("task", "addPlayers");
                    thisClientResponse.put("players", array);
                }
                else
                {
                    lobbyClientResponse.put("task", "addNewPlayer");
                    lobbyClientResponse.put("newPlayer", lobby.getPlayerBySession(clientSession).getNickname());
                }
            }
        }
        else
        {
            thisClientResponse.put("error", responseData.getString("reason"));
        }
        Lobby lobby = lobbies.getLobbyByClientSession(clientSession);
        for(Player player : lobby.getPlayers())
        {
            try
            {
                if(player.getClientSession() == clientSession)
                {
                    clientSession.getBasicRemote().sendText(thisClientResponse.toString());
                }
                else
                {
                    player.getClientSession().getBasicRemote().sendText(lobbyClientResponse.toString());
                }
            }
            catch (IOException exc)
            {
                exc.printStackTrace();
                return false;
            }
        }
        return true;
    }
}
