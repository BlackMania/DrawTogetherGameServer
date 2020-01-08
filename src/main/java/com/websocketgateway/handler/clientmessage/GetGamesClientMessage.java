package com.websocketgateway.handler.clientmessage;

import com.gamelogic.Lobby;
import com.gamelogic.LobbyCollection;
import com.websocketgateway.jsonbuilder.BuildType;
import com.websocketgateway.jsonbuilder.JSONBuilderHandler;
import com.websocketgateway.session.SessionCollection;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Arrays;

public class GetGamesClientMessage implements ClientMessageHandler {
    @Override
    public JSONObject processMessage(JSONObject jsonObject, String clientid) {
        LobbyCollection lobbies = LobbyCollection.getInstance();
        String[] params = new String[lobbies.getLobbies().size()];
        for(int i = 0; i < lobbies.getLobbies().size(); i++)
        {
            params[i] = Arrays.toString(new String[]{lobbies.getLobbies().get(i).getLobbyId(), lobbies.getLobbies().get(i).getLobbyname()});
        }
        return JSONBuilderHandler.buildJson(params, BuildType.GETGAMES);
    }

    @Override
    public boolean updateMessage(String clientid, JSONObject responseData) {
        SessionCollection collection = SessionCollection.getInstance();
        Session clientSession = collection.getSessionByClientId(clientid);
        try
        {
            clientSession.getBasicRemote().sendText(responseData.toString());
        }
        catch (IOException exc)
        {
            exc.printStackTrace();
            return false;
        }
        return true;
    }
}
