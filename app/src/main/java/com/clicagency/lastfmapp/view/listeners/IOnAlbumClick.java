package com.clicagency.lastfmapp.view.listeners;

import android.view.View;

import com.clicagency.lastfmapp.data.local.entity.Album;

public interface IOnAlbumClick {
    void itemClicked(Album album, int position, View view);

}
