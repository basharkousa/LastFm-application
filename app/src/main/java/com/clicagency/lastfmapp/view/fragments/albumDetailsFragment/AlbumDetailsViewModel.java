package com.clicagency.lastfmapp.view.fragments.albumDetailsFragment;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.clicagency.lastfmapp.data.local.entity.Album;
import com.clicagency.lastfmapp.data.remote.models.albums.albumDetails.AlbumDetailsRespnse;
import com.clicagency.lastfmapp.data.remote.repositories.AlbumRepository;
import com.clicagency.lastfmapp.tools.SingleLiveEvent;
import com.clicagency.lastfmapp.view.listeners.IResponseListener;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AlbumDetailsViewModel extends ViewModel {

    private MutableLiveData<AlbumDetailsRespnse> mutableLiveDataAlbum = new MutableLiveData<>();
    private SingleLiveEvent<String> errorMessageRecieved = new SingleLiveEvent<>();
    private AlbumRepository albumRepository ;


    private LiveData<List<Album>> mAllAlbums;

    @Inject
    public AlbumDetailsViewModel(AlbumRepository albumRepository, SavedStateHandle savedStateHandle) {

        this.albumRepository = albumRepository;

        this.mAllAlbums = albumRepository.getAllAlbums();

        Log.e("AlbumDetailsViewModel","Init()");

        Album album = (Album) savedStateHandle.get("Album");
        Log.e("AlbumDetailsAlbum",album.getName()+"BBBB");

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
