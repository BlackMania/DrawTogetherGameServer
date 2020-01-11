package com.websocketgateway.handler.clientmessage;

import com.gamelogic.Lobby;
import com.gamelogic.LobbyCollection;
import com.gamelogic.Player;
import com.websocketgateway.jsonbuilder.BuildType;
import com.websocketgateway.jsonbuilder.JSONBuilderHandler;
import com.websocketgateway.session.SessionCollection;
import org.json.JSONObject;

import javax.websocket.Session;
import java.io.IOException;
import java.sql.Timestamp;

public class LeaveLobbyClientMessage implements ClientMessageHandler {
    @Override
    public ClientResponsePair processMessage(JSONObject jsonObject, String clientid) {
        LobbyCollection lobbies = LobbyCollection.getInstance();
        Lobby lobby = lobbies.getLobbyByClientId(clientid);
        if (lobby != null) {
            Player leftPlayer = lobby.getPlayerByClientId(clientid);
            lobbies.leaveLobby(clientid);
            return new ClientResponsePair(lobby.getAllClientIds(), JSONBuilderHandler.buildJson(new String[]{leftPlayer.getNickname(), lobby.getRoomMaster().getNickname(), lobby.getLobbyId()}, BuildType.LEAVELOBBY));
        } else {
            return new ClientResponsePair(new String[]{clientid}, JSONBuilderHandler.buildJson(new String[]{"You can't leave, because you are not in a game lobby"}, BuildType.ERRORJSON));
        }
    }
}
