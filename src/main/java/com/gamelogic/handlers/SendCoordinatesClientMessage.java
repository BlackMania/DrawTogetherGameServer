package com.gamelogic.handlers;

import com.gamelogic.logic.DrawCoordinate;
import com.gamelogic.logic.Lobby;
import com.gamelogic.logic.LobbyCollection;
import com.gamelogic.jsonbuilders.BuildType;
import com.gamelogic.jsonbuilders.JSONBuilderHandler;
import org.json.JSONObject;

public class SendCoordinatesClientMessage implements ClientMessageHandler {
    @Override
    public ClientResponsePair processMessage(JSONObject jsonObject, String clientid) {
        LobbyCollection lobbies = LobbyCollection.getInstance();
        Lobby lobby = lobbies.getLobbyByClientId(clientid);
        if(lobby.getGame().getDrawingPlayer() == lobby.getPlayerByClientId(clientid))
        {
            int prevX = jsonObject.getInt("prevX");
            int prevY = jsonObject.getInt("prevY");
            int currX = jsonObject.getInt("currX");
            int currY = jsonObject.getInt("currY");
            String color = jsonObject.getString("strokeStyle");
            int lineWidth = jsonObject.getInt("lineWidth");
            DrawCoordinate coordinate = new DrawCoordinate(currX, currY, prevX, prevY, color, lineWidth);
            lobby.getGame().getDrawing().addCoordinate(coordinate);

            String[] params = new String[6];
            params[0] = Integer.toString(prevX);
            params[1] = Integer.toString(prevY);
            params[2] = Integer.toString(currX);
            params[3] = Integer.toString(currY);
            params[4] = color;
            params[5] = Integer.toString(lineWidth);
            return new ClientResponsePair(lobby.getAllClientIds(), JSONBuilderHandler.buildJson(params, BuildType.SENDCOORDINATES));
        }
        return new ClientResponsePair(new String[]{clientid}, JSONBuilderHandler.buildJson(new String[]{"You are not the drawer!"}, BuildType.ERRORJSON));
    }
}
