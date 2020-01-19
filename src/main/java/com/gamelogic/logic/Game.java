package com.gamelogic.logic;

import java.util.Random;
import java.util.Timer;

public class Game {
    private Drawing drawing;
    private boolean started;
    private int rounds;
    private boolean roundRunning;
    private Timer timer;
    private Lobby lobby;
    private Random randomGenerator;

    public Game(Lobby lobby) {
        started = false;
        rounds = 3;
        roundRunning = false;
        timer = new Timer();
        this.lobby = lobby;
        randomGenerator = new Random();
    }

    //region getters and setters

    public Drawing getDrawing() {
        return drawing;
    }

    public void setDrawing(Drawing drawing) {
        this.drawing = drawing;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public boolean isRoundRunning() {
        return roundRunning;
    }

    public void setRoundRunning(boolean roundRunning) {
        this.roundRunning = roundRunning;
    }


    //endregion

    public void startGame() {
        setNewPlayerToDraw();
        setStarted(true);
    }

    public void startRound(String drawing) {
        this.drawing = new Drawing(drawing);
        this.roundRunning = true;
        startTimer();
    }

    public void roundEnd() {
        stopTimer();
        resetPlayerGuessedWord();
        setNewPlayerToDraw();
        this.roundRunning = false;
        this.drawing = null;
        this.rounds--;
    }

    private void stopTimer()
    {
        timer.cancel();
        timer.purge();
    }


    private void startTimer()
    {
        timer = new Timer();
        timer.schedule(new GameTimer(lobby), 0, 1000);
    }

    public void endGame() {
        this.started = false;
        this.drawing = null;
        this.rounds = 3;
        this.roundRunning = false;
        stopTimer();
    }

    boolean checkAllPlayersGuessedWord(){
        for(Player player : lobby.getPlayers())
        {
            if(player.isDrawer()) continue;
            if(!player.getGuessedWord()) return false;
        }
        return true;
    }


    private void setNewPlayerToDraw()
    {
        for(Player p : lobby.getPlayers())
        {
            p.setDrawer(false);
        }
        int randomInt = randomGenerator.nextInt(lobby.getPlayers().size());
        Player drawer = lobby.getPlayers().get(randomInt);
        drawer.setDrawer(true);
    }


    private void resetPlayerGuessedWord() {
        for(Player player : lobby.getPlayers())
        {
            player.setGuessedWord(false);
        }
    }

    public Player getDrawingPlayer(){
        for(Player player : lobby.getPlayers())
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
        Player drawer = null;
        for(Player player : lobby.getPlayers())
        {
            if(player.getClientid().equals(clientid))
            {
                drawer = player;
            }
        }
        return drawer.isDrawer();
    }

    public boolean checkWordGuess(String content)
    {
        if(this.drawing != null)
        {
            String word = this.drawing.getWord();
            if(word != null)
            {
                return word.toLowerCase().equals(content.toLowerCase());
            }
        }
        return false;
    }
}
