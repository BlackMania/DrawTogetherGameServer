package com.websocketgateway.handler.clientmessage;

import org.json.JSONObject;

import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;

// Collection of all client message handlers. This class will choose the correct messagehandler for the task thats been called by the client
public class ClientMessageContextHandler {
    private static Map<EMessage, ClientMessageHandler> messageHandlers = new HashMap<EMessage, ClientMessageHandler>();

    static {
        messageHandlers.put(EMessage.CreateGame, new CreateLobbyClientMessage());
        messageHandlers.put(EMessage.JoinGame, new JoinLobbyClientMessage());
        messageHandlers.put(EMessage.GetGames, new GetGamesClientMessage());
        messageHandlers.put(EMessage.LeaveGame, new LeaveLobbyClientMessage());
    }

    public static boolean processMessage(EMessage taskname, JSONObject jsonObject, Session clientSession)
    {
        JSONObject response = messageHandlers.get(taskname).processMessage(jsonObject, clientSession);
        return messageHandlers.get(taskname).updateMessage(clientSession, response);
    }
}
