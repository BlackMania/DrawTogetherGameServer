package com.gamelogic.handlers;

import com.gamelogic.logic.Lobby;
import com.gamelogic.logic.LobbyCollection;
import com.gamelogic.logic.Player;
import com.gamelogic.jsonbuilders.BuildType;
import com.gamelogic.jsonbuilders.JSONBuilderHandler;
import org.json.JSONObject;

import java.util.Arrays;

public class JoinLobbyClientMessage implements ClientMessageHandler {
    @Override
    public ClientResponsePair processMessage(JSONObject jsonObject, String clientid) {
        LobbyCollection lobbies = LobbyCollection.getInstance();
        boolean success = lobbies.joinLobby(new Player(clientid, jsonObject.getString("nickname")), jsonObject.getString("gameSessionId"));
        if (success) {
            Lobby lobby = lobbies.getLobbyByClientId(clientid);
            String[] playernames = new String[lobby.getPlayers().size()];
            for (int i = 0; i < lobby.getPlayers().size(); i++) {
                playernames[i] = lobby.getPlayers().get(i).getNickname();
            }
            return new ClientResponsePair(lobby.getAllClientIds(), JSONBuilderHandler.buildJson(new String[]{Arrays.toString(playernames), lobby.getRoomMaster().getNickname()}, BuildType.JOINLOBBY));
        }
        return new ClientResponsePair(new String[]{clientid}, JSONBuilderHandler.buildJson(new String[]{"Something went wrong joining the game"}, BuildType.ERRORJSON));
    }
}
