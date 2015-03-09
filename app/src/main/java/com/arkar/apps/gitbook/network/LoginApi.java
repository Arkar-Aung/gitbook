package com.arkar.apps.gitbook.network;

import com.arkar.apps.gitbook.model.Auth;
import com.arkar.apps.gitbook.model.LoginParam;

import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.PUT;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by arkar on 3/9/15.
 */
public interface LoginApi {

    @Headers("Content-Type: application/json")
    @PUT("/authorizations/clients/{clientId}")
    Observable<Auth> basicLogin(@Header("Authorization") String authorization,
            @Path("clientId") String clientId, @Body LoginParam loginParam);
}
