package com.websocketgateway.handler.clientmessage;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ClientMessageContextHandler {
    private static Map<EMessage, ClientMessageHandler> messageHandlers = new HashMap<EMessage, ClientMessageHandler>();

    static {
        messageHandlers.put(EMessage.Draw, new DrawClientMessage());
    }

    public static void processMessage(EMessage taskname, JSONObject jsonObject)
    {
        messageHandlers.get(taskname).processMessage(jsonObject);
    }
}
