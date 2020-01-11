package com.websocketgateway.session;

import com.websocketgateway.handler.clientmessage.ClientResponsePair;
import org.json.JSONObject;

import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientNotifyer {
    private List<Session> clientSessions;
    private SessionCollection collection;

    public ClientNotifyer(String[] clientids) {
        clientSessions = new ArrayList<Session>();
        collection = SessionCollection.getInstance();
        for(String id : clientids)
        {
            clientSessions.add(collection.getSessionByClientId(id));
        }
    }

    public void notifyClients(JSONObject object)
    {
        for(Session session : clientSessions)
        {
            try {
                session.getBasicRemote().sendText(object.toString());
            } catch (IOException exc)
            {
                exc.printStackTrace();
            }
        }
    }

    public void notifyClients(ClientResponsePair object)
    {
        clientSessions = new ArrayList<>();
        for(String id : object.getUpdatableClientIDs())
        {
            clientSessions.add(collection.getSessionByClientId(id));
        }
        for(Session session : clientSessions)
        {
            try {
                session.getBasicRemote().sendText(object.getResponse().toString());
            } catch (IOException exc)
            {
                exc.printStackTrace();
            }
        }
    }
}
