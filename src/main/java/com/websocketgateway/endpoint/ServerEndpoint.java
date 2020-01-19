package com.websocketgateway.endpoint;

import com.apihandler.TokenAuthAPI;
import com.gamelogic.handlers.ClientMessageContextHandler;
import com.gamelogic.handlers.ClientResponsePair;
import com.gamelogic.handlers.EMessage;
import com.websocketgateway.session.ClientNotifyer;
import com.websocketgateway.session.SessionCollection;
import com.websocketgateway.utils.TokenUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import java.net.HttpURLConnection;
import java.sql.Timestamp;

@javax.websocket.server.ServerEndpoint(value="/lobby/{jwttoken}")
public class ServerEndpoint {

    private static final Logger logger = Logger.getLogger(ServerEndpoint.class);
    @OnOpen
    public void onOpen(Session clientSession, @PathParam("jwttoken") String token)
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.printf("[Connection opened] Session id: %s | Token used: %s | %s \n", clientSession.getId(), token, timestamp.toString());
        try {
            TokenAuthAPI tokenAuth = new TokenAuthAPI();
            int response = tokenAuth.verifyUserToken(token);
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
            logger.error(exc);
        }
    }

    @OnMessage
    public void onMessage(Session clientSession, String message) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.printf("[Message received] %s | %s | %s \n", clientSession.getId(), message, timestamp.toString());
        try {
            JSONObject json = new JSONObject(message);
            EMessage task = EMessage.valueOf(json.get("task").toString());
            ClientResponsePair response = ClientMessageContextHandler.processMessage(task, json,
                    SessionCollection.getInstance().getClientIdBySession(clientSession));
            ClientNotifyer notifyer = new ClientNotifyer(response.getUpdatableClientIDs());
            notifyer.notifyClients(response.getResponse());
        }
        catch(Exception exc)
        {
            logger.error(exc);
        }
    }

    @OnClose
    public void onClose(Session clientSession, CloseReason reason)
    {
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
        logger.error(e);
    }
}
