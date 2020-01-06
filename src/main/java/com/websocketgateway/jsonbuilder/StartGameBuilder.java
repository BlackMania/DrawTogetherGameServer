package com.websocketgateway.jsonbuilder;

import org.json.JSONArray;
import org.json.JSONObject;

public class StartGameBuilder implements JSONBuilderable {
    @Override
    public JSONObject buildJson(String[] params) {
        JSONObject object = new JSONObject();
        JSONArray array = new JSONArray(params[1]);

        object.put("drawer", params[0]);
        object.put("playerData", array);
        object.put("task", "startGame");
        return object;
    }
}
