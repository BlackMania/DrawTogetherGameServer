package com.gamelogic.jsonbuilders;

import org.json.JSONObject;

public interface JSONBuilderable {
    JSONObject buildJson(String[] params);
}
