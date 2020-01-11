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
    public ClientResponsePair processMessage(JSONObject jsonObject, String clientid){
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
                return new ClientResponsePair(lobby.getAllClientIds(), JSONBuilderHandler.buildJson(new String[]{Arrays.toString(playernames), lobby.getRoomMaster().getNickname()}, BuildType.JOINLOBBY));
            }
        }
        return new ClientResponsePair(new String[]{clientid}, JSONBuilderHandler.buildJson(new String[]{"Something went wrong joining the game"}, BuildType.ERRORJSON));
    }
}
