package com.websocketgateway.handler.clientmessage;

import com.gamelogic.Lobby;
import com.gamelogic.LobbyCollection;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.websocket.Session;
import java.io.IOException;

public class GetGamesClientMessage implements ClientMessageHandler {
    @Override
    public JSONObject processMessage(JSONObject jsonObject, Session clientSession) {
        return null;
    }

    @Override
    public boolean updateMessage(Session clientSession, JSONObject responseData) {
        LobbyCollection lobbies = LobbyCollection.getInstance();
        JSONObject clientResponse = new JSONObject();
        JSONArray array = new JSONArray();
        for(Lobby lobby : lobbies.getLobbies())
        {
            array.put(lobby.getLobbyId());
        }
        clientResponse.put("task", "updateGameList");
        clientResponse.put("gameLobbys", array);
        try
        {
            clientSession.getBasicRemote().sendText(clientResponse.toString());
        }
        catch (IOException exc)
        {
            exc.printStackTrace();
            return false;
        }
        return true;
    }
}
