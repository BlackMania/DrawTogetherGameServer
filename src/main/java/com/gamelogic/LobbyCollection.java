package com.gamelogic;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;

public class LobbyCollection {
    private static LobbyCollection lobbyCollection = null;
    private List<Lobby> lobbies;

    public LobbyCollection()
    {
        lobbies = new ArrayList<Lobby>();
    }

    public static LobbyCollection getInstance() {
        if(lobbyCollection == null)
        {
            lobbyCollection = new LobbyCollection();
        }
        return lobbyCollection;
    }

    public List<Lobby> getLobbies() {
        return lobbies;
    }

    public Lobby getLobbyByClientSession(Session session)
    {
        for(Lobby lobby : lobbies)
        {
            if(lobby.checkIfPlayerIsInHere(session))
                return lobby;
        }
        return null;
    }

    public Lobby getLobbyByLobbyId(String lobbyId)
    {

        for(Lobby lobby : lobbies)
        {
            if(lobby.getLobbyId().equals(lobbyId))
                return lobby;
        }
        return null;
    }

    public String getLobbyIDLastGameSession()
    {
        return lobbies.get(lobbies.size() - 1).getLobbyId();
    }

    public boolean createLobby(Session clientSession) {
        if(alreadyInLobby(clientSession))
        {
            return false;
        }
        else {
            this.lobbies.add(new Lobby());
            return true;
        }
    }


    public boolean joinLobby(Player player, String gameSessionId){
        if(alreadyInLobby(player.getClientSession())) return false;
        Lobby lobbyToJoin = null;
        for(Lobby lobby : lobbies)
        {
            if(lobby.getLobbyId().equals(gameSessionId))
            {
                lobbyToJoin = lobby;
            }
        }
        if(lobbyToJoin != null)
        {
            if(lobbyToJoin.isStarted()) return false;
            if(lobbyToJoin.isNotFull())
            {
                lobbyToJoin.addPlayer(player);
            }
            else return false;
        }
        else return false;
        return true;
    }

    public boolean leaveLobby(Player player, String gameSessionId) {
        if(!alreadyInLobby(player.getClientSession())) return false;
        Lobby actualSession = null;
        for(Lobby lobby : lobbies)
        {
            if(lobby.getLobbyId().equals(gameSessionId))
            {
                actualSession = lobby;
            }
        }
        if(actualSession != null)
        {
            actualSession.removePlayer(player.getClientSession());
        } else return false;
        return true;
    }

    private boolean alreadyInLobby(Session clientSession)
    {
        for(Lobby lobby : lobbies)
        {
            if(lobby.checkIfPlayerIsInHere(clientSession))
            {
                return true;
            }
        }
        return false;
    }
}
