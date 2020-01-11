package com.websocketgateway.jsonbuilder;

import org.json.JSONArray;
import org.json.JSONObject;

public class GetGamesBuilder implements JSONBuilderable {
    @Override
    public JSONObject buildJson(String[] params) {
        JSONObject object = new JSONObject();
        object.put("task", "updateGameList");

        JSONArray array = new JSONArray();
        for(String lobby : params)
        {
            String[] lobbyInfo = lobby.split(",");
            JSONObject lob = new JSONObject();
            lob.put("lobbyId", lobbyInfo[0].replace("[", "").replace("]", "").trim());
            lob.put("lobbyName", lobbyInfo[1].replace("[", "").replace("]", "").trim());
            array.put(lob);
        }
        object.put("gameLobbys", array);
        return object;
    }
}
