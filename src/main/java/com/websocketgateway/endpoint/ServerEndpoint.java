package com.websocketgateway.endpoint;

import com.websocketgateway.handler.APIHandler;
import com.websocketgateway.handler.clientmessage.ClientMessageContextHandler;
import com.websocketgateway.handler.clientmessage.EMessage;
import org.json.JSONObject;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@javax.websocket.server.ServerEndpoint(value="/lobby/{jwttoken}")
public class ServerEndpoint {
    static Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());

    @OnOpen
    public void onOpen(Session session, @PathParam("jwttoken") String token)
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.printf("[Connection opened] Session id: %s | Token used: %s | %s \n", session.getId(), token, timestamp.toString());
        try {
            APIHandler handler = new APIHandler("http://localhost:9091/api/secured/tokenauth");
            int response = handler.verifyUserToken(token).getResponseCode();
            if(response != HttpURLConnection.HTTP_OK)
            {
                System.out.printf(response + "\n");
                session.close(new CloseReason(CloseReason.CloseCodes.CANNOT_ACCEPT, "Token is invalid"));
                return;
            }
        }
        catch(Exception exc)
        {
            exc.printStackTrace();
        }
        try{
            session.getBasicRemote().sendText("You successfully connected");
            sessions.add(session);
        }
        catch(IOException exc)
        {
            exc.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.printf("[Message received] %s | %s | %s \n", session.getId(), message, timestamp.toString());
        JSONObject json = null;
        try{
            json = new JSONObject(message);
        }
        catch(Exception exc)
        {
            System.out.printf("[Message error received] %s | %s | %s \n", session.getId(), exc.getMessage(), timestamp.toString());
        }
        if(json != null)
        {
            System.out.printf("[Processing Message] %s | %s | %s \n", session.getId(), message, timestamp.toString());
            EMessage task = null;
            try {
                task = EMessage.valueOf(json.get("task").toString());
                ClientMessageContextHandler.processMessage(task, json);
            }
            catch (Exception exc)
            {
                System.out.printf("[Error Processing Message] %s | %s | %s \n", session.getId(), exc.getMessage(), timestamp.toString());
            }
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason reason)
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.printf("[Connection closed] %s | %s | %s \n", session.getId(), reason.getCloseCode() + " - " + reason.getReasonPhrase(), timestamp.toString());
        sessions.remove(session);
    }

    @OnError
    public void onError(Throwable e)
    {
        e.printStackTrace();
    }
}
