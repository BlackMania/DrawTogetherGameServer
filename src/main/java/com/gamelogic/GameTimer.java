package com.gamelogic;

import com.websocketgateway.handler.clientmessage.ClientMessageContextHandler;
import com.websocketgateway.handler.clientmessage.EMessage;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class GameTimer extends TimerTask {

    int counter = 60;
    private Lobby lobby;
    private List<Session> clientSessions;

    public GameTimer(Lobby lobby) {
        this.lobby = lobby;
        clientSessions = new ArrayList<Session>();
        for(Player player : lobby.getPlayers())
        {
            clientSessions.add(player.getClientSession());
        }
    }

    @Override
    public void run() {
        JSONObject object = new JSONObject();
        if(counter >= 0)
        {
            object.put("task", "updateTimer");
            object.put("currentTime", counter);
            for(Session clientSession : clientSessions)
            {
                try {
                    clientSession.getBasicRemote().sendText(object.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            if(lobby.getRounds() > 0) {
                lobby.roundEnd();
                Player newDrawer = lobby.getAndSetRandomPlayerToDraw();
                for (Session clientSession : clientSessions) {
                    object = new JSONObject();
                    object.put("drawer", newDrawer.getNickname());
                    if (clientSession == newDrawer.getClientSession()) {
                        object.put("task", "chooseWord2");
                    } else {
                        object.put("task", "roundEnded");
                    }

                    try {
                        clientSession.getBasicRemote().sendText(object.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
                lobby.endGame();
                object = new JSONObject();
                object.put("task", "backToLobbyState");
                for(Session clientSession : clientSessions)
                {
                    try{
                        clientSession.getBasicRemote().sendText(object.toString());
                    } catch(IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            this.cancel();
        }
        counter--;
    }
}
