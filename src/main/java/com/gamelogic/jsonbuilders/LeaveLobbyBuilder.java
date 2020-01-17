package com.gamelogic.jsonbuilders;

import org.json.JSONObject;

public class LeaveLobbyBuilder implements JSONBuilderable {
    @Override
    public JSONObject buildJson(String[] params) {
        JSONObject object = new JSONObject();
        object.put("task", "removePlayer");
        object.put("removedPlayer", params[0]);
        object.put("newRoomMaster", params[1]);
        object.put("leftLobbyId", params[2]);
        return object;
    }
}
