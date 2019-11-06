package com.gamelogic;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameSession {
    // ToDo
    // Timer implementeren
    private String sessionId;
    private String hostId;
    private List<Player> players;
    private final int MAX_PLAYERS = 6;
    private Chat chat;
    private Drawing drawing;
    private boolean started;

    public GameSession(String hostId) {
        sessionId = UUID.randomUUID().toString();
        this.hostId = hostId;
        players = new ArrayList<Player>();
        chat = new Chat();
        started = false;
    }

    //region getters and setters
    public String getSessionId() {
        return sessionId;
    }

    public String getHostId() {
        return hostId;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Chat getChat() {
        return chat;
    }

    public Player getPlayerBySession(Session session) {
        for(Player p : players)
        {
            if(p.getSession() == session)
            {
                return p;
            }
        }
        return null;
    }



    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public Drawing getDrawing() {
        return drawing;
    }

    //endregion

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Session session)
    {
        Player playerToRemove = null;
        for(Player player : players)
        {
            if(player.getSession() == session)
            {
                playerToRemove = player;
            }
        }
        if(playerToRemove != null)
        {
            players.remove(playerToRemove);
        }
    }

    public boolean isNotFull(){
        return players.size() != MAX_PLAYERS;
    }
    public boolean checkIfPlayerIsInHere(Session session)
    {
        for(Player player : players)
        {
            if(player.getSession().getId().equals(session.getId()))
                return true;
        }
        return false;
    }
}
