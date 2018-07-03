package com.rocklobstre.sink.data;

import com.rocklobstre.sink.domain.RemoteCommentRepository;
import com.rocklobstre.sink.domain.services.jobs.JobManagerFactory;
import com.rocklobstre.sink.domain.services.jobs.SyncCommentJob;
import com.rocklobstre.sink.model.Comment;

import io.reactivex.Completable;

public class RemoteCommentDataStore implements RemoteCommentRepository {

    @Override
    public Completable sync(Comment comment) {
        return Completable.fromAction(() ->
                JobManagerFactory.getJobManager().addJobInBackground(new SyncCommentJob(comment)));
    }
}
