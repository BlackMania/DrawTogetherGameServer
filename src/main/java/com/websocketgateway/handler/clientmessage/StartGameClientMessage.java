package com.websocketgateway.handler.clientmessage;

import com.gamelogic.Lobby;
import com.gamelogic.LobbyCollection;
import com.gamelogic.Player;
import com.websocketgateway.jsonbuilder.BuildType;
import com.websocketgateway.jsonbuilder.JSONBuilderHandler;
import com.websocketgateway.session.SessionCollection;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.websocket.Session;
import java.io.IOException;

public class StartGameClientMessage implements ClientMessageHandler {
    @Override
    public JSONObject processMessage(JSONObject jsonObject, String clientid) {
        LobbyCollection lobbies = LobbyCollection.getInstance();
        Lobby lobby = lobbies.getLobbyByClientId(clientid);
        if(lobby.getPlayers().size() < 2)
        {
            return JSONBuilderHandler.buildJson(new String[]{"2 or more players are required to start the game"}, BuildType.ERRORJSON);
        }
        if(!lobby.getRoomMaster().getClientid().equals(clientid))
        {
            return JSONBuilderHandler.buildJson(new String[]{"You are not the roommaster. Only the roommaster can start the game"}, BuildType.ERRORJSON);
        }
        if(lobby.isStarted())
        {
            return JSONBuilderHandler.buildJson(new String[]{"The game already started"}, BuildType.ERRORJSON);
        }
        else {
            lobby.setStarted(true);
            String[] params = new String[2];
            params[0] = lobby.getAndSetRandomPlayerToDraw().getNickname();

            JSONArray array = new JSONArray();
            for(Player player : lobby.getPlayers())
            {
                JSONObject data = new JSONObject();
                data.put("playerName", player.getNickname());
                data.put("playerPoints", player.getPoints());
                array.put(data);
            }
            params[1] = array.toString();
            return  JSONBuilderHandler.buildJson(params, BuildType.STARTGAME);
        }
    }

    @Override
    public boolean updateMessage(String clientid, JSONObject responseData) {
        LobbyCollection lobbies = LobbyCollection.getInstance();
        Lobby lobby = lobbies.getLobbyByClientId(clientid);
        if(!responseData.has("error"))
        {
            for(Player player : lobby.getPlayers())
            {
                SessionCollection sessionCollection = SessionCollection.getInstance();
                Session session = sessionCollection.getSessionByClientId(player.getClientid());
                try {
                    session.getBasicRemote().sendText(responseData.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        else {
            SessionCollection sessionCollection = SessionCollection.getInstance();
            Session session = sessionCollection.getSessionByClientId(clientid);
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
