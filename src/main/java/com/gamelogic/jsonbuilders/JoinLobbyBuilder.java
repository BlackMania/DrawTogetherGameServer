package com.gamelogic.jsonbuilders;

import org.json.JSONArray;
import org.json.JSONObject;

public class JoinLobbyBuilder implements JSONBuilderable {
    @Override
    public JSONObject buildJson(String[] params) {
        JSONObject object = new JSONObject();
        object.put("task", "addPlayers");
        JSONArray array = new JSONArray();
        String[] players = params[0].split(",");
        for(String name : players)
        {
            array.put(name.replace("[", "").replace("]", "").trim());
        }
        object.put("roomMaster", params[1]);
        object.put("players", array);
        return object;
    }
}
