package com.websocketgateway.handler.clientmessage;

import com.gamelogic.ChatMessage;
import com.gamelogic.Lobby;
import com.gamelogic.Player;
import org.json.JSONObject;

import javax.websocket.Session;
import com.gamelogic.LobbyCollection;

import java.io.IOException;
import java.sql.Timestamp;

public class GuessDrawingClientMessage implements ClientMessageHandler {
    @Override
    public JSONObject processMessage(JSONObject jsonObject, Session clientSession) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        LobbyCollection lobbies = LobbyCollection.getInstance();
        Lobby lobby = lobbies.getLobbyByClientSession(clientSession);
        JSONObject json = new JSONObject();
        if(lobby != null)
        {
            String content = jsonObject.getString("message");
            if(content.contains("script"))
            {
                json.put("status", "error");
                json.put("reason", "Are you doing something thats not allowed?");
                return json;
            }
            Player player = lobby.getPlayerBySession(clientSession);
            lobby.getChat().addChatMessage(new ChatMessage(timestamp, content, player));
            if(lobby.checkWordGuess(content))
            {
                player.setGuessedWord(true);
                json.put("correct", true);
                json.put("message", "guessed the word correctly!");
                json.put("messager", lobby.getPlayerBySession(clientSession).getNickname());
            }
            else {
                json.put("correct", false);
                json.put("message", jsonObject.getString("message"));
                json.put("messager", lobby.getPlayerBySession(clientSession).getNickname());
            }
            json.put("status", "successful");
        }
        else {
            json.put("status", "error");
            json.put("reason", "Player is not in a lobby");
        }
        return json;
    }

    @Override
    public boolean updateMessage(Session clientSession, JSONObject responseData) {
        LobbyCollection lobbies = LobbyCollection.getInstance();
        Lobby lobby = lobbies.getLobbyByClientSession(clientSession);
        JSONObject clientResponse = new JSONObject();
        if(!responseData.getString("status").equals("error"))
        {
            clientResponse.put("task", "updateChat");
            clientResponse.put("message", responseData.getString("message"));
            clientResponse.put("messager", responseData.getString("messager"));
            clientResponse.put("correct", responseData.getBoolean("correct"));
            for(Player player : lobby.getPlayers())
            {
                try {
                    player.getClientSession().getBasicRemote().sendText(clientResponse.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        else {
            clientResponse.put("error", responseData.getString("reason"));
            try {
                clientSession.getBasicRemote().sendText(clientResponse.toString());
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
}
