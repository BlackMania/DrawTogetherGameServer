package com.apihandler;

import org.json.JSONObject;

public class GameHistoryAPI extends APIBase {

    public GameHistoryAPI() {
        super("secured/gamehistory/add");
    }

    public void uploadGameHistory(JSONObject data)
    {
        this.setPost();
        this.setRequestProperty("SharedKey", sharedSecret);
        this.setBody(data);
        this.makeCall();
    }
}
