package com.gamelogic.jsonbuilders;

import org.json.JSONObject;

public class GetDrawWordBuilder implements JSONBuilderable {
    @Override
    public JSONObject buildJson(String[] params) {
        // Make call to restapi to get 3 random words

        JSONObject object = new JSONObject();
        object.put("task", "chooseWord");
        object.put("word1", "House");
        object.put("word2", "Key");
        object.put("word3", "Airplane");
        return object;
    }
}
