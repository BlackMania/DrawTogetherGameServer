package com.websocketgateway.jsonbuilder;

import org.json.JSONObject;

public interface JSONBuilderable {
    JSONObject buildJson(String[] params);
}
