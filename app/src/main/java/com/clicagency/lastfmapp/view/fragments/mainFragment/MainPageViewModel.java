package com.clicagency.lastfmapp.view.fragments.mainFragment;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.clicagency.lastfmapp.data.local.entity.Album;
import com.clicagency.lastfmapp.data.remote.repositories.AlbumRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainPageViewModel extends ViewModel implements DefaultLifecycleObserver {

    private AlbumRepository mRepository;
    private LiveData<PagedList<Album>> mAlbumsBypage;

    @Inject
    public MainPageViewModel(AlbumRepository albumRepository) {

        mRepository = albumRepository;
        mAlbumsBypage = mRepository.getAllAlbumsPerPage();
//        mRepository.printMessage("hello from repository");

        Log.e("MainPageViewModel","Init()");

    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onCreate(owner);
        Log.e("MainPageViewModel","onCreate()");
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner){
        DefaultLifecycleObserver.super.onStart(owner);
        Log.e("MainPageViewModel","onStart()");
    }

    //    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
//    protected void onCreate(){
//      Log.i("TAG","onCreateMainPageViewModel");
//    }

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
