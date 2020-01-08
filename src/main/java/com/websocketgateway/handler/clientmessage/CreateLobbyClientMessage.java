package com.websocketgateway.handler.clientmessage;

import com.gamelogic.LobbyCollection;
import com.websocketgateway.jsonbuilder.BuildType;
import com.websocketgateway.jsonbuilder.JSONBuilderHandler;
import com.websocketgateway.session.SessionCollection;
import org.json.JSONObject;

import javax.websocket.Session;
import java.io.IOException;

public class CreateLobbyClientMessage implements ClientMessageHandler {

    @Override
    public JSONObject processMessage(JSONObject jsonObject, String clientid){
        LobbyCollection lobbies = LobbyCollection.getInstance();
        if(lobbies.createLobby(clientid, jsonObject.getString("lobbyName")))
        {
            return JSONBuilderHandler.buildJson(new String[]{lobbies.getLobbyIDLastGameSession()}, BuildType.CREATELOBBY);
        }
        else {
            return JSONBuilderHandler.buildJson(new String[]{"You are already in a lobby"}, BuildType.ERRORJSON);
        }
    }

    @Override
    public boolean updateMessage(String clientid, JSONObject responseData) {
        if(responseData != null)
        {
            SessionCollection collection = SessionCollection.getInstance();
            Session clientSession = collection.getSessionByClientId(clientid);
            try {
                clientSession.getBasicRemote().sendText(responseData.toString());
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } else return false;
    }
}
