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
    public JSONObject processMessage(JSONObject jsonObject, String clientid) {
        LobbyCollection lobbies = LobbyCollection.getInstance();
        Lobby lobby = lobbies.getLobbyByClientId(clientid);
        if (lobby != null) {
            Player leftPlayer = lobby.getPlayerByClientId(clientid);
            lobbies.leaveLobby(clientid);
            return JSONBuilderHandler.buildJson(new String[]{leftPlayer.getNickname(), lobby.getRoomMaster().getNickname(), lobby.getLobbyId()}, BuildType.LEAVELOBBY);
        } else {
            return JSONBuilderHandler.buildJson(new String[]{"You can't leave, because you are not in a game lobby"}, BuildType.ERRORJSON);
        }
    }

    @Override
    public boolean updateMessage(String clientid, JSONObject responseData) {
        LobbyCollection lobbies = LobbyCollection.getInstance();
        Lobby lobby = lobbies.getLobbyByLobbyId(responseData.getString("leftLobbyId"));
        if (!responseData.has("error")) {
            if (lobby != null) {
                for (Player player : lobby.getPlayers()) {
                    SessionCollection collection = SessionCollection.getInstance();
                    Session session = collection.getSessionByClientId(player.getClientid());
                    try {
                        session.getBasicRemote().sendText(responseData.toString());
                    } catch (IOException exc) {
                        exc.printStackTrace();
                        return false;
                    }
                }
            }
        } else {
            SessionCollection collection = SessionCollection.getInstance();
            Session session = collection.getSessionByClientId(clientid);
            try{
                session.getBasicRemote().sendText(responseData.toString());
            } catch (IOException exc) {
                exc.printStackTrace();
                return false;
            }
        }
        return true;
    }
}
