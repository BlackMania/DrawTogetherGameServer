package com.websocketgateway.handler.clientmessage;

import com.gamelogic.DrawCoordinates;
import com.gamelogic.Lobby;
import com.gamelogic.LobbyCollection;
import com.gamelogic.Player;
import org.json.JSONObject;

import javax.websocket.Session;
import java.io.IOException;

public class SendCoordinatesClientMessage implements ClientMessageHandler {
    @Override
    public JSONObject processMessage(JSONObject jsonObject, Session clientSession) {
        LobbyCollection lobbies = LobbyCollection.getInstance();
        Lobby lobby = lobbies.getLobbyByClientSession(clientSession);
        int prevX = jsonObject.getInt("prevX");
        int prevY = jsonObject.getInt("prevY");
        int currX = jsonObject.getInt("currX");
        int currY = jsonObject.getInt("currY");
        String color = jsonObject.getString("strokeStyle");
        int lineWidth = jsonObject.getInt("lineWidth");
        DrawCoordinates coordinate = new DrawCoordinates(currX, currY, prevX, prevY, color, lineWidth);
        lobby.getDrawing().addCoordinate(coordinate);
        return jsonObject;
    }

    @Override
    public boolean updateMessage(Session clientSession, JSONObject responseData) {
        LobbyCollection lobbies = LobbyCollection.getInstance();
        Lobby lobby  = lobbies.getLobbyByClientSession(clientSession);
        JSONObject clientResponse = responseData;
        clientResponse.put("task", "addCoordinate");
        for(Player player : lobby.getPlayers())
        {
            if(player.getClientSession() != clientSession)
            {
                try {
                    player.getClientSession().getBasicRemote().sendText(clientResponse.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return true;
    }
}
