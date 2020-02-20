package com.clicagency.lastfmapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.clicagency.lastfmapp.data.local.entity.Album;
import com.clicagency.lastfmapp.data.remote.repositories.AlbumRepository;

import javax.inject.Inject;

public class MainPageViewModel extends ViewModel{

    private AlbumRepository mRepository;
    private LiveData<PagedList<Album>> mAlbumsBypage;

    @Inject
    public MainPageViewModel(AlbumRepository albumRepository) {

        mRepository = albumRepository;
        mAlbumsBypage = mRepository.getAllAlbumsPerPage();
    }

    public LiveData<PagedList<Album>> getmAlbumsPerPage() {
        return mAlbumsBypage;
    }

    public void insert(Album album) {
        mRepository.insert(album);
    }


    public void delete(Album album) {
        mRepository.delete(album);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }



}
