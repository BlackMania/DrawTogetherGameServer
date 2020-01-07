package com.websocketgateway.jsonbuilder;

import org.json.JSONObject;

public class CreateLobbyBuilder implements JSONBuilderable{

    @Override
    public JSONObject buildJson(String[] params) {
        JSONObject object = new JSONObject();
        object.put("task", "joinGame");
        object.put("gameSessionId", params[0]);

        return object;
    }
}
