package com.gamelogic;

import javax.websocket.Session;

public class Player {
    private Session session;
    private int points;
    private boolean drawer;
    private String nickname;

    public Player(Session session, String nickname) {
        this.session = session;
        this.nickname = nickname;
        points = 0;
        drawer = false;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setDrawer(boolean drawer) {
        this.drawer = drawer;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Session getSession() {
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
}
