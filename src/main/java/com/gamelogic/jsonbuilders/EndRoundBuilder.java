package com.gamelogic.jsonbuilders;

import org.json.JSONArray;
import org.json.JSONObject;

public class EndRoundBuilder implements JSONBuilderable {
    @Override
    public JSONObject buildJson(String[] params) {
        // Make call to restapi to get 3 random words

        JSONArray array = new JSONArray(params[0]);
        JSONObject object = new JSONObject();
        object.put("task", "endRound");
        object.put("data", array);
        object.put("newDrawer", params[1]);
        return object;
    }
}
