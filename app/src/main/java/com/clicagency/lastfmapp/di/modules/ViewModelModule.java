package com.clicagency.lastfmapp.di.modules;


import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.clicagency.lastfmapp.viewmodel.AlbumDetailsViewModel;
import com.clicagency.lastfmapp.viewmodel.AlbumViewModel;
import com.clicagency.lastfmapp.viewmodel.ArtistViewModel;
import com.clicagency.lastfmapp.viewmodel.MainPageViewModel;
import com.clicagency.lastfmapp.viewmodel.ViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Allows us to inject dependencies via constructor injection
 */
@Module
public abstract class ViewModelModule {

    @Binds
    @SuppressWarnings("unused")
    abstract ViewModelProvider.Factory bindsViewModelFactory(ViewModelFactory viewModelFactory);

    @Binds
    @IntoMap
    @ViewModelKey(AlbumDetailsViewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindsAlbumDetailsViewModel(AlbumDetailsViewModel albumDetailsViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AlbumViewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindsAlbumViewModel(AlbumViewModel albumViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ArtistViewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindsArtistViewModel(ArtistViewModel artistViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MainPageViewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindsMainPageViewModel(MainPageViewModel mainPageViewModel);


}
