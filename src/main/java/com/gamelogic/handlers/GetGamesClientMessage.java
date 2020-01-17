package com.gamelogic.handlers;

import com.gamelogic.logic.LobbyCollection;
import com.gamelogic.jsonbuilders.BuildType;
import com.gamelogic.jsonbuilders.JSONBuilderHandler;
import org.json.JSONObject;
import java.util.Arrays;

public class GetGamesClientMessage implements ClientMessageHandler {
    @Override
    public ClientResponsePair processMessage(JSONObject jsonObject, String clientid) {
        LobbyCollection lobbies = LobbyCollection.getInstance();
        String[] params = new String[lobbies.getLobbies().size()];
        for(int i = 0; i < lobbies.getLobbies().size(); i++)
        {
            params[i] = Arrays.toString(new String[]{lobbies.getLobbies().get(i).getLobbyId(), lobbies.getLobbies().get(i).getLobbyname()});
        }
        return new ClientResponsePair(new String[]{clientid}, JSONBuilderHandler.buildJson(params, BuildType.GETGAMES));
    }
}
