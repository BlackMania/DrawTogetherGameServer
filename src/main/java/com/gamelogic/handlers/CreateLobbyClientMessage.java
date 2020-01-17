package com.gamelogic.handlers;

import com.gamelogic.logic.LobbyCollection;
import com.gamelogic.jsonbuilders.BuildType;
import com.gamelogic.jsonbuilders.JSONBuilderHandler;
import org.json.JSONObject;

public class CreateLobbyClientMessage implements ClientMessageHandler {

    @Override
    public ClientResponsePair processMessage(JSONObject jsonObject, String clientid) {
        LobbyCollection lobbies = LobbyCollection.getInstance();
        String[] params = new String[]{"You are already in a lobby"};
        BuildType type = BuildType.ERRORJSON;
        if (lobbies.createLobby(clientid, jsonObject.getString("lobbyName"))) {
            params = new String[]{lobbies.getLobbyIDLastGameSession()};
            type = BuildType.CREATELOBBY;
        }
        return new ClientResponsePair(new String[]{clientid}, JSONBuilderHandler.buildJson(params, type));
    }
}
