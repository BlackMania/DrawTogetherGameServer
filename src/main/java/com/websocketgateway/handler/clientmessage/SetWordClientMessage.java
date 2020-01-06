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
    public JSONObject processMessage(JSONObject jsonObject, String clientid) {
        LobbyCollection lobbies = LobbyCollection.getInstance();
        Lobby lobby = lobbies.getLobbyByClientId(clientid);
        if(lobby != null)
        {
            if(lobby.checkIfClientIsDrawer(clientid))
            {
                lobby.startRound(new Drawing(jsonObject.getString("word")));
                return JSONBuilderHandler.buildJson(new String[]{jsonObject.getString("word")}, BuildType.SETWORD);
            }
            else {
                return JSONBuilderHandler.buildJson(new String[]{"Player is not the drawer"}, BuildType.ERRORJSON);
            }
        }
        else {
            return JSONBuilderHandler.buildJson(new String[]{"Player is not in a lobby"}, BuildType.ERRORJSON);
        }
    }

    @Override
    public boolean updateMessage(String clientid, JSONObject responseData) {
        LobbyCollection lobbies = LobbyCollection.getInstance();
        Lobby lobby = lobbies.getLobbyByClientId(clientid);
        if(!responseData.has("error"))
        {
            Timer timer = new Timer();
            timer.schedule(new GameTimer(lobby), 0, 1000);

            for(Player player : lobby.getPlayers())
            {
                SessionCollection collection = SessionCollection.getInstance();
                Session session = collection.getSessionByClientId(player.getClientid());
                try {
                    session.getBasicRemote().sendText(responseData.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        else {
            SessionCollection collection = SessionCollection.getInstance();
            Session session = collection.getSessionByClientId(clientid);
            try {
                session.getBasicRemote().sendText(responseData.toString());
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
}
