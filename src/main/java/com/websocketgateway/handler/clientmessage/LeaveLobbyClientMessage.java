package com.websocketgateway.handler.clientmessage;

import com.gamelogic.Lobby;
import com.gamelogic.LobbyCollection;
import com.gamelogic.Player;
import org.json.JSONObject;

import javax.websocket.Session;
import java.io.IOException;
import java.sql.Timestamp;

public class LeaveLobbyClientMessage implements ClientMessageHandler {
    @Override
    public JSONObject processMessage(JSONObject jsonObject, Session clientSession) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        LobbyCollection lobbies = LobbyCollection.getInstance();
        JSONObject json = new JSONObject();
        Lobby lobby = lobbies.getLobbyByClientSession(clientSession);
        if (lobby != null) {
            json.put("status", "successful");
            json.put("leftLobbyId", lobby.getLobbyId());
            json.put("playerNickName", lobby.getPlayerBySession(clientSession).getNickname());
            json.put("oldRoomMaster", lobby.getRoomMaster().getNickname());
            lobbies.leaveLobby(clientSession);
            json.put("newRoomMaster", lobby.getRoomMaster().getNickname());
            System.out.printf("[Removed Client from Lobby] %s | %s | %s \n", clientSession.getId(), "Removing player from game lobby", timestamp.toString());
        } else {
            json.put("status", "error");
            json.put("reason", "You can't leave, because you are not in a game lobby");
        }
        return json;
    }

    @Override
    public boolean updateMessage(Session clientSession, JSONObject responseData) {
        LobbyCollection lobbies = LobbyCollection.getInstance();
        JSONObject clientResponse = new JSONObject();
        if (responseData.getString("status").equals("successful")) {
            Lobby lobby = lobbies.getLobbyByLobbyId(responseData.getString("leftLobbyId"));
            if (lobby != null) {
                clientResponse.put("task", "removePlayer");
                clientResponse.put("removedPlayer", responseData.getString("playerNickName"));
                clientResponse.put("newRoomMaster", responseData.getString("newRoomMaster"));
                try {
                    for (Player player : lobby.getPlayers()) {
                        if (player.getClientSession() != clientSession) {
                            player.getClientSession().getBasicRemote().sendText(clientResponse.toString());
                        }
                    }
                } catch (IOException exc) {
                    exc.printStackTrace();
                    return false;
                }
            }

        } else {
            clientResponse.put("status", "error");
            clientResponse.put("reason", responseData.getString("reason"));
            try{
                clientSession.getBasicRemote().sendText(clientResponse.toString());
            } catch (IOException exc) {
                exc.printStackTrace();
                return false;
            }
        }
        return true;
    }
}
