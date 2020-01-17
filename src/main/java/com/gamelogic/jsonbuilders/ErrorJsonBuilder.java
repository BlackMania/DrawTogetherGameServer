package com.gamelogic.jsonbuilders;

import org.json.JSONObject;

public class ErrorJsonBuilder implements JSONBuilderable{

    @Override
    public JSONObject buildJson(String[] params) {
        JSONObject object = new JSONObject();
        object.put("error", params[0]);

        return object;
    }
}
