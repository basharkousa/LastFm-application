package com.clicagency.lastfmapp.di.modules;

import android.app.Application;
import android.util.Log;

import androidx.room.Room;

import com.clicagency.lastfmapp.data.local.AlbumDatabase;
import com.clicagency.lastfmapp.data.local.dao.AlbumDao;
import com.clicagency.lastfmapp.data.remote.LastFmApi;
import com.clicagency.lastfmapp.data.remote.RequestInterceptor;
import com.clicagency.lastfmapp.data.remote.repositories.AlbumRepository;
import com.clicagency.lastfmapp.data.remote.repositories.ArtistRepository;
import com.clicagency.lastfmapp.data.remote.repositories.albumPagedRepository.AlbumDataSource;
import com.clicagency.lastfmapp.data.remote.repositories.albumPagedRepository.AlbumDataSourceFactory;
import com.clicagency.lastfmapp.tools.Constants;

import java.lang.annotation.Retention;
import java.util.concurrent.TimeUnit;


import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import kotlin.annotation.AnnotationRetention;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.inject.Qualifier;
import javax.inject.Singleton;

//import static com.clicagency.lastfmapp.data.local.AlbumDatabase.sRoomDatabaseCallback;

//@Module(includes = ViewModelModule.class)
@Module
@InstallIn(ActivityComponent.class)
public class AppModule {

//    @Provides
////    @Singleton
//    OkHttpClient provideOkHttpClient() {
//        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
//        okHttpClient.connectTimeout(Constants.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
//        okHttpClient.readTimeout(Constants.READ_TIMEOUT, TimeUnit.MILLISECONDS);
//        okHttpClient.writeTimeout(Constants.WRITE_TIMEOUT, TimeUnit.MILLISECONDS);
//        okHttpClient.addInterceptor(new RequestInterceptor());
//        okHttpClient.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
//        return okHttpClient.build();
//    }
//
//    @Provides
////    @Singleton
//    LastFmApi provideRetrofit(OkHttpClient okHttpClient) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Constants.BASE_API_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(okHttpClient)
//                .build();
//
//        return retrofit.create(LastFmApi.class);
//    }

//    @Provides
////    @Singleton
//    AlbumDatabase provideArticleDatabase(Application application) {
//        Log.d(AlbumDatabase.class.getSimpleName(), "From AppModule: Creating a Singleton database instance");
//        return Room.databaseBuilder(
//                application, AlbumDatabase.class,
//                AlbumDatabase.DATABASE_NAME)
//                //.addCallback(sRoomDatabaseCallback)
//                .build();
//    }

    @Provides
    @Singleton
    AlbumRepository provideAlbumRepository(AlbumDao albumDao, LastFmApi lastFmAPI){
        return new AlbumRepository(albumDao,lastFmAPI);
    }

    @Provides
    @Singleton
    ArtistRepository provideArtistRepository(AlbumDao albumDao, LastFmApi lastFmAPI){
        return new ArtistRepository(lastFmAPI);
    }

    @Provides
    @Singleton
    AlbumDataSource provideAlbumDataSource(AlbumDao albumDao, LastFmApi lastFmAPI){
        return new AlbumDataSource();
    }

    @Provides
    @Singleton
    AlbumDataSourceFactory provideAlbumDataSourceFactory(AlbumDao albumDao, LastFmApi lastFmAPI){
        return new AlbumDataSourceFactory();
    }


//

//    @Provides
////    @Singleton
//    AlbumDao provideAlbumDao(AlbumDatabase albumDatabase) {
//        return albumDatabase.albumDao();
//    }


//    @Provides
//    @ArtistName
//    static String provideArtistName(AlbumsArtistFragment fragment) {
//        return fragment.getArtistName();
//    }

}
