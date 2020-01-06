package com.gamelogic;

import javax.websocket.Session;

public class Player {
    private int points;
    private boolean drawer;
    private String nickname;
    private boolean guessedWord;
    private String clientid;

    public Player(String clientid, String nickname) {
        this.clientid = clientid;
        this.nickname = nickname;
        points = 0;
        drawer = false;
        guessedWord = false;
    }

    //region Getters and setters
    public void setPoints(int points) {
        this.points = points;
    }

    public void setDrawer(boolean drawer) {
        this.drawer = drawer;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getPoints() {
        return points;
    }

    public boolean isDrawer() {
        return drawer;
    }

    public String getNickname() {
        return nickname;
    }

    public boolean isGuessedWord() {
        return guessedWord;
    }

    public void setGuessedWord(boolean guessedWord) {
        this.guessedWord = guessedWord;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }
    //endregion


}
