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
import java.util.Arrays;

public class JoinLobbyClientMessage implements ClientMessageHandler {
    @Override
    public JSONObject processMessage(JSONObject jsonObject, String clientid){
        LobbyCollection lobbies = LobbyCollection.getInstance();
        if(jsonObject.get("nickname") != null || jsonObject.get("gameSessionId") != null)
        {
            boolean success = lobbies.joinLobby(new Player(clientid, jsonObject.getString("nickname")), jsonObject.getString("gameSessionId"));
            if(success)
            {
                Lobby lobby = lobbies.getLobbyByClientId(clientid);
                String[] playernames = new String[lobby.getPlayers().size()];
                for(int i = 0; i < lobby.getPlayers().size(); i++)
                {
                    playernames[i] = lobby.getPlayers().get(i).getNickname();
                }
                return JSONBuilderHandler.buildJson(new String[]{Arrays.toString(playernames), lobby.getRoomMaster().getNickname()}, BuildType.JOINLOBBY);
            }
        }
        return JSONBuilderHandler.buildJson(new String[]{"Something went wrong joining the game"}, BuildType.ERRORJSON);
    }

    @Override
    public boolean updateMessage(String clientid, JSONObject responseData) {
        LobbyCollection lobbies = LobbyCollection.getInstance();
        if(!responseData.has("error"))
        {
            Lobby lobby = lobbies.getLobbyByClientId(clientid);
            for(Player player : lobby.getPlayers())
            {
                SessionCollection collection = SessionCollection.getInstance();
                Session session = collection.getSessionByClientId(player.getClientid());
                try{
                    session.getBasicRemote().sendText(responseData.toString());
                } catch(IOException exc)
                {
                    exc.printStackTrace();
                    return false;
                }
            }
        }
        else
        {
            try{
                SessionCollection collection = SessionCollection.getInstance();
                Session session = collection.getSessionByClientId(clientid);
                session.getBasicRemote().sendText(responseData.toString());
            } catch(IOException exc)
            {
                exc.printStackTrace();
                return false;
            }
        }

        return true;
    }
}
