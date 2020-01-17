package com.gamelogic.handlers;

import com.gamelogic.logic.Lobby;
import com.gamelogic.logic.LobbyCollection;
import com.gamelogic.logic.Player;
import com.gamelogic.jsonbuilders.BuildType;
import com.gamelogic.jsonbuilders.JSONBuilderHandler;
import org.json.JSONObject;

public class LeaveLobbyClientMessage implements ClientMessageHandler {
    @Override
    public ClientResponsePair processMessage(JSONObject jsonObject, String clientid) {
        LobbyCollection lobbies = LobbyCollection.getInstance();
        Lobby lobby = lobbies.getLobbyByClientId(clientid);
        if (lobby != null) {
            Player leftPlayer = lobby.getPlayerByClientId(clientid);
            lobbies.leaveLobby(clientid);
            return new ClientResponsePair(lobby.getAllClientIds(), JSONBuilderHandler.buildJson(new String[]{leftPlayer.getNickname(), lobby.getRoomMaster().getNickname(), lobby.getLobbyId()}, BuildType.LEAVELOBBY));
        }
        return new ClientResponsePair(new String[]{clientid}, JSONBuilderHandler.buildJson(new String[]{"You can't leave, because you are not in a game lobby"}, BuildType.ERRORJSON));
    }
}
