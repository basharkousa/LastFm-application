package com.clicagency.lastfmapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.clicagency.lastfmapp.data.local.entity.Album;
import com.clicagency.lastfmapp.data.remote.models.NetworkState;
import com.clicagency.lastfmapp.data.remote.repositories.albumPagedRepository.AlbumDataSource;
import com.clicagency.lastfmapp.data.remote.repositories.albumPagedRepository.AlbumDataSourceFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class AlbumViewModel extends ViewModel {

    //creating livedata for PagedList and PagedKeyedDataSource

    // LiveData<PageKeyedDataSource<Integer, Album>> liveDataSource;

    private Executor executor;
    private LiveData<PagedList<Album>> albumPagedList;
    private LiveData<NetworkState> networkState;

    @Inject
    AlbumDataSourceFactory albumDataSourceFactory;

    @Inject
    public AlbumViewModel() {

    }

    public void getAlbums(String artistName) {
        executor = Executors.newFixedThreadPool(5);
        //getting our data source factory
        //after dagger2
        //albumDataSourceFactory = new AlbumDataSourceFactory(artistName);
        albumDataSourceFactory.setArtistName(artistName);

        networkState = Transformations.switchMap(albumDataSourceFactory.getItemLiveDataSource(),
                dataSource -> dataSource.getNetworkState());

        //Getting PagedList config
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        //.setInitialLoadSizeHint(10)
                        .setPageSize(AlbumDataSource.PAGE_SIZE).build();
        //Building the paged list
        albumPagedList = (new LivePagedListBuilder(albumDataSourceFactory, pagedListConfig))
                .setFetchExecutor(executor)
                .build();
    }


    public LiveData<PagedList<Album>> getAlbumPagedList() {
        return albumPagedList;
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

//    @Override
//    protected void onCleared() {
//        super.onCleared();
//        albumDataSourceFactory.getAlbumDataSource().clear();
//    }

    public void retry() {
        albumDataSourceFactory.getAlbumDataSource().retryPagination();
    }

}
