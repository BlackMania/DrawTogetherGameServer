package com.gamelogic;

import javax.websocket.Session;

public class Player {
    private Session session;
    private int points;
    private boolean drawer;
    private String nickname;
    private boolean guessedWord;

    public Player(Session session, String nickname) {
        this.session = session;
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

    public Session getClientSession() {
        return session;
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

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public boolean isGuessedWord() {
        return guessedWord;
    }

    public void setGuessedWord(boolean guessedWord) {
        this.guessedWord = guessedWord;
    }

    //endregion


}
