package com.rocklobstre.sink.di;

import android.content.Context;

import com.rocklobstre.sink.App;
import com.rocklobstre.sink.data.CommentDao;
import com.rocklobstre.sink.data.CommentDatabase;
import com.rocklobstre.sink.data.LocalCommentDataStore;
import com.rocklobstre.sink.data.RemoteCommentDataStore;
import com.rocklobstre.sink.domain.LocalCommentRepository;
import com.rocklobstre.sink.domain.RemoteCommentRepository;
import com.rocklobstre.sink.domain.services.jobs.GcmJobService;
import com.rocklobstre.sink.domain.services.jobs.SchedulerJobService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * This is where you will inject application-wide dependencies.
 */
@Module
public class AppModule {

    @Provides
    Context provideContext(App application) {
        return application.getApplicationContext();
    }

    @Singleton
    @Provides
    SchedulerJobService provideSchedulerJobService() {
        return new SchedulerJobService();
    }

    @Singleton
    @Provides
    GcmJobService provideGcmJobService() {
        return new GcmJobService();
    }

    @Singleton
    @Provides
    CommentDao provideCommentDao(Context context) {
        return CommentDatabase.getInstance(context).commentDao();
    }

    @Singleton
    @Provides
    LocalCommentRepository provideLocalCommentRepository(CommentDao commentDao) {
        return new LocalCommentDataStore(commentDao);
    }

    @Singleton
    @Provides
    RemoteCommentRepository provideRemoteCommentRepository() {
        return new RemoteCommentDataStore();
    }
}
