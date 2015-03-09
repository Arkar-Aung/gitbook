package com.arkar.apps.gitbook.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by arkar on 3/9/15.
 */
public class Repo {
    @SerializedName("name") private String repoName;
    @SerializedName("full_name") private String repoFullName;
    private Owner owner;
    @SerializedName("stargazers_count") private String starCount;
    private String description;

    public Repo() {
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getStarCount() {
        return starCount;
    }

    public void setStarCount(String starCount) {
        this.starCount = starCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
