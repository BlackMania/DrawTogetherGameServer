package com.gamelogic;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameSession {
    private String sessionId;
    private String hostId;
    private List<Player> players;
    private final int MAX_PLAYERS = 6;

    public GameSession(String hostId) {
        sessionId = UUID.randomUUID().toString();
        this.hostId = hostId;
        players = new ArrayList<Player>();
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getHostId() {
        return hostId;
    }

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

    public boolean canJoinSession(){
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

    public List<Player> getPlayers() {
        return players;
    }
}
