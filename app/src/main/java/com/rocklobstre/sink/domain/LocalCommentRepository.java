package com.rocklobstre.sink.domain;

import com.rocklobstre.sink.model.Comment;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Responsible for CRUD operations against local comment repository
 */
public interface LocalCommentRepository {
    Single<Comment> add(long photoId, String commentText);
    Completable update(Comment comment);
    Completable delete(Comment comment);
    Flowable<List<Comment>> getComments(long photoId);
}
