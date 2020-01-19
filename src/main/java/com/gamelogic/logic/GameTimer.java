package com.gamelogic.logic;

import com.gamelogic.handlers.ClientMessageContextHandler;
import com.gamelogic.handlers.EMessage;
import com.websocketgateway.session.ClientNotifyer;
import org.json.JSONObject;

import java.util.TimerTask;

public class GameTimer extends TimerTask {

    private int counter = 60;
    private Lobby lobby;

    public GameTimer(Lobby lobby) {
        this.lobby = lobby;
    }

    @Override
    public void run() {
        JSONObject object = new JSONObject();
        ClientNotifyer notifyer = new ClientNotifyer(lobby.getAllClientIds());
        if(lobby.getPlayers().isEmpty() || lobby == null)
        {
            this.cancel();
        }
        else if(counter < 0 || lobby.getGame().checkAllPlayersGuessedWord())
        {
            notifyer.notifyClients(ClientMessageContextHandler.processMessage(EMessage.EndRound, null, lobby.getAllClientIds()[0]));
        }
        else
        {
            object.put("task", "updateTimer");
            object.put("time", counter);
        }
        notifyer.notifyClients(object);
        counter--;
    }
}
