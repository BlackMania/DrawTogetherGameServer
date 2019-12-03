package com.gamelogic;

import org.json.JSONObject;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Lobby {
    private String sessionId;
    private List<Player> players;
    private final int MAX_PLAYERS = 6;
    private Chat chat;
    private Drawing drawing;
    private boolean started;
    private Player roomMaster;
    private int rounds;
    private boolean roundIsRunning;

    public Lobby() {
        sessionId = UUID.randomUUID().toString();
        players = new ArrayList<Player>();
        chat = new Chat();
        started = false;
        rounds = 3;
        roundIsRunning = false;
    }

    //region getters and setters
    public String getLobbyId() {
        return sessionId;
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
            if(p.getClientSession() == session)
            {
                return p;
            }
        }
        return null;
    }

    public int getRounds() {
        return rounds;
    }

    public void startRound(Drawing drawing) {
        this.drawing = drawing;
        this.roundIsRunning = true;
    }

    public void roundEnd() {
        this.roundIsRunning = false;
        this.drawing = null;
        this.rounds--;
    }

    public Player getRoomMaster() {
        return roomMaster;
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

    public void setDrawing(Drawing drawing) {
        this.drawing = drawing;
    }

    //endregion

    public void addPlayer(Player player) {
        if(players.size() < 1)
        {
            roomMaster = player;
        }
        players.add(player);
    }

    public void removePlayer(Session session)
    {
        Player playerToRemove = null;
        for(Player player : players)
        {
            if(player.getClientSession() == session)
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

    public boolean checkIfPlayerIsInHere(Session session)
    {
        for(Player player : players)
        {
            if(player.getClientSession().equals(session))
            {
                return true;
            }
        }
        return false;
    }

    public Player getAndSetRandomPlayerToDraw()
    {
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(this.players.size());
        Player drawer = players.get(randomInt);
        drawer.setDrawer(true);
        return drawer;
    }

    public boolean checkIfClientIsDrawer(Session clientSession)
    {
        for(Player player : players)
        {
            if(player.getClientSession() == clientSession)
            {
                return true;
            }
        }
        return false;
    }

    public void endGame() {
        this.started = false;
        this.drawing = null;
        this.rounds = 3;
        this.chat = new Chat();
        this.roundIsRunning = false;
        // Update all collected data to database
    }

    public boolean checkWordGuess(String content)
    {
        if(drawing != null)
        {
            String word = drawing.getWord();
            if(word != null)
            {
                if(word.toLowerCase().equals(content.toLowerCase()))
                {
                    return true;
                }
            }
        }
        return false;
    }
}
