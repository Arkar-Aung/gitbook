package com.arkar.apps.gitbook.network;

import com.arkar.apps.gitbook.model.Repo;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by arkar on 3/9/15.
 */
public interface RepoApi {
    @GET("/repositories")
    Observable<List<Repo>> getRepositories(@Header("Authorization") String token);

    @GET("/repos/{owner}/{repoName}")
    Observable<Repo> getRepo(@Header("Authorization") String token,
        @Path("owner") String owner, @Path("repoName") String repoName);
}
