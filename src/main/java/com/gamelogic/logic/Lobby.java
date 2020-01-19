package com.gamelogic.logic;

import java.util.*;

public class Lobby {
    private String sessionId;
    private String lobbyname;
    private List<Player> players;
    private final int MAX_PLAYERS = 6;
    private Game game;
    private Chat chat;
    private Player roomMaster;

    public Lobby(String lobbyname) {
        sessionId = UUID.randomUUID().toString();
        this.lobbyname = lobbyname;
        players = new ArrayList<>();
        chat = new Chat();
        game = new Game(this);
    }



    //region getters and setters

    public Game getGame() {
        return game;
    }

    public String getLobbyId() {
        return sessionId;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Chat getChat() {
        return chat;
    }

    public Player getPlayerByClientId(String clientid) {
        for(Player p : players)
        {
            if(p.getClientid().equals(clientid))
            {
                return p;
            }
        }
        return null;
    }

    public String getLobbyname() {
        return lobbyname;
    }

    public void setLobbyname(String lobbyname) {
        this.lobbyname = lobbyname;
    }

    public Player getRoomMaster() {
        return roomMaster;
    }

    //endregion

    public void addPlayer(Player player) {
        if(players.size() < 1)
        {
            roomMaster = player;
        }
        players.add(player);
    }

    public void removePlayer(String clientid)
    {
        Player playerToRemove = null;
        for(Player player : players)
        {
            if(player.getClientid().equals(clientid))
            {
                playerToRemove = player;
            }
        }
        if(playerToRemove != null)
        {
            players.remove(playerToRemove);
            if(players.size() != 0)
            {
                roomMaster = players.get(0);
            }
        }
    }

    public boolean isNotFull(){
        return players.size() != MAX_PLAYERS;
    }

    public boolean checkIfPlayerIsInHere(String clientid)
    {
        for(Player player : players)
        {
            if(player.getClientid().equals(clientid))
            {
                return true;
            }
        }
        return false;
    }

    public String[] getAllClientIds()
    {
        ArrayList<String> clientids = new ArrayList<>();
        for(Player player : players)
        {
            clientids.add(player.getClientid());
        }
        return clientids.toArray(new String[0]);
    }

}
