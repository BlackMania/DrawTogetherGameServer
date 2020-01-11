package com.websocketgateway.handler.clientmessage;

import com.gamelogic.DrawCoordinates;
import com.gamelogic.Lobby;
import com.gamelogic.LobbyCollection;
import com.gamelogic.Player;
import com.websocketgateway.jsonbuilder.BuildType;
import com.websocketgateway.jsonbuilder.JSONBuilderHandler;
import com.websocketgateway.session.SessionCollection;
import org.json.JSONObject;

import javax.websocket.Session;
import java.io.IOException;

public class SendCoordinatesClientMessage implements ClientMessageHandler {
    @Override
    public ClientResponsePair processMessage(JSONObject jsonObject, String clientid) {
        LobbyCollection lobbies = LobbyCollection.getInstance();
        Lobby lobby = lobbies.getLobbyByClientId(clientid);
        int prevX = jsonObject.getInt("prevX");
        int prevY = jsonObject.getInt("prevY");
        int currX = jsonObject.getInt("currX");
        int currY = jsonObject.getInt("currY");
        String color = jsonObject.getString("strokeStyle");
        int lineWidth = jsonObject.getInt("lineWidth");
        DrawCoordinates coordinate = new DrawCoordinates(currX, currY, prevX, prevY, color, lineWidth);
        lobby.getDrawing().addCoordinate(coordinate);

        String[] params = new String[6];
        params[0] = Integer.toString(prevX);
        params[1] = Integer.toString(prevY);
        params[2] = Integer.toString(currX);
        params[3] = Integer.toString(currY);
        params[4] = color;
        params[5] = Integer.toString(lineWidth);
        return new ClientResponsePair(lobby.getAllClientIds(), JSONBuilderHandler.buildJson(params, BuildType.SENDCOORDINATES));
    }
}
