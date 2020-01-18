package com.clicagency.lastfmapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.clicagency.lastfmapp.data.local.entity.Album;
import com.clicagency.lastfmapp.data.remote.repositories.AlbumRepository;

public class MainPageViewModel extends AndroidViewModel{

    private AlbumRepository mRepository;
    private LiveData<PagedList<Album>> mAlbumsBypage;

    public MainPageViewModel(@NonNull Application application) {
        super(application);

        mRepository = AlbumRepository.getInstance(application);
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
