package com.arkar.apps.gitbook.model;

/**
 * Created by arkar on 3/9/15.
 */
public class Auth {
    private String token;
    private String hashToken;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getHashToken() {
        return hashToken;
    }

    public void setHashToken(String hashToken) {
        this.hashToken = hashToken;
    }
}
