package com.gamelogic.handlers;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

// Collection of all client message handlers. This class will choose the correct messagehandler for the task thats been called by the client
public class ClientMessageContextHandler {
    private static Map<EMessage, ClientMessageHandler> messageHandlers = new HashMap<>();

    static {
        messageHandlers.put(EMessage.CreateGame, new CreateLobbyClientMessage());
        messageHandlers.put(EMessage.JoinGame, new JoinLobbyClientMessage());
        messageHandlers.put(EMessage.GetGames, new GetGamesClientMessage());
        messageHandlers.put(EMessage.LeaveGame, new LeaveLobbyClientMessage());
        messageHandlers.put(EMessage.StartGame, new StartGameClientMessage());
        messageHandlers.put(EMessage.GetDrawWords, new GetDrawWordClientMessage());
        messageHandlers.put(EMessage.SetWord, new SetWordClientMessage());
        messageHandlers.put(EMessage.SendCoordinates, new SendCoordinatesClientMessage());
        messageHandlers.put(EMessage.GuessDrawing, new GuessDrawingClientMessage());
        messageHandlers.put(EMessage.EndRound, new EndRoundClientMessage());
    }

    public static ClientResponsePair processMessage(EMessage taskname, JSONObject jsonObject, String clientid)
    {
        return messageHandlers.get(taskname).processMessage(jsonObject, clientid);
    }
}
