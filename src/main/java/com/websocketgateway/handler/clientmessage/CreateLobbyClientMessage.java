package com.websocketgateway.handler.clientmessage;

import com.gamelogic.LobbyCollection;
import org.json.JSONObject;

import javax.websocket.Session;
import java.io.IOException;

public class CreateLobbyClientMessage implements ClientMessageHandler {

    @Override
    public JSONObject processMessage(JSONObject jsonObject, Session clientSession){
        LobbyCollection lobbies = LobbyCollection.getInstance();
        JSONObject json = new JSONObject();
        if(lobbies.createLobby(clientSession))
        {
            json.put("status", "successful");
        }
        else {
            json.put("status", "error");
            json.put("reason", "You are already in a lobby");
        }
        return json;
    }

    @Override
    public boolean updateMessage(Session clientSession, JSONObject responseData) {
        if(responseData != null)
        {
            LobbyCollection lobbies = LobbyCollection.getInstance();
            JSONObject clientResponse = new JSONObject();
            if(responseData.getString("status").equals("successful"))
            {
                clientResponse.put("task", "joinGame");
                clientResponse.put("gameSessionId", lobbies.getLobbyIDLastGameSession());
            }
            else
            {
                clientResponse.put("error", responseData.get("reason"));
            }
            try {
                clientSession.getBasicRemote().sendText(clientResponse.toString());
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } else return false;
    }
}
