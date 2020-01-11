package com.websocketgateway.handler.clientmessage;

import com.websocketgateway.jsonbuilder.BuildType;
import com.websocketgateway.jsonbuilder.JSONBuilderHandler;
import org.json.JSONObject;

public class GetDrawWordClientMessage implements ClientMessageHandler {
    @Override
    public ClientResponsePair processMessage(JSONObject jsonObject, String clientid) {

        return new ClientResponsePair(new String[]{clientid}, JSONBuilderHandler.buildJson(null, BuildType.GETDRAWWORDS));
    }
}
