package com.websocketgateway.handler.clientmessage;

import org.json.JSONObject;

import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;

// Collection of all client message handlers. This class will choose the correct messagehandler for the task thats been called by the client
public class ClientMessageContextHandler {
    private static Map<EMessage, ClientMessageHandler> messageHandlers = new HashMap<EMessage, ClientMessageHandler>();

    static {
        messageHandlers.put(EMessage.Draw, new DrawClientMessage());
        messageHandlers.put(EMessage.CreateGame, new CreateGameSessionMessage());
        messageHandlers.put(EMessage.JoinGame, new JoinGameSessionMessage());
        messageHandlers.put(EMessage.GetGames, new GetGamesClientMessage());
    }

    public static void processMessage(EMessage taskname, JSONObject jsonObject, Session session)
    {
        messageHandlers.get(taskname).processMessage(jsonObject, session);
        messageHandlers.get(taskname).updateMessage(session);
    }
}
