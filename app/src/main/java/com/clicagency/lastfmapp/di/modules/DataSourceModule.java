package com.clicagency.lastfmapp.di.modules;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import com.clicagency.lastfmapp.data.local.AlbumDatabase;
import com.clicagency.lastfmapp.data.local.dao.AlbumDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DataSourceModule {

    @Provides
    @Singleton
    AlbumDatabase provideArticleDatabase(@ApplicationContext Context application) {
        Log.d(AlbumDatabase.class.getSimpleName(), "From AppModule: Creating a Singleton database instance");
        return Room.databaseBuilder(
                        application, AlbumDatabase.class,
                        AlbumDatabase.DATABASE_NAME)
                //.addCallback(sRoomDatabaseCallback)
                .build();
    }


//    @Provides
//    @Singleton
//    fun provideDatabase(application: Application, callback: ArticleDatabase.Callback): ArticleDatabase{
//        return Room.databaseBuilder(application, ArticleDatabase::class.java, "news_database")
//            .fallbackToDestructiveMigration()
//                .addCallback(callback)
//                .build()
//    }



    @Provides
    @Singleton
    AlbumDao provideAlbumDao(AlbumDatabase albumDatabase) {
        return albumDatabase.albumDao();
    }

//    @Provides
//    fun provideArticleDao(db: ArticleDatabase): ArticleDao{
//        return db.getArticleDao()
//    }
}
