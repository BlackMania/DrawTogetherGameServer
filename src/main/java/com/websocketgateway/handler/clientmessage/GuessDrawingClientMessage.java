package com.websocketgateway.handler.clientmessage;

import com.gamelogic.ChatMessage;
import com.gamelogic.Lobby;
import com.gamelogic.Player;
import com.websocketgateway.jsonbuilder.BuildType;
import com.websocketgateway.jsonbuilder.JSONBuilderHandler;
import com.websocketgateway.session.SessionCollection;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.websocket.Session;

import com.gamelogic.LobbyCollection;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class GuessDrawingClientMessage implements ClientMessageHandler {
    @Override
    public ClientResponsePair processMessage(JSONObject jsonObject, String clientid) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        LobbyCollection lobbies = LobbyCollection.getInstance();
        Lobby lobby = lobbies.getLobbyByClientId(clientid);
        if (lobby != null) {
            String content = jsonObject.getString("message");
            Player player = lobby.getPlayerByClientId(clientid);
            lobby.getChat().addChatMessage(new ChatMessage(timestamp, content, player));
            String[] params;
            if (lobby.getPlayerByClientId(clientid).getGuessedWord()) {
                params = new String[]{"false", content, lobby.getPlayerByClientId(clientid).getNickname(), jsonObject.getString("uuid")};
                return new ClientResponsePair(lobby.getAllClientIds(), JSONBuilderHandler.buildJson(params, BuildType.GUESSDRAWING));
            }
            if (lobby.isRoundRunning()) {
                if (lobby.getDrawingPlayer() == lobby.getPlayerByClientId(clientid)) {
                    params = new String[]{"false", content, lobby.getPlayerByClientId(clientid).getNickname(), jsonObject.getString("uuid")};
                    return new ClientResponsePair(lobby.getAllClientIds(), JSONBuilderHandler.buildJson(params, BuildType.GUESSDRAWING));
                }
                else if (lobby.checkWordGuess(content)) {
                    player.setGuessedWord(true);
                    player.addPoints(jsonObject.getInt("time"));
                    params = new String[]{"true", "guessed the word correctly!", lobby.getPlayerByClientId(clientid).getNickname(), jsonObject.getString("uuid")};
                    return new ClientResponsePair(lobby.getAllClientIds(), JSONBuilderHandler.buildJson(params, BuildType.GUESSDRAWING));
                } else {
                    params = new String[]{"false", content, lobby.getPlayerByClientId(clientid).getNickname(), jsonObject.getString("uuid")};
                    return new ClientResponsePair(lobby.getAllClientIds(), JSONBuilderHandler.buildJson(params, BuildType.GUESSDRAWING));
                }
            } else {
                params = new String[]{"false", content, lobby.getPlayerByClientId(clientid).getNickname(), jsonObject.getString("uuid")};
                return new ClientResponsePair(lobby.getAllClientIds(), JSONBuilderHandler.buildJson(params, BuildType.GUESSDRAWING));
            }
        } else {
            return new ClientResponsePair(new String[]{clientid}, JSONBuilderHandler.buildJson(new String[]{"Player is not in a lobby"}, BuildType.ERRORJSON));
        }
    }
}
