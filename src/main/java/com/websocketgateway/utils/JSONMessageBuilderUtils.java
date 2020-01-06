package com.websocketgateway.utils;

import org.json.JSONObject;

public class JSONMessageBuilderUtils {
    public static JSONObject buildErrorResponse(String reason)
    {
        JSONObject object = new JSONObject();
        object.put("status", "error");
        object.put("reason", reason);
        return object;
    }

    public static JSONObject buildSuccessResponse()
    {
        JSONObject object = new JSONObject();
        object.put("status", "successful");
        return object;
    }

    public static JSONObject buildCreateLobbyResponse(String lobbyid)
    {
        JSONObject object = new JSONObject();
        object.put("task", "joinGame");
        object.put("gameSessionId", lobbyid);
        return object;
    }
}
