package com.gamelogic.handlers;

import com.gamelogic.logic.Lobby;
import com.gamelogic.logic.LobbyCollection;
import com.gamelogic.jsonbuilders.BuildType;
import com.gamelogic.jsonbuilders.JSONBuilderHandler;
import org.json.JSONObject;

public class SetWordClientMessage implements ClientMessageHandler {
    @Override
    public ClientResponsePair processMessage(JSONObject jsonObject, String clientid) {
        LobbyCollection lobbies = LobbyCollection.getInstance();
        Lobby lobby = lobbies.getLobbyByClientId(clientid);
        if (lobby != null && lobby.getGame().checkIfClientIsDrawer(clientid)) {
            lobby.getGame().startRound(jsonObject.getString("word"));
            return new ClientResponsePair(lobby.getAllClientIds(), JSONBuilderHandler.buildJson(new String[]{jsonObject.getString("word")}, BuildType.SETWORD));
        }
        return new ClientResponsePair(new String[]{clientid}, JSONBuilderHandler.buildJson(new String[]{"Player is not in a lobby or you are not the drawer"}, BuildType.ERRORJSON));
    }
}
