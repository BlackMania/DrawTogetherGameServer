package com.gamelogic.handlers;

import com.gamelogic.logic.Lobby;
import com.gamelogic.logic.LobbyCollection;
import com.gamelogic.logic.Player;
import com.gamelogic.jsonbuilders.BuildType;
import com.gamelogic.jsonbuilders.JSONBuilderHandler;
import org.json.JSONArray;
import org.json.JSONObject;

public class StartGameClientMessage implements ClientMessageHandler {
    @Override
    public ClientResponsePair processMessage(JSONObject jsonObject, String clientid) {
        LobbyCollection lobbies = LobbyCollection.getInstance();
        Lobby lobby = lobbies.getLobbyByClientId(clientid);
        if (lobby.getPlayers().size() < 2) {
            return new ClientResponsePair(new String[]{clientid}, JSONBuilderHandler.buildJson(new String[]{"2 or more players are required to start the game"}, BuildType.ERRORJSON));
        }
        if (!lobby.getRoomMaster().getClientid().equals(clientid)) {
            return new ClientResponsePair(new String[]{clientid}, JSONBuilderHandler.buildJson(new String[]{"You are not the roommaster. Only the roommaster can start the game"}, BuildType.ERRORJSON));
        }
        if (lobby.getGame().isStarted()) {
            return new ClientResponsePair(new String[]{clientid}, JSONBuilderHandler.buildJson(new String[]{"The game already started"}, BuildType.ERRORJSON));
        }
        lobby.getGame().startGame();
        String[] params = new String[2];
        params[0] = lobby.getGame().getDrawingPlayer().getNickname();

        JSONArray array = new JSONArray();
        for (Player player : lobby.getPlayers()) {
            JSONObject data = new JSONObject();
            data.put("playerName", player.getNickname());
            data.put("playerPoints", player.getPoints());
            array.put(data);
        }
        params[1] = array.toString();
        return new ClientResponsePair(lobby.getAllClientIds(), JSONBuilderHandler.buildJson(params, BuildType.STARTGAME));
    }
}
