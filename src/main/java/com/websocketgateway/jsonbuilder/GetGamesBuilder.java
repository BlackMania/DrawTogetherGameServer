package com.websocketgateway.jsonbuilder;

import org.json.JSONArray;
import org.json.JSONObject;

public class GetGamesBuilder implements JSONBuilderable {
    @Override
    public JSONObject buildJson(String[] params) {
        JSONObject object = new JSONObject();
        object.put("task", "updateGameList");

        JSONArray array = new JSONArray();
        for(String lobbyid : params)
        {
            array.put(lobbyid);
        }
        object.put("gameLobbys", array);
        return object;
    }
}
