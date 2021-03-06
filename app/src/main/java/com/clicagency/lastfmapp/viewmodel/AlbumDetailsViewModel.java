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
import com.clicagency.lastfmapp.tools.SingleLiveEvent;
import com.clicagency.lastfmapp.view.listeners.IResponseListener;

import java.util.List;

import javax.inject.Inject;

public class AlbumDetailsViewModel extends ViewModel {

    private MutableLiveData<AlbumDetailsRespnse> mutableLiveDataAlbum = new MutableLiveData<>();
    private SingleLiveEvent<String> errorMessageRecieved = new SingleLiveEvent<>();
    private AlbumRepository albumRepository ;


    private LiveData<List<Album>> mAllAlbums;

    @Inject
    public AlbumDetailsViewModel(AlbumRepository albumRepository) {

        this.albumRepository = albumRepository;

        this.mAllAlbums = albumRepository.getAllAlbums();

    }

//    public MutableLiveData<AlbumDetailsRespnse> getAlbumDetails() {
//        return mutableLiveDataAlbum = albumRepository.getAlbumDetailsRequest(albumName,artistName);
//    }

    public void getAlbumDetails(String albumName,String artistName){

        if(albumRepository != null){
           albumRepository.getAlbumDetailsRequest(albumName, artistName, new IResponseListener<AlbumDetailsRespnse>() {
               @Override
               public void onSuccess(AlbumDetailsRespnse data) {
                   mutableLiveDataAlbum.setValue(data);
               }

               @Override
               public void onFailure(String message) {
                   errorMessageRecieved.setValue(message);
               }
           });
        }

    }

    public MutableLiveData<AlbumDetailsRespnse> getMutableLiveDataAlbum() {
        return mutableLiveDataAlbum;
    }

    public void setMutableLiveDataAlbum(MutableLiveData<AlbumDetailsRespnse> mutableLiveDataAlbum) {
        this.mutableLiveDataAlbum = mutableLiveDataAlbum;
    }

    public SingleLiveEvent<String> getErrorMessageReceived() {
        return errorMessageRecieved;
    }

    public void setErrorMessageReceived(SingleLiveEvent<String> errorMessageRecieved) {
        this.errorMessageRecieved = errorMessageRecieved;
    }

    //for db

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
