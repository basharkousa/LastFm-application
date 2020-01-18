package com.clicagency.lastfmapp.data.remote.repositories;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.clicagency.lastfmapp.data.local.AlbumDatabase;
import com.clicagency.lastfmapp.data.local.dao.AlbumDao;
import com.clicagency.lastfmapp.data.local.entity.Album;
import com.clicagency.lastfmapp.data.remote.ApiClient;
import com.clicagency.lastfmapp.data.remote.LastFmApi;
import com.clicagency.lastfmapp.data.remote.models.albums.albumDetails.AlbumDetailsRespnse;
import com.clicagency.lastfmapp.tools.AppExecutors;
import com.clicagency.lastfmapp.tools.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumRepository {

    private static AlbumRepository albumRepository;

    //for api
    private final LastFmApi lastFmAPI;
    private Map<String, String> options;

    //for dataBase
    private final AlbumDao mAlbumDao;
    private LiveData<List<Album>> mAlbums;

    private AlbumRepository(Application application) {
        //for api
        this.lastFmAPI = ApiClient.getClient();
        options = new HashMap<>();

        //for database
        AlbumDatabase db = AlbumDatabase.getInstance(application);
        mAlbumDao = db.albumDao();
        mAlbums = mAlbumDao.getAllAlbums();

    }

    public static AlbumRepository getInstance(Application application) {

        if (albumRepository == null) {
            synchronized (ArtistRepository.class) {
                if (albumRepository == null) {
                    albumRepository = new AlbumRepository(application);
                }
            }
        }
        return albumRepository;
    }

    public MutableLiveData<AlbumDetailsRespnse> getAlbumDetailsRequest(String albumName, String artistName) {

        final MutableLiveData<AlbumDetailsRespnse> data = new MutableLiveData<>();
        if (options == null)
            options = new HashMap<>();
        options.put("api_key", Constants.API_KEY);
        options.put("format", "json");
        options.put("artist", artistName);
        options.put("album", albumName);


        Call<AlbumDetailsRespnse> callBack = lastFmAPI.getAlbumDetails(options);
        callBack.enqueue(new Callback<AlbumDetailsRespnse>() {

            @Override
            public void onResponse(@NonNull Call<AlbumDetailsRespnse> call, @NonNull Response<AlbumDetailsRespnse> response) {

                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {

                }
            }

            @Override
            public void onFailure(Call<AlbumDetailsRespnse> call, Throwable t) {

            }
        });
        return data;
    }

    //for database
    public LiveData<List<Album>> getAllAlbums() {
        return mAlbums;
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
