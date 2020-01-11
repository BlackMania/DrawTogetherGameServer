package com.websocketgateway.handler.clientmessage;

import com.gamelogic.Lobby;
import com.gamelogic.LobbyCollection;
import com.gamelogic.Player;
import com.websocketgateway.jsonbuilder.BuildType;
import com.websocketgateway.jsonbuilder.JSONBuilderHandler;
import org.json.JSONArray;
import org.json.JSONObject;


public class EndRoundClientMessage implements ClientMessageHandler {
    @Override
    public ClientResponsePair processMessage(JSONObject jsonObject, String clientid) {

        Lobby lobby = LobbyCollection.getInstance().getLobbyByClientId(clientid);
        if (lobby.getRounds() <= 0) {
            lobby.endGame();
            return new ClientResponsePair(lobby.getAllClientIds(), JSONBuilderHandler.buildJson(null, BuildType.ENDGAME));
        }
        else {
            lobby.setNewPlayerToDraw();
            lobby.roundEnd();
            JSONArray array = new JSONArray();

            for (Player p : lobby.getPlayers()) {
                    JSONObject data = new JSONObject();
                    data.put("playerName", p.getNickname());
                    data.put("points", p.getPoints());
                    array.put(data);
            }
            return new ClientResponsePair(lobby.getAllClientIds(), JSONBuilderHandler.buildJson(new String[]{array.toString(), lobby.getDrawingPlayer().getNickname()}, BuildType.ENDROUND));
        }
    }
}
