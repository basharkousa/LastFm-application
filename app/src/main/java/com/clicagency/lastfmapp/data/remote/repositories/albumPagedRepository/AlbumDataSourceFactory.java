package com.clicagency.lastfmapp.data.remote.repositories.albumPagedRepository;


import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.clicagency.lastfmapp.data.local.entity.Album;

import javax.inject.Inject;
import javax.inject.Singleton;

//@Singleton
public class AlbumDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<AlbumDataSource> albumLiveDataSource = new MutableLiveData<>();

    @Inject
    AlbumDataSource albumDataSource;

    private String artistName;

    @Inject
    public AlbumDataSourceFactory(){
       //this.artistName = artistName ;

        this.albumLiveDataSource = new MutableLiveData<AlbumDataSource>();
    }

    @Override
    public DataSource create() {
//        albumDataSource = new AlbumDataSource(artistName);
        albumDataSource.setArtistName(artistName);
        albumLiveDataSource.postValue(albumDataSource);

        return albumDataSource;
    }

    public MutableLiveData<AlbumDataSource> getItemLiveDataSource() {
        return albumLiveDataSource;
    }
    public AlbumDataSource getAlbumDataSource() {
        return albumDataSource;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
}
