package com.gamelogic.handlers;

import com.gamelogic.logic.Lobby;
import com.gamelogic.logic.LobbyCollection;
import com.gamelogic.logic.Player;
import com.gamelogic.jsonbuilders.BuildType;
import com.gamelogic.jsonbuilders.JSONBuilderHandler;
import org.json.JSONArray;
import org.json.JSONObject;


public class EndRoundClientMessage implements ClientMessageHandler {
    @Override
    public ClientResponsePair processMessage(JSONObject jsonObject, String clientid) {

        Lobby lobby = LobbyCollection.getInstance().getLobbyByClientId(clientid);
        if (lobby.getGame().getRounds() <= 0) {
            lobby.getGame().endGame();
            return new ClientResponsePair(lobby.getAllClientIds(), JSONBuilderHandler.buildJson(null, BuildType.ENDGAME));
        }

        lobby.getGame().roundEnd();
        JSONArray array = new JSONArray();

        for (Player p : lobby.getPlayers()) {
            JSONObject data = new JSONObject();
            data.put("playerName", p.getNickname());
            data.put("points", p.getPoints());
            array.put(data);
        }
        return new ClientResponsePair(lobby.getAllClientIds(), JSONBuilderHandler.buildJson(new String[]{array.toString(), lobby.getGame().getDrawingPlayer().getNickname()}, BuildType.ENDROUND));
    }
}
