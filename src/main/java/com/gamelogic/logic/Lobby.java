package com.gamelogic.logic;

import java.util.*;

public class Lobby {
    private String sessionId;
    private String lobbyname;
    private List<Player> players;
    private final int MAX_PLAYERS = 6;
    private Chat chat;
    private Drawing drawing;
    private boolean started;
    private Player roomMaster;
    private int rounds;
    private boolean roundRunning;
    private Timer timer;
    private Random randomGenerator;

    public Lobby(String lobbyname) {
        sessionId = UUID.randomUUID().toString();
        this.lobbyname = lobbyname;
        players = new ArrayList<Player>();
        chat = new Chat();
        started = false;
        rounds = 3;
        roundRunning = false;
        timer = new Timer();
        randomGenerator = new Random();
    }



    //region getters and setters


    public boolean isRoundRunning() {
        return roundRunning;
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

    public int getRounds() {
        return rounds;
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

    public void startRound(String drawing) {
        this.drawing = new Drawing(drawing);
        this.roundRunning = true;
        startTimer();
    }

    public void roundEnd() {
        stopTimer();
        resetPlayerGuessedWord();
        this.roundRunning = false;
        this.drawing = null;
        this.rounds--;
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

    public void setNewPlayerToDraw()
    {
        for(Player p : players)
        {
            p.setDrawer(false);
        }
        int randomInt = randomGenerator.nextInt(this.players.size());
        Player drawer = players.get(randomInt);
        drawer.setDrawer(true);
    }

    public Player getDrawingPlayer(){
        for(Player player : players)
        {
            if(player.isDrawer())
            {
                return player;
            }
        }
        return null;
    }

    public boolean checkIfClientIsDrawer(String clientid)
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

    public void endGame() {
        this.started = false;
        this.drawing = null;
        this.rounds = 3;
        this.chat = new Chat();
        this.roundRunning = false;
        stopTimer();
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

    public String[] getAllClientIds()
    {
        ArrayList<String> clientids = new ArrayList<>();
        for(Player player : players)
        {
            clientids.add(player.getClientid());
        }
        return clientids.toArray(new String[0]);
    }

    public boolean checkAllPlayersGuessedWord(){
        for(Player player : players)
        {
            if(player.isDrawer()) continue;
            if(!player.getGuessedWord()) return false;
        }
        return true;
    }

    private void startTimer()
    {
        timer = new Timer();
        timer.schedule(new GameTimer(this), 0, 1000);
    }

    private void stopTimer()
    {
        timer.cancel();
        timer.purge();
    }

    private void resetPlayerGuessedWord() {
        for(Player player : players)
        {
            player.setGuessedWord(false);
        }
    }
}
