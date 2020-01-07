package com.websocketgateway.session;

import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;

public class SessionCollection {
    private Map<String, Session> sessions = null;
    private static SessionCollection sessionCollection;

    public SessionCollection() {
        sessions = new HashMap<String, Session>();
    }

    public static SessionCollection getInstance() {
        if(sessionCollection == null)
        {
            sessionCollection = new SessionCollection();
        }
        return sessionCollection;
    }

    public Session getSessionByClientId(String clientid)
    {
        return sessions.get(clientid);
    }

    public String getCLientIdBySession(Session session)
    {
        for(Map.Entry<String, Session> field : sessions.entrySet())
        {
            if(field.getValue() == session)
            {
                return field.getKey();
            }
        }
        return null;
    }

    public void removeSessionByClientId(String clientid)
    {
        sessions.remove(clientid);
    }

    public void removeSessionBySesion(Session session)
    {
        for(Map.Entry<String, Session> field : sessions.entrySet())
        {
            if(field.getValue() == session)
            {
                sessions.remove(field.getKey());
            }
        }
    }

    public void addSession(Session session, String clientid)
    {
        sessions.put(clientid, session);
    }

    public int getCount()
    {
        return sessions.size();
    }
}
