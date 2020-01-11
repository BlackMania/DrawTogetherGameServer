package com.websocketgateway.handler.clientmessage;

import com.gamelogic.*;
import com.websocketgateway.jsonbuilder.BuildType;
import com.websocketgateway.jsonbuilder.JSONBuilderHandler;
import com.websocketgateway.session.SessionCollection;
import org.json.JSONObject;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Timer;

public class SetWordClientMessage implements ClientMessageHandler {
    @Override
    public ClientResponsePair processMessage(JSONObject jsonObject, String clientid) {
        LobbyCollection lobbies = LobbyCollection.getInstance();
        Lobby lobby = lobbies.getLobbyByClientId(clientid);
        if(lobby != null)
        {
            if(lobby.checkIfClientIsDrawer(clientid))
            {
                lobby.startRound(jsonObject.getString("word"));
                return new ClientResponsePair(lobby.getAllClientIds(), JSONBuilderHandler.buildJson(new String[]{jsonObject.getString("word")}, BuildType.SETWORD));
            }
            else {
                return new ClientResponsePair(new String[]{clientid}, JSONBuilderHandler.buildJson(new String[]{"Player is not the drawer"}, BuildType.ERRORJSON));
            }
        }
        else {
            return new ClientResponsePair(new String[]{clientid}, JSONBuilderHandler.buildJson(new String[]{"Player is not in a lobby"}, BuildType.ERRORJSON));
        }
    }
}
