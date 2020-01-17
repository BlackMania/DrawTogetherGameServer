package com.gamelogic.jsonbuilders;

import org.json.JSONObject;

public class EndGameBuilder implements JSONBuilderable {
    @Override
    public JSONObject buildJson(String[] params) {
        JSONObject object = new JSONObject();
        object.put("task", "endGame");
        return object;
    }
}
