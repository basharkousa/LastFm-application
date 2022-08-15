package com.clicagency.lastfmapp.data.remote.repositories;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.clicagency.lastfmapp.data.local.dao.AlbumDao;
import com.clicagency.lastfmapp.data.local.entity.Album;
import com.clicagency.lastfmapp.data.remote.LastFmApi;
import com.clicagency.lastfmapp.data.remote.models.albums.albumDetails.AlbumDetailsRespnse;
import com.clicagency.lastfmapp.tools.AppExecutors;
import com.clicagency.lastfmapp.tools.Constants;
import com.clicagency.lastfmapp.view.listeners.IResponseListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Provides;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class AlbumRepository {

    //for api
    private final LastFmApi lastFmAPI;
    //for dataBase
    private final AlbumDao mAlbumDao;
    private LiveData<List<Album>> mAlbums;

    @Inject
    public AlbumRepository(AlbumDao albumDao, LastFmApi lastFmAPI) {
        //for api
        this.lastFmAPI = lastFmAPI;
        //for database
        this.mAlbumDao = albumDao;
        this.mAlbums = albumDao.getAllAlbums();
    }


    public void getAlbumDetailsRequest(String albumName, String artistName,
                                       final IResponseListener<AlbumDetailsRespnse> listener) {

        Call<AlbumDetailsRespnse> callBack = lastFmAPI.getAlbumDetails(artistName, albumName);
        callBack.enqueue(new Callback<AlbumDetailsRespnse>() {

            @Override
            public void onResponse(@NonNull Call<AlbumDetailsRespnse> call,
                                   @NonNull Response<AlbumDetailsRespnse> response) {

                if (response.isSuccessful()) {

                    listener.onSuccess(response.body());

                } else {

                    listener.onFailure("body error");
                }
            }

            @Override
            public void onFailure(Call<AlbumDetailsRespnse> call, Throwable t) {
                listener.onFailure(t.getMessage());

            }
        });
    }

    //for database
    public LiveData<List<Album>> getAllAlbums() {
        return mAlbums;
    }

    public void printMessage(String str){
        Log.e("printMessage",str);
    }

    public LiveData<PagedList<Album>> getAllAlbumsPerPage() {
        // Configuration for our Paging
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setPrefetchDistance(3)
                .setPageSize(3)
                .build();

        LiveData<PagedList<Album>> albums = (new LivePagedListBuilder(mAlbumDao.getAllAlbumsPerPage(), pagedListConfig)).build();

        return albums;
    }

    public void insert(final Album album) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mAlbumDao.insertAlbum(album);
            }
        });
    }

    public void delete(final Album album) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mAlbumDao.deleteAlbum(album);
            }
        });
    }

    public void deleteAll() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mAlbumDao.deleteAll();
            }
        });
    }

    public LiveData<Album> isExistAlbum(Album album) {
        return mAlbumDao.getAlbumById(album.getMbid());
    }
}
