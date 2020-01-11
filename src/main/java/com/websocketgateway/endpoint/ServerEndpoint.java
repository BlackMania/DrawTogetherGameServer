package com.websocketgateway.endpoint;

import com.websocketgateway.handler.APIHandler;
import com.websocketgateway.handler.clientmessage.ClientMessageContextHandler;
import com.websocketgateway.handler.clientmessage.ClientResponsePair;
import com.websocketgateway.handler.clientmessage.EMessage;
import com.websocketgateway.session.ClientNotifyer;
import com.websocketgateway.session.SessionCollection;
import com.websocketgateway.utils.TokenUtils;
import org.json.JSONObject;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import java.net.HttpURLConnection;
import java.sql.Timestamp;

@javax.websocket.server.ServerEndpoint(value="/lobby/{jwttoken}")
public class ServerEndpoint {

    @OnOpen
    public void onOpen(Session clientSession, @PathParam("jwttoken") String token)
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.printf("[Connection opened] Session id: %s | Token used: %s | %s \n", clientSession.getId(), token, timestamp.toString());
        try {
            APIHandler handler = new APIHandler("http://localhost:9091/api/auth/tokenauth");
            int response = handler.verifyUserToken(token).getResponseCode();
            if(response != HttpURLConnection.HTTP_OK)
            {
                clientSession.close(new CloseReason(CloseReason.CloseCodes.CANNOT_ACCEPT,  String.format("Couldn't accept token. Responsecode: %s", response)));
            }
            else {
                SessionCollection collection = SessionCollection.getInstance();
                collection.addSession(clientSession, TokenUtils.getClientId(token));
                clientSession.getBasicRemote().sendText("You successfully connected");
            }
        }
        catch(Exception exc)
        {
            exc.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(Session clientSession, String message) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.printf("[Message received] %s | %s | %s \n", clientSession.getId(), message, timestamp.toString());
        JSONObject json = null;
        try{
            json = new JSONObject(message);
        }
        catch(Exception exc)
        {
            System.out.printf("[Error] %s | %s | %s \n", clientSession.getId(), exc.getMessage(), timestamp.toString());
        }
        if(json != null)
        {
            System.out.printf("[Processing Message] %s | Executing task: %s | %s \n", clientSession.getId(), json.get("task"), timestamp.toString());
            EMessage task = null;
            ClientResponsePair response = null;
            try {
                task = EMessage.valueOf(json.get("task").toString());
                SessionCollection collection = SessionCollection.getInstance();
                response = ClientMessageContextHandler.processMessage(task, json, collection.getClientIdBySession(clientSession));

            }
            catch (Exception exc)
            {
                System.out.printf("[Error] %s | %s | %s \n", clientSession.getId(), exc.getMessage(), timestamp.toString());
            }
            System.out.printf("[Message Processed] %s | Executing task: %s | %s \n", clientSession.getId(), json.get("task"), timestamp.toString());
            System.out.printf("[Updating Clients] %s | Executing task: %s | %s \n", clientSession.getId(), json.get("task"), timestamp.toString());
            if(response != null)
            {
                ClientNotifyer notifyer = new ClientNotifyer(response.getUpdatableClientIDs());
                notifyer.notifyClients(response.getResponse());
            }
        }

    }

    @OnClose
    public void onClose(Session clientSession, CloseReason reason)
    {
        // TODO
        // Needs fixing
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SessionCollection collection = SessionCollection.getInstance();
        ClientResponsePair pair = ClientMessageContextHandler.processMessage(EMessage.LeaveGame, null, collection.getClientIdBySession(clientSession));
        if(!pair.getResponse().has("error"))
        {
            ClientNotifyer notifyer = new ClientNotifyer(pair.getUpdatableClientIDs());
            notifyer.notifyClients(pair.getResponse());
        }
        collection.removeSessionBySesion(clientSession);
        System.out.printf("[Connection closed] %s | %s | %s \n", clientSession.getId(), reason.getCloseCode() + " - " + reason.getReasonPhrase(), timestamp.toString());
    }

    @OnError
    public void onError(Throwable e)
    {
        e.printStackTrace();
    }
}
