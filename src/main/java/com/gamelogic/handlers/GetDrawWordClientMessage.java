package com.gamelogic.handlers;

import com.gamelogic.jsonbuilders.BuildType;
import com.gamelogic.jsonbuilders.JSONBuilderHandler;
import org.json.JSONObject;

public class GetDrawWordClientMessage implements ClientMessageHandler {
    @Override
    public ClientResponsePair processMessage(JSONObject jsonObject, String clientid) {

        return new ClientResponsePair(new String[]{clientid}, JSONBuilderHandler.buildJson(null, BuildType.GETDRAWWORDS));
    }
}
