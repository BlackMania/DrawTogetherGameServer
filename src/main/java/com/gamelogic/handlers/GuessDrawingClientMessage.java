package com.gamelogic.handlers;

import com.gamelogic.logic.ChatMessage;
import com.gamelogic.logic.Lobby;
import com.gamelogic.logic.Player;
import com.gamelogic.jsonbuilders.BuildType;
import com.gamelogic.jsonbuilders.JSONBuilderHandler;
import org.json.JSONObject;

import com.gamelogic.logic.LobbyCollection;

import java.sql.Timestamp;

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
            if (lobby.isRoundRunning() && lobby.getDrawingPlayer() != lobby.getPlayerByClientId(clientid)) {
                if (lobby.checkWordGuess(content)) {
                    player.setGuessedWord(true);
                    player.addPoints(jsonObject.getInt("time"));
                    params = new String[]{"true", "guessed the word correctly!", lobby.getPlayerByClientId(clientid).getNickname(), jsonObject.getString("uuid")};
                    return new ClientResponsePair(lobby.getAllClientIds(), JSONBuilderHandler.buildJson(params, BuildType.GUESSDRAWING));
                }
            }
            params = new String[]{"false", content, lobby.getPlayerByClientId(clientid).getNickname(), jsonObject.getString("uuid")};
            return new ClientResponsePair(lobby.getAllClientIds(), JSONBuilderHandler.buildJson(params, BuildType.GUESSDRAWING));
        }
        return new ClientResponsePair(new String[]{clientid}, JSONBuilderHandler.buildJson(new String[]{"Player is not in a lobby"}, BuildType.ERRORJSON));
    }
}
