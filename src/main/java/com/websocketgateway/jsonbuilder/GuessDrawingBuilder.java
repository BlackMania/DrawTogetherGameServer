package com.websocketgateway.jsonbuilder;

import org.json.JSONObject;

public class GuessDrawingBuilder implements JSONBuilderable {
    @Override
    public JSONObject buildJson(String[] params) {
        JSONObject object = new JSONObject();
        object.put("task", "updateChat");
        object.put("correct", Boolean.parseBoolean(params[0]));
        object.put("message", params[1]);
        object.put("messager",params[2]);
        return object;
    }
}
