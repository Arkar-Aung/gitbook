package com.arkar.apps.gitbook.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by arkar on 3/9/15.
 */
public class LoginParam {
    @SerializedName("client_secret") private String clientSecret;
    private String note;

    public LoginParam(String clientSecret, String note) {
        this.clientSecret = clientSecret;
        this.note = note;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
