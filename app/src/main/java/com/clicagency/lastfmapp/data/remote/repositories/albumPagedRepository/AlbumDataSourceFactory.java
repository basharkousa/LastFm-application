package com.clicagency.lastfmapp.data.remote.repositories.albumPagedRepository;


import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.clicagency.lastfmapp.data.local.entity.Album;

public class AlbumDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<PageKeyedDataSource<Integer, Album>> albumLiveDataSource = new MutableLiveData<>();

    private String artistName;
    public AlbumDataSourceFactory(String artistName){
       this.artistName = artistName ;
    }

    @Override
    public DataSource create() {
        AlbumDataSource albumDataSource = new AlbumDataSource(artistName);

        albumLiveDataSource.postValue(albumDataSource);

        return albumDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, Album>> getItemLiveDataSource() {
        return albumLiveDataSource;
    }
}
