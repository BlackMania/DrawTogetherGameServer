package com.gamelogic.handlers;

import org.json.JSONObject;

public class ClientResponsePair {
    private String[] updatableClientIDs;
    private JSONObject response;

    public ClientResponsePair(String[] updatableClientIDs, JSONObject response) {
        this.updatableClientIDs = updatableClientIDs;
        this.response = response;
    }

    public String[] getUpdatableClientIDs() {
        return updatableClientIDs;
    }

    public JSONObject getResponse() {
        return response;
    }
}
