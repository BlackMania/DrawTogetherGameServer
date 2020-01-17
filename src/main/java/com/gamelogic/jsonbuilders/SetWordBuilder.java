package com.gamelogic.jsonbuilders;

import org.json.JSONObject;

public class SetWordBuilder implements JSONBuilderable {
    @Override
    public JSONObject buildJson(String[] params) {
        JSONObject object = new JSONObject();
        object.put("task", "wordSet");
        object.put("wordCount", params[0].length());

        return object;
    }
}
