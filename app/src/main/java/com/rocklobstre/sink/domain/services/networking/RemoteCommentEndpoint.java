package com.rocklobstre.sink.domain.services.networking;

import com.rocklobstre.sink.model.Comment;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

interface RemoteCommentEndpoint {

    @POST("/posts")
    Call<Comment> addComment(@Body Comment comment);
}
