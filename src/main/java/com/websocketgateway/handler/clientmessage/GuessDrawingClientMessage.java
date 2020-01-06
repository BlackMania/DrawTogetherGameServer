package com.websocketgateway.handler.clientmessage;

import com.gamelogic.ChatMessage;
import com.gamelogic.Lobby;
import com.gamelogic.Player;
import com.websocketgateway.jsonbuilder.BuildType;
import com.websocketgateway.jsonbuilder.JSONBuilderHandler;
import com.websocketgateway.session.SessionCollection;
import org.json.JSONObject;

import javax.websocket.Session;
import com.gamelogic.LobbyCollection;

import java.io.IOException;
import java.sql.Timestamp;

public class GuessDrawingClientMessage implements ClientMessageHandler {
    @Override
    public JSONObject processMessage(JSONObject jsonObject, String clientid) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        LobbyCollection lobbies = LobbyCollection.getInstance();
        Lobby lobby = lobbies.getLobbyByClientId(clientid);
        JSONObject json = new JSONObject();
        if(lobby != null)
        {
            String content = jsonObject.getString("message");
            if(content.contains("script"))
            {
                return JSONBuilderHandler.buildJson(new String[]{"Are you doing something thats not allowed?"}, BuildType.ERRORJSON);
            }
            Player player = lobby.getPlayerByClientId(clientid);
            lobby.getChat().addChatMessage(new ChatMessage(timestamp, content, player));
            if(lobby.checkWordGuess(content))
            {
                player.setGuessedWord(true);
                String[] params = new String[]{"true", "guessed the word correctly!", lobby.getPlayerByClientId(clientid).getNickname() };
                return JSONBuilderHandler.buildJson(params, BuildType.GUESSDRAWING);
            }
            else {
                String[] params = new String[]{"false", content, lobby.getPlayerByClientId(clientid).getNickname() };
                return JSONBuilderHandler.buildJson(params, BuildType.GUESSDRAWING);
            }
        }
        else {
            return JSONBuilderHandler.buildJson(new String[]{"Player is not in a lobby"}, BuildType.ERRORJSON);
        }
    }

    @Override
    public boolean updateMessage(String clientid, JSONObject responseData) {
        LobbyCollection lobbies = LobbyCollection.getInstance();
        Lobby lobby = lobbies.getLobbyByClientId(clientid);
        if(!responseData.has("error"))
        {
            for(Player player : lobby.getPlayers())
            {
                SessionCollection collection = SessionCollection.getInstance();
                Session clientSession = collection.getSessionByClientId(player.getClientid());
                try {
                    clientSession.getBasicRemote().sendText(responseData.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        else {
            SessionCollection collection = SessionCollection.getInstance();
            Session clientSession = collection.getSessionByClientId(clientid);
            try {
                clientSession.getBasicRemote().sendText(responseData.toString());
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
}
