package com.rocklobstre.sink.domain;

import com.rocklobstre.sink.model.Comment;

import io.reactivex.Completable;

public interface RemoteCommentRepository {
    Completable sync(Comment comment);
}
