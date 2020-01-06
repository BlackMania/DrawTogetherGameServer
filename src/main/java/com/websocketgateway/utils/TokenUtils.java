package com.websocketgateway.utils;

import org.json.JSONObject;

import java.util.Base64;

public class TokenUtils {
    private static JSONObject getTokenPayload(String token)
    {
        String[] sections = token.split("\\.");
        byte[] decodedPayload = Base64.getDecoder().decode(sections[1].getBytes());
        return new JSONObject(new String(decodedPayload));
    }

    public static String getClientId(String token)
    {
        JSONObject object = getTokenPayload(token);
        return object.getString("clientid");
    }
}
