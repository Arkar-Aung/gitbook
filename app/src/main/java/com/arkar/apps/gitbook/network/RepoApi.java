package com.arkar.apps.gitbook.network;

import com.arkar.apps.gitbook.model.Repo;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by arkar on 3/9/15.
 */
public interface RepoApi {
    @GET("/repositories")
    Observable<List<Repo>> getRepositories(@Query("per_page") String perPage);

    @GET("/repos/{owner}/{repoName}")
    Observable<Repo> getRepo(@Path("owner") String owner, @Path("repoName") String repoName);
}
