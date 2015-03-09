package com.arkar.apps.gitbook.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by arkar on 3/9/15.
 */
public class Owner {
    @SerializedName("login") private String name;
    @SerializedName("avatar_url") private String avatar;

    public Owner() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
