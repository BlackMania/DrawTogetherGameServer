package com.gamelogic;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;

// SessionCollection is Singleton cause it has to contain all active GameSessions
public class SessionCollection {
    private static SessionCollection sessionCollection = null;
    private List<GameSession> gameSessions;

    public List<GameSession> getGameSessions() {
        return gameSessions;
    }

    public SessionCollection()
    {
        gameSessions = new ArrayList<GameSession>();
    }

    public static SessionCollection getInstance() {
        if(sessionCollection == null)
        {
            sessionCollection = new SessionCollection();
        }
        return sessionCollection;
    }

    public GameSession getSessionByHostId(String hostId) throws Exception{
        for(GameSession gameSession : gameSessions) {
            if (gameSession.getHostId().equals(hostId)) {
                return gameSession;
            }
        }
        throw new Exception("Gamesession doesn't exist or is no longer active");
    }

    public GameSession getGameSessionByClientSession(Session session) throws Exception
    {
        for(GameSession gameSession : gameSessions)
        {
            if(gameSession.checkIfPlayerIsInHere(session))
                return gameSession;
        }
        throw new Exception("Player is not in a game session");
    }

    public GameSession getLastGameSession() {
        return gameSessions.get(gameSessions.size() - 1);
    }

    public void createGameSession(String hostId) {
        gameSessions.add(new GameSession(hostId));
    }

    public void joinGameSession(Player player, String gameSessionId) throws Exception{
        if(alreadyInGame(player.getSession())) throw new Exception("You are already in a game");
        GameSession actualSession = null;
        for(GameSession gameSession : gameSessions)
        {
            if(gameSession.getSessionId().equals(gameSessionId))
            {
                actualSession = gameSession;
            }
        }
        if(actualSession != null)
        {
            if(actualSession.isNotFull())
            {
                actualSession.addPlayer(player);
            }
            else throw new Exception("Game is full");
        }
        else throw new Exception("Gamesession doesn't exist or is no longer active");
    }

    private boolean alreadyInGame(Session session)
    {
        for(GameSession gameSession : gameSessions)
        {
            if(gameSession.checkIfPlayerIsInHere(session))
            {
                return true;
            }
        }
        return false;
    }
}
