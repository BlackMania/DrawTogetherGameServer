package com.apihandler;

public class TokenAuthAPI extends APIBase{
    public TokenAuthAPI() {
        super("auth/tokenauth");
    }

    public Integer verifyUserToken(String token){
        this.setPost();
        this.setRequestProperty("Authorization", "Bearer " + token);
        this.makeCall();
        return this.getResponseCode();
    }
}
