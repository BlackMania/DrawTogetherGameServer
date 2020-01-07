package com.websocketgateway.handler.clientmessage;

import com.websocketgateway.jsonbuilder.BuildType;
import com.websocketgateway.jsonbuilder.JSONBuilderHandler;
import com.websocketgateway.session.SessionCollection;
import org.json.JSONObject;

import javax.websocket.Session;
import java.io.IOException;

public class GetDrawWordClientMessage implements ClientMessageHandler {
    @Override
    public JSONObject processMessage(JSONObject jsonObject, String clientid) {
        return JSONBuilderHandler.buildJson(null, BuildType.GETDRAWWORDS);
    }

    @Override
    public boolean updateMessage(String clientid, JSONObject responseData) {

        SessionCollection collection = SessionCollection.getInstance();
        Session clientSession = collection.getSessionByClientId(clientid);
        try {
            clientSession.getBasicRemote().sendText(responseData.toString());
        } catch(IOException exc)
        {
            exc.printStackTrace();
            return false;
        }
        return true;
    }
}
