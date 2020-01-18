package com.clicagency.lastfmapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.clicagency.lastfmapp.data.local.entity.Album;
import com.clicagency.lastfmapp.data.remote.models.albums.albumDetails.AlbumDetailsRespnse;
import com.clicagency.lastfmapp.data.remote.repositories.AlbumRepository;

import java.util.List;

public class AlbumDetailsViewModel extends AndroidViewModel {

    private MutableLiveData<AlbumDetailsRespnse> mutableLiveDataAlbum;
    private AlbumRepository albumRepository ;
    private String albumName;
    private String artistName;

    private LiveData<List<Album>> mAllAlbums;

    public AlbumDetailsViewModel(@NonNull Application application,String albumName,String artistName) {
        super(application);
        albumRepository = AlbumRepository.getInstance(application);
        this.albumName = albumName;
        this.artistName = artistName;

        this.mAllAlbums = albumRepository.getAllAlbums();

    }

    public MutableLiveData<AlbumDetailsRespnse> getAlbumDetails() {
        return mutableLiveDataAlbum = albumRepository.getAlbumDetailsRequest(albumName,artistName);
    }

    public LiveData<List<Album>> getAllAlbumFromDb() {
        return mAllAlbums;
    }

    public void insert(Album albumEntity) {
        albumRepository.insert(albumEntity);
    }

    public void delete(Album albumEntity) {
        albumRepository.delete(albumEntity);
    }

    public void deleteAllAlbums(){albumRepository.deleteAll();}

    public LiveData<Album> isAlbumExist(Album album){
       return albumRepository.isExistAlbum(album);
    }
}
