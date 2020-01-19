package com.gamelogic.logic;

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

    public Lobby getLobbyByClientId(String clientid)
    {
        for(Lobby lobby : lobbies)
        {
            if(lobby.checkIfPlayerIsInHere(clientid))
                return lobby;
        }
        return null;
    }

    public String getLobbyIDLastGameSession()
    {
        return lobbies.get(lobbies.size() - 1).getLobbyId();
    }

    public boolean createLobby(String clientid, String lobbyname) {
        if(alreadyInLobby(clientid))
        {
            return false;
        }
        else {
            this.lobbies.add(new Lobby(lobbyname));
            return true;
        }
    }


    public boolean joinLobby(Player player, String gameSessionId){
        if(alreadyInLobby(player.getClientid())) return false;
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
            if(lobbyToJoin.getGame().isStarted()) return false;
            if(lobbyToJoin.isNotFull())
            {
                lobbyToJoin.addPlayer(player);
            }
            else return false;
        }
        else return false;
        return true;
    }


    public boolean leaveLobby(String clientid) {
        if(!alreadyInLobby(clientid)) return false;
        Lobby actualSession = getLobbyByClientId(clientid);
        if(actualSession != null)
        {
            actualSession.removePlayer(clientid);
            if(actualSession.getPlayers().size() == 0)
            {
                lobbies.remove(actualSession);
            }
        } else return false;
        return true;
    }

    private boolean alreadyInLobby(String clientid)
    {
        for(Lobby lobby : lobbies)
        {
            if(lobby.checkIfPlayerIsInHere(clientid))
            {
                return true;
            }
        }
        return false;
    }
}
