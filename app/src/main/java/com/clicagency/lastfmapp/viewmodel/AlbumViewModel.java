package com.clicagency.lastfmapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.clicagency.lastfmapp.data.local.entity.Album;
import com.clicagency.lastfmapp.data.remote.repositories.albumPagedRepository.AlbumDataSource;
import com.clicagency.lastfmapp.data.remote.repositories.albumPagedRepository.AlbumDataSourceFactory;

public class AlbumViewModel extends ViewModel {

    //creating livedata for PagedList and PagedKeyedDataSource
    LiveData<PagedList<Album>> albumPagedList;
    LiveData<PageKeyedDataSource<Integer, Album>> liveDataSource;

    //constructor
    public AlbumViewModel(@NonNull Application application,String artistName) {
        //Log.e("Artist",artistName);
        //getting our data source factory
        AlbumDataSourceFactory albumDataSourceFactory = new AlbumDataSourceFactory(artistName);

        //getting the live data source from data source factory
        liveDataSource = albumDataSourceFactory.getItemLiveDataSource();

        //Getting PagedList config
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(true)
                        .setPageSize(AlbumDataSource.PAGE_SIZE).build();

        //Building the paged list
        albumPagedList = (new LivePagedListBuilder(albumDataSourceFactory, pagedListConfig)).build();
    }


    public LiveData<PagedList<Album>> getAlbumPagedList() {
        return albumPagedList;
    }

}
