package com.websocketgateway.endpoint;

import com.gamelogic.Lobby;
import com.gamelogic.Player;
import com.gamelogic.LobbyCollection;
import com.websocketgateway.handler.APIHandler;
import com.websocketgateway.handler.clientmessage.ClientMessageContextHandler;
import com.websocketgateway.handler.clientmessage.EMessage;
import com.websocketgateway.session.SessionCollection;
import com.websocketgateway.utils.TokenUtils;
import org.json.JSONObject;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import java.io.IOException;
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
            }
        }
        catch(Exception exc)
        {
            exc.printStackTrace();
        }
        try{
            clientSession.getBasicRemote().sendText("You successfully connected");
        }
        catch(IOException exc)
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
            System.out.printf("[Message error received] %s | %s | %s \n", clientSession.getId(), exc.getMessage(), timestamp.toString());
        }
        if(json != null)
        {
            System.out.printf("[Processing Message] %s | Executing task: %s | %s \n", clientSession.getId(), json.get("task"), timestamp.toString());
            EMessage task = null;
            try {
                task = EMessage.valueOf(json.get("task").toString());
                SessionCollection collection = SessionCollection.getInstance();
                if(ClientMessageContextHandler.processMessage(task, json, collection.getCLientIdBySession(clientSession))){
                    System.out.printf("[Message Processed] %s | Executing task: %s | %s \n", clientSession.getId(), json.get("task"), timestamp.toString());
                } else throw new Exception("Something went wrong");
            }
            catch (Exception exc)
            {
                System.out.printf("[Error Processing Message] %s | %s | %s \n", clientSession.getId(), exc.getMessage(), timestamp.toString());
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
        collection.removeSessionBySesion(clientSession);
        System.out.printf("[Connection closed] %s | %s | %s \n", clientSession.getId(), reason.getCloseCode() + " - " + reason.getReasonPhrase(), timestamp.toString());
    }

    @OnError
    public void onError(Throwable e)
    {
        e.printStackTrace();
    }
}
