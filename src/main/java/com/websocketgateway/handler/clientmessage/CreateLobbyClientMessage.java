package com.websocketgateway.handler.clientmessage;

import com.gamelogic.LobbyCollection;
import com.websocketgateway.jsonbuilder.BuildType;
import com.websocketgateway.jsonbuilder.JSONBuilderHandler;
import org.json.JSONObject;

public class CreateLobbyClientMessage implements ClientMessageHandler {

    @Override
    public ClientResponsePair processMessage(JSONObject jsonObject, String clientid){
        LobbyCollection lobbies = LobbyCollection.getInstance();
        if(lobbies.createLobby(clientid, jsonObject.getString("lobbyName")))
        {
            return new ClientResponsePair(new String[]{clientid}, JSONBuilderHandler.buildJson(new String[]{lobbies.getLobbyIDLastGameSession()}, BuildType.CREATELOBBY));
        }
        else {
            return new ClientResponsePair(new String[]{clientid}, JSONBuilderHandler.buildJson(new String[]{"You are already in a lobby"}, BuildType.ERRORJSON));
        }
    }
}
